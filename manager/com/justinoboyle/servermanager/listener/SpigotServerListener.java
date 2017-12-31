package com.justinoboyle.servermanager.listener;

import java.io.PrintStream;

public class SpigotServerListener
  extends ServerListener
{
  public void onListen(String s)
  {
    super.onListen(s);
    if ((s.contains(" Done (")) && (s.contains("s)! For help, type \"help\" or \"?\""))) {
      System.out.println("Server ID " + getUniqueName() + " is online!");
    }
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
