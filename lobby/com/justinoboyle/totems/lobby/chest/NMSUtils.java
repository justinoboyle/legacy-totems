package com.justinoboyle.totems.lobby.chest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.server.v1_8_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack;

public class NMSUtils
{
  private static String version = ;
  
  public static String getVersion()
  {
    if (version != null) {
      return version;
    }
    String name = Bukkit.getServer().getClass().getPackage().getName();
    String version = name.substring(name.lastIndexOf('.') + 1) + ".";
    return version;
  }
  
  public static Class<?> getNMSClass(String className)
  {
    String fullName = "net.minecraft.server." + getVersion() + className;
    Class<?> clazz = null;
    try
    {
      clazz = Class.forName(fullName);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return clazz;
  }
  
  public static Class<?> getOBCClass(String className)
  {
    String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
    Class<?> clazz = null;
    try
    {
      clazz = Class.forName(fullName);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return clazz;
  }
  
  public static Object getHandle(Object obj)
  {
    try
    {
      return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Object getBlockHandle(Object obj)
  {
    try
    {
      Class<?> c = getOBCClass("block.CraftBlock");
      Method m = c.getDeclaredMethod("getNMSBlock", new Class[0]);
      m.setAccessible(true);
      return m.invoke(obj, new Object[0]);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Field getField(Class<?> clazz, String name)
    throws Exception
  {
    Field field = clazz.getDeclaredField(name);
    field.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    int modifiers = modifiersField.getInt(field);
    modifiers &= 0xFFFFFFEF;
    modifiersField.setInt(field, modifiers);
    return field;
  }
  
  public static Method getMethod(Class<?> clazz, String name, Class<?>... args)
  {
    Method[] arrayOfMethod;
    int j = (arrayOfMethod = clazz.getMethods()).length;
    for (int i = 0; i < j; i++)
    {
      Method m = arrayOfMethod[i];
      if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes()))))
      {
        m.setAccessible(true);
        return m;
      }
    }
    j = (arrayOfMethod = clazz.getDeclaredMethods()).length;
    for (i = 0; i < j; i++)
    {
      Method m = arrayOfMethod[i];
      if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes()))))
      {
        m.setAccessible(true);
        return m;
      }
    }
    return null;
  }
  
  public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2)
  {
    boolean equal = true;
    if (l1.length != l2.length) {
      return false;
    }
    for (int i = 0; i < l1.length; i++) {
      if (l1[i] != l2[i])
      {
        equal = false;
        break;
      }
    }
    return equal;
  }
  
  public static org.bukkit.inventory.ItemStack setData(org.bukkit.inventory.ItemStack item, String key, String data)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NullPointerException
  {
    net.minecraft.server.v1_8_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag = itemStack.getTag();
    tag.setString(key, data);
    itemStack.setTag(tag);
    return CraftItemStack.asBukkitCopy(itemStack);
  }
  
  public static String getData(org.bukkit.inventory.ItemStack item, String key)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, NullPointerException
  {
    net.minecraft.server.v1_8_R2.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
    NBTTagCompound tag = itemStack.getTag();
    return tag.getString(key);
  }
  
  public static org.bukkit.inventory.ItemStack setID(org.bukkit.inventory.ItemStack item, long data)
  {
    try
    {
      return setData(item, "voterKeyID", data);
    }
    catch (SecurityException|NoSuchFieldException|IllegalArgumentException|IllegalAccessException|NullPointerException e)
    {
      e.printStackTrace();
    }
    return item;
  }
  
  public static long getID(org.bukkit.inventory.ItemStack item)
  {
    try
    {
      return Long.parseLong(getData(item, "voterKeyID"));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return -1L;
  }
}
