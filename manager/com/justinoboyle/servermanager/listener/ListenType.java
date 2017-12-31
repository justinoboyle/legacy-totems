package com.justinoboyle.servermanager.listener;

public enum ListenType
{
  GENERIC(DefaultListener.class),  JAVA(JavaServerListener.class),  SPIGOT(SpigotServerListener.class);
  
  private Class<? extends ServerListener> cl;
  
  private ListenType(Class<? extends ServerListener> cl)
  {
    this.cl = cl;
  }
  
  public Class<? extends ServerListener> getListenerClass()
  {
    return this.cl;
  }
}
