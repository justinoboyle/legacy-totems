package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class Gson
{
  static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
  private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
  private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal();
  private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = Collections.synchronizedMap(new HashMap());
  private final List<TypeAdapterFactory> factories;
  private final ConstructorConstructor constructorConstructor;
  private final boolean serializeNulls;
  private final boolean htmlSafe;
  private final boolean generateNonExecutableJson;
  private final boolean prettyPrinting;
  final JsonDeserializationContext deserializationContext = new JsonDeserializationContext()
  {
    public <T> T deserialize(JsonElement json, Type typeOfT)
      throws JsonParseException
    {
      return (T)Gson.this.fromJson(json, typeOfT);
    }
  };
  final JsonSerializationContext serializationContext = new JsonSerializationContext()
  {
    public JsonElement serialize(Object src)
    {
      return Gson.this.toJsonTree(src);
    }
    
    public JsonElement serialize(Object src, Type typeOfSrc)
    {
      return Gson.this.toJsonTree(src, typeOfSrc);
    }
  };
  
  public Gson()
  {
    this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
  }
  
  Gson(Excluder excluder, FieldNamingStrategy fieldNamingPolicy, Map<Type, InstanceCreator<?>> instanceCreators, boolean serializeNulls, boolean complexMapKeySerialization, boolean generateNonExecutableGson, boolean htmlSafe, boolean prettyPrinting, boolean serializeSpecialFloatingPointValues, LongSerializationPolicy longSerializationPolicy, List<TypeAdapterFactory> typeAdapterFactories)
  {
    this.constructorConstructor = new ConstructorConstructor(instanceCreators);
    this.serializeNulls = serializeNulls;
    this.generateNonExecutableJson = generateNonExecutableGson;
    this.htmlSafe = htmlSafe;
    this.prettyPrinting = prettyPrinting;
    
    List<TypeAdapterFactory> factories = new ArrayList();
    
    factories.add(TypeAdapters.JSON_ELEMENT_FACTORY);
    factories.add(ObjectTypeAdapter.FACTORY);
    
    factories.add(excluder);
    
    factories.addAll(typeAdapterFactories);
    
    factories.add(TypeAdapters.STRING_FACTORY);
    factories.add(TypeAdapters.INTEGER_FACTORY);
    factories.add(TypeAdapters.BOOLEAN_FACTORY);
    factories.add(TypeAdapters.BYTE_FACTORY);
    factories.add(TypeAdapters.SHORT_FACTORY);
    factories.add(TypeAdapters.newFactory(Long.TYPE, Long.class, 
      longAdapter(longSerializationPolicy)));
    factories.add(TypeAdapters.newFactory(Double.TYPE, Double.class, 
      doubleAdapter(serializeSpecialFloatingPointValues)));
    factories.add(TypeAdapters.newFactory(Float.TYPE, Float.class, 
      floatAdapter(serializeSpecialFloatingPointValues)));
    factories.add(TypeAdapters.NUMBER_FACTORY);
    factories.add(TypeAdapters.CHARACTER_FACTORY);
    factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
    factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
    factories.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
    factories.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
    factories.add(TypeAdapters.URL_FACTORY);
    factories.add(TypeAdapters.URI_FACTORY);
    factories.add(TypeAdapters.UUID_FACTORY);
    factories.add(TypeAdapters.LOCALE_FACTORY);
    factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
    factories.add(TypeAdapters.BIT_SET_FACTORY);
    factories.add(DateTypeAdapter.FACTORY);
    factories.add(TypeAdapters.CALENDAR_FACTORY);
    factories.add(TimeTypeAdapter.FACTORY);
    factories.add(SqlDateTypeAdapter.FACTORY);
    factories.add(TypeAdapters.TIMESTAMP_FACTORY);
    factories.add(ArrayTypeAdapter.FACTORY);
    factories.add(TypeAdapters.CLASS_FACTORY);
    
    factories.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
    factories.add(new MapTypeAdapterFactory(this.constructorConstructor, complexMapKeySerialization));
    factories.add(new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor));
    factories.add(TypeAdapters.ENUM_FACTORY);
    factories.add(new ReflectiveTypeAdapterFactory(
      this.constructorConstructor, fieldNamingPolicy, excluder));
    
    this.factories = Collections.unmodifiableList(factories);
  }
  
  private TypeAdapter<Number> doubleAdapter(boolean serializeSpecialFloatingPointValues)
  {
    if (serializeSpecialFloatingPointValues) {
      return TypeAdapters.DOUBLE;
    }
    new TypeAdapter()
    {
      public Double read(JsonReader in)
        throws IOException
      {
        if (in.peek() == JsonToken.NULL)
        {
          in.nextNull();
          return null;
        }
        return Double.valueOf(in.nextDouble());
      }
      
      public void write(JsonWriter out, Number value)
        throws IOException
      {
        if (value == null)
        {
          out.nullValue();
          return;
        }
        double doubleValue = value.doubleValue();
        Gson.this.checkValidFloatingPoint(doubleValue);
        out.value(value);
      }
    };
  }
  
  private TypeAdapter<Number> floatAdapter(boolean serializeSpecialFloatingPointValues)
  {
    if (serializeSpecialFloatingPointValues) {
      return TypeAdapters.FLOAT;
    }
    new TypeAdapter()
    {
      public Float read(JsonReader in)
        throws IOException
      {
        if (in.peek() == JsonToken.NULL)
        {
          in.nextNull();
          return null;
        }
        return Float.valueOf((float)in.nextDouble());
      }
      
      public void write(JsonWriter out, Number value)
        throws IOException
      {
        if (value == null)
        {
          out.nullValue();
          return;
        }
        float floatValue = value.floatValue();
        Gson.this.checkValidFloatingPoint(floatValue);
        out.value(value);
      }
    };
  }
  
  private void checkValidFloatingPoint(double value)
  {
    if ((Double.isNaN(value)) || (Double.isInfinite(value))) {
      throw new IllegalArgumentException(value + 
        " is not a valid double value as per JSON specification. To override this" + 
        " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
    }
  }
  
  private TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy)
  {
    if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
      return TypeAdapters.LONG;
    }
    new TypeAdapter()
    {
      public Number read(JsonReader in)
        throws IOException
      {
        if (in.peek() == JsonToken.NULL)
        {
          in.nextNull();
          return null;
        }
        return Long.valueOf(in.nextLong());
      }
      
      public void write(JsonWriter out, Number value)
        throws IOException
      {
        if (value == null)
        {
          out.nullValue();
          return;
        }
        out.value(value.toString());
      }
    };
  }
  
  public <T> TypeAdapter<T> getAdapter(TypeToken<T> type)
  {
    TypeAdapter<?> cached = (TypeAdapter)this.typeTokenCache.get(type);
    if (cached != null) {
      return cached;
    }
    Map<TypeToken<?>, FutureTypeAdapter<?>> threadCalls = (Map)this.calls.get();
    boolean requiresThreadLocalCleanup = false;
    if (threadCalls == null)
    {
      threadCalls = new HashMap();
      this.calls.set(threadCalls);
      requiresThreadLocalCleanup = true;
    }
    FutureTypeAdapter<T> ongoingCall = (FutureTypeAdapter)threadCalls.get(type);
    if (ongoingCall != null) {
      return ongoingCall;
    }
    try
    {
      FutureTypeAdapter<T> call = new FutureTypeAdapter();
      threadCalls.put(type, call);
      for (TypeAdapterFactory factory : this.factories)
      {
        TypeAdapter<T> candidate = factory.create(this, type);
        if (candidate != null)
        {
          call.setDelegate(candidate);
          this.typeTokenCache.put(type, candidate);
          return candidate;
        }
      }
      throw new IllegalArgumentException("GSON cannot handle " + type);
    }
    finally
    {
      threadCalls.remove(type);
      if (requiresThreadLocalCleanup) {
        this.calls.remove();
      }
    }
  }
  
  public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory skipPast, TypeToken<T> type)
  {
    boolean skipPastFound = false;
    if (!this.factories.contains(skipPast)) {
      skipPastFound = true;
    }
    for (TypeAdapterFactory factory : this.factories) {
      if (!skipPastFound)
      {
        if (factory == skipPast) {
          skipPastFound = true;
        }
      }
      else
      {
        TypeAdapter<T> candidate = factory.create(this, type);
        if (candidate != null) {
          return candidate;
        }
      }
    }
    throw new IllegalArgumentException("GSON cannot serialize " + type);
  }
  
  public <T> TypeAdapter<T> getAdapter(Class<T> type)
  {
    return getAdapter(TypeToken.get(type));
  }
  
  public JsonElement toJsonTree(Object src)
  {
    if (src == null) {
      return JsonNull.INSTANCE;
    }
    return toJsonTree(src, src.getClass());
  }
  
  public JsonElement toJsonTree(Object src, Type typeOfSrc)
  {
    JsonTreeWriter writer = new JsonTreeWriter();
    toJson(src, typeOfSrc, writer);
    return writer.get();
  }
  
  public String toJson(Object src)
  {
    if (src == null) {
      return toJson(JsonNull.INSTANCE);
    }
    return toJson(src, src.getClass());
  }
  
  public String toJson(Object src, Type typeOfSrc)
  {
    StringWriter writer = new StringWriter();
    toJson(src, typeOfSrc, writer);
    return writer.toString();
  }
  
  public void toJson(Object src, Appendable writer)
    throws JsonIOException
  {
    if (src != null) {
      toJson(src, src.getClass(), writer);
    } else {
      toJson(JsonNull.INSTANCE, writer);
    }
  }
  
  public void toJson(Object src, Type typeOfSrc, Appendable writer)
    throws JsonIOException
  {
    try
    {
      JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
      toJson(src, typeOfSrc, jsonWriter);
    }
    catch (IOException e)
    {
      throw new JsonIOException(e);
    }
  }
  
  /* Error */
  public void toJson(Object src, Type typeOfSrc, JsonWriter writer)
    throws JsonIOException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_2
    //   2: invokestatic 509	com/google/gson/reflect/TypeToken:get	(Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;
    //   5: invokevirtual 433	com/google/gson/Gson:getAdapter	(Lcom/google/gson/reflect/TypeToken;)Lcom/google/gson/TypeAdapter;
    //   8: astore 4
    //   10: aload_3
    //   11: invokevirtual 512	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   14: istore 5
    //   16: aload_3
    //   17: iconst_1
    //   18: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   21: aload_3
    //   22: invokevirtual 521	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   25: istore 6
    //   27: aload_3
    //   28: aload_0
    //   29: getfield 110	com/google/gson/Gson:htmlSafe	Z
    //   32: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   35: aload_3
    //   36: invokevirtual 527	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   39: istore 7
    //   41: aload_3
    //   42: aload_0
    //   43: getfield 106	com/google/gson/Gson:serializeNulls	Z
    //   46: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   49: aload 4
    //   51: aload_3
    //   52: aload_1
    //   53: invokevirtual 533	com/google/gson/TypeAdapter:write	(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
    //   56: goto +38 -> 94
    //   59: astore 8
    //   61: new 484	com/google/gson/JsonIOException
    //   64: dup
    //   65: aload 8
    //   67: invokespecial 500	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   70: athrow
    //   71: astore 9
    //   73: aload_3
    //   74: iload 5
    //   76: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   79: aload_3
    //   80: iload 6
    //   82: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   85: aload_3
    //   86: iload 7
    //   88: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   91: aload 9
    //   93: athrow
    //   94: aload_3
    //   95: iload 5
    //   97: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   100: aload_3
    //   101: iload 6
    //   103: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   106: aload_3
    //   107: iload 7
    //   109: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   112: return
    // Line number table:
    //   Java source line #595	-> byte code offset #0
    //   Java source line #596	-> byte code offset #10
    //   Java source line #597	-> byte code offset #16
    //   Java source line #598	-> byte code offset #21
    //   Java source line #599	-> byte code offset #27
    //   Java source line #600	-> byte code offset #35
    //   Java source line #601	-> byte code offset #41
    //   Java source line #603	-> byte code offset #49
    //   Java source line #604	-> byte code offset #56
    //   Java source line #605	-> byte code offset #61
    //   Java source line #606	-> byte code offset #71
    //   Java source line #607	-> byte code offset #73
    //   Java source line #608	-> byte code offset #79
    //   Java source line #609	-> byte code offset #85
    //   Java source line #610	-> byte code offset #91
    //   Java source line #607	-> byte code offset #94
    //   Java source line #608	-> byte code offset #100
    //   Java source line #609	-> byte code offset #106
    //   Java source line #611	-> byte code offset #112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	113	0	this	Gson
    //   0	113	1	src	Object
    //   0	113	2	typeOfSrc	Type
    //   0	113	3	writer	JsonWriter
    //   8	42	4	adapter	TypeAdapter<?>
    //   14	82	5	oldLenient	boolean
    //   25	77	6	oldHtmlSafe	boolean
    //   39	69	7	oldSerializeNulls	boolean
    //   59	7	8	e	IOException
    //   71	21	9	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   49	56	59	java/io/IOException
    //   49	71	71	finally
  }
  
  public String toJson(JsonElement jsonElement)
  {
    StringWriter writer = new StringWriter();
    toJson(jsonElement, writer);
    return writer.toString();
  }
  
  public void toJson(JsonElement jsonElement, Appendable writer)
    throws JsonIOException
  {
    try
    {
      JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
      toJson(jsonElement, jsonWriter);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
  
  private JsonWriter newJsonWriter(Writer writer)
    throws IOException
  {
    if (this.generateNonExecutableJson) {
      writer.write(")]}'\n");
    }
    JsonWriter jsonWriter = new JsonWriter(writer);
    if (this.prettyPrinting) {
      jsonWriter.setIndent("  ");
    }
    jsonWriter.setSerializeNulls(this.serializeNulls);
    return jsonWriter;
  }
  
  /* Error */
  public void toJson(JsonElement jsonElement, JsonWriter writer)
    throws JsonIOException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 512	com/google/gson/stream/JsonWriter:isLenient	()Z
    //   4: istore_3
    //   5: aload_2
    //   6: iconst_1
    //   7: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   10: aload_2
    //   11: invokevirtual 521	com/google/gson/stream/JsonWriter:isHtmlSafe	()Z
    //   14: istore 4
    //   16: aload_2
    //   17: aload_0
    //   18: getfield 110	com/google/gson/Gson:htmlSafe	Z
    //   21: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   24: aload_2
    //   25: invokevirtual 527	com/google/gson/stream/JsonWriter:getSerializeNulls	()Z
    //   28: istore 5
    //   30: aload_2
    //   31: aload_0
    //   32: getfield 106	com/google/gson/Gson:serializeNulls	Z
    //   35: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   38: aload_1
    //   39: aload_2
    //   40: invokestatic 564	com/google/gson/internal/Streams:write	(Lcom/google/gson/JsonElement;Lcom/google/gson/stream/JsonWriter;)V
    //   43: goto +37 -> 80
    //   46: astore 6
    //   48: new 484	com/google/gson/JsonIOException
    //   51: dup
    //   52: aload 6
    //   54: invokespecial 500	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   57: athrow
    //   58: astore 7
    //   60: aload_2
    //   61: iload_3
    //   62: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   65: aload_2
    //   66: iload 4
    //   68: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   71: aload_2
    //   72: iload 5
    //   74: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   77: aload 7
    //   79: athrow
    //   80: aload_2
    //   81: iload_3
    //   82: invokevirtual 517	com/google/gson/stream/JsonWriter:setLenient	(Z)V
    //   85: aload_2
    //   86: iload 4
    //   88: invokevirtual 524	com/google/gson/stream/JsonWriter:setHtmlSafe	(Z)V
    //   91: aload_2
    //   92: iload 5
    //   94: invokevirtual 530	com/google/gson/stream/JsonWriter:setSerializeNulls	(Z)V
    //   97: return
    // Line number table:
    //   Java source line #664	-> byte code offset #0
    //   Java source line #665	-> byte code offset #5
    //   Java source line #666	-> byte code offset #10
    //   Java source line #667	-> byte code offset #16
    //   Java source line #668	-> byte code offset #24
    //   Java source line #669	-> byte code offset #30
    //   Java source line #671	-> byte code offset #38
    //   Java source line #672	-> byte code offset #43
    //   Java source line #673	-> byte code offset #48
    //   Java source line #674	-> byte code offset #58
    //   Java source line #675	-> byte code offset #60
    //   Java source line #676	-> byte code offset #65
    //   Java source line #677	-> byte code offset #71
    //   Java source line #678	-> byte code offset #77
    //   Java source line #675	-> byte code offset #80
    //   Java source line #676	-> byte code offset #85
    //   Java source line #677	-> byte code offset #91
    //   Java source line #679	-> byte code offset #97
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	this	Gson
    //   0	98	1	jsonElement	JsonElement
    //   0	98	2	writer	JsonWriter
    //   4	78	3	oldLenient	boolean
    //   14	73	4	oldHtmlSafe	boolean
    //   28	65	5	oldSerializeNulls	boolean
    //   46	7	6	e	IOException
    //   58	20	7	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   38	43	46	java/io/IOException
    //   38	58	58	finally
  }
  
  public <T> T fromJson(String json, Class<T> classOfT)
    throws JsonSyntaxException
  {
    Object object = fromJson(json, classOfT);
    return (T)Primitives.wrap(classOfT).cast(object);
  }
  
  public <T> T fromJson(String json, Type typeOfT)
    throws JsonSyntaxException
  {
    if (json == null) {
      return null;
    }
    StringReader reader = new StringReader(json);
    T target = fromJson(reader, typeOfT);
    return target;
  }
  
  public <T> T fromJson(Reader json, Class<T> classOfT)
    throws JsonSyntaxException, JsonIOException
  {
    JsonReader jsonReader = new JsonReader(json);
    Object object = fromJson(jsonReader, classOfT);
    assertFullConsumption(object, jsonReader);
    return (T)Primitives.wrap(classOfT).cast(object);
  }
  
  public <T> T fromJson(Reader json, Type typeOfT)
    throws JsonIOException, JsonSyntaxException
  {
    JsonReader jsonReader = new JsonReader(json);
    T object = fromJson(jsonReader, typeOfT);
    assertFullConsumption(object, jsonReader);
    return object;
  }
  
  private static void assertFullConsumption(Object obj, JsonReader reader)
  {
    try
    {
      if ((obj != null) && (reader.peek() != JsonToken.END_DOCUMENT)) {
        throw new JsonIOException("JSON document was not fully consumed.");
      }
    }
    catch (MalformedJsonException e)
    {
      throw new JsonSyntaxException(e);
    }
    catch (IOException e)
    {
      throw new JsonIOException(e);
    }
  }
  
  public <T> T fromJson(JsonReader reader, Type typeOfT)
    throws JsonIOException, JsonSyntaxException
  {
    boolean isEmpty = true;
    boolean oldLenient = reader.isLenient();
    reader.setLenient(true);
    try
    {
      reader.peek();
      isEmpty = false;
      TypeToken<T> typeToken = TypeToken.get(typeOfT);
      TypeAdapter<T> typeAdapter = getAdapter(typeToken);
      T object = typeAdapter.read(reader);
      return object;
    }
    catch (EOFException e)
    {
      if (isEmpty) {
        return null;
      }
      throw new JsonSyntaxException(e);
    }
    catch (IllegalStateException e)
    {
      throw new JsonSyntaxException(e);
    }
    catch (IOException e)
    {
      throw new JsonSyntaxException(e);
    }
    finally
    {
      reader.setLenient(oldLenient);
    }
  }
  
  public <T> T fromJson(JsonElement json, Class<T> classOfT)
    throws JsonSyntaxException
  {
    Object object = fromJson(json, classOfT);
    return (T)Primitives.wrap(classOfT).cast(object);
  }
  
  public <T> T fromJson(JsonElement json, Type typeOfT)
    throws JsonSyntaxException
  {
    if (json == null) {
      return null;
    }
    return (T)fromJson(new JsonTreeReader(json), typeOfT);
  }
  
  static class FutureTypeAdapter<T>
    extends TypeAdapter<T>
  {
    private TypeAdapter<T> delegate;
    
    public void setDelegate(TypeAdapter<T> typeAdapter)
    {
      if (this.delegate != null) {
        throw new AssertionError();
      }
      this.delegate = typeAdapter;
    }
    
    public T read(JsonReader in)
      throws IOException
    {
      if (this.delegate == null) {
        throw new IllegalStateException();
      }
      return (T)this.delegate.read(in);
    }
    
    public void write(JsonWriter out, T value)
      throws IOException
    {
      if (this.delegate == null) {
        throw new IllegalStateException();
      }
      this.delegate.write(out, value);
    }
  }
  
  public String toString()
  {
    return 
    
      "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
  }
}
