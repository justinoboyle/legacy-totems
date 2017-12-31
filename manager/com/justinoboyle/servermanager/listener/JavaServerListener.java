package com.justinoboyle.servermanager.listener;

import java.io.PrintStream;

public class JavaServerListener
  extends ServerListener
{
  public void onListen(String s)
  {
    if (s.contains("Listening on /0.0.0.0:")) {
      System.out.println("Server ID " + getUniqueName() + " is online!");
    }
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
