package com.justinoboyle.servermanager.listener;

public class DefaultListener
  extends ServerListener
{
  public void onListen(String s)
  {
    super.onListen(s);
  }
  
  public void onListenError(String s)
  {
    super.onListen(s);
  }
  
  public boolean exitValue(int value)
  {
    return false;
  }
}
