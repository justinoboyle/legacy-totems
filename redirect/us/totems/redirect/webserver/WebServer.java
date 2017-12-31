package us.totems.redirect.webserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import us.totems.redirect.Main;

public class WebServer
  extends Thread
{
  public ServerHandler ft = null;
  public boolean running = true;
  
  public WebServer(ServerHandler f)
  {
    this.ft = f;
  }
  
  public void run()
  {
    try
    {
      this.ft.Server = new ServerSocket(Main.PORT, 10, InetAddress.getByName("0"));
    }
    catch (UnknownHostException e1)
    {
      e1.printStackTrace();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    while (this.running)
    {
      Socket connected = null;
      try
      {
        new WebHandler(this.ft.Server.accept(), this).start();
      }
      catch (IOException localIOException1) {}
    }
  }
}
