package com.justinoboyle.servermanager.listener;

import com.justinoboyle.servermanager.core.Server;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerListener
{
  private Server server;
  private File dataFolder;
  private int id;
  private Process proc;
  private List<PrintStream> listeners;
  private Thread thread;
  private String shutdownCommand;
  
  public void setup(Server server, int id, File dataFolder, String shutdownCommand)
  {
    this.server = server;
    this.id = id;
    this.dataFolder = dataFolder;
    this.listeners = new ArrayList();
    this.shutdownCommand = shutdownCommand;
  }
  
  public void run(File directory2, String command)
    throws InterruptedException, IOException
  {
    File directory = new File(directory2.getAbsolutePath().replace("\\", "/"));
    final boolean isWindows = System.getProperty("os.name").startsWith("Windows");
    final File bat = new File(directory, "start" + (isWindows ? ".bat" : ".sh"));
    PrintWriter w = new PrintWriter(bat);
    w.println(isWindows ? "@echo off" : "#!/bin/bash");
    w.println("cd " + directory.getAbsolutePath());
    w.println(command);
    w.close();
    String temp = command;
    this.thread = new Thread()
    {
      public void run()
      {
        try
        {
          Runtime rt = Runtime.getRuntime();
          ServerListener.this.proc = rt.exec((isWindows ? "CMD /C " : "bash ") + bat.getAbsolutePath());
          BufferedReader input = new BufferedReader(new InputStreamReader(ServerListener.this.proc.getInputStream()));
          BufferedReader error = new BufferedReader(new InputStreamReader(ServerListener.this.proc.getErrorStream()));
          String line;
          for (; ServerListener.this.proc.isAlive(); (line = error.readLine()) != null)
          {
            while ((line = input.readLine()) != null)
            {
              String line;
              ServerListener.this.onListen(line);
            }
            continue;
            ServerListener.this.onListenError(line);
          }
          input.close();
          error.close();
          if (ServerListener.this.exitValue(ServerListener.this.proc.waitFor())) {
            run();
          }
          bat.delete();
        }
        catch (Exception localException) {}
      }
    };
    this.thread.start();
  }
  
  public void forceShutdown()
  {
    if (this.proc != null) {
      try
      {
        new Thread()
        {
          public void run()
          {
            try
            {
              if (ServerListener.this.proc == null) {
                return;
              }
              ServerListener.this.proc.getOutputStream().write((ServerListener.this.shutdownCommand + "\n").getBytes());
              ServerListener.this.proc.getOutputStream().flush();
              ServerListener.this.proc.destroy();
              ServerListener.this.proc = null;
            }
            catch (IOException e)
            {
              e.printStackTrace();
            }
          }
        }.start();
        do
        {
          this.proc.destroyForcibly();
          if (this.proc == null) {
            break;
          }
        } while (this.proc.isAlive());
      }
      catch (Exception localException) {}
    }
  }
  
  public void write(final String... msgs)
  {
    if (this.proc != null) {
      new Thread()
      {
        public void run()
        {
          String[] arrayOfString;
          int j = (arrayOfString = msgs).length;
          for (int i = 0; i < j; i++)
          {
            String s = arrayOfString[i];
            try
            {
              ServerListener.this.proc.getOutputStream().write((s + "\n").getBytes());
            }
            catch (IOException localIOException) {}
          }
          try
          {
            ServerListener.this.proc.getOutputStream().flush();
          }
          catch (IOException localIOException1) {}
        }
      }.start();
    }
  }
  
  public Server getServer()
  {
    return this.server;
  }
  
  public void start()
  {
    try
    {
      run(this.dataFolder, this.server.compileLaunchArguments(this.id));
    }
    catch (InterruptedException|IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void onListen(String s)
  {
    if (!this.listeners.isEmpty()) {
      for (PrintStream out : this.listeners) {
        out.println("[" + getUniqueName() + "] " + s);
      }
    }
  }
  
  public void onListenError(String s)
  {
    System.out.println("[ERROR] " + getUniqueName() + "saved to logs/" + getUniqueName() + ".log");
  }
  
  public abstract boolean exitValue(int paramInt);
  
  public String getUniqueName()
  {
    return this.server.getName() + "#" + this.id;
  }
  
  public List<PrintStream> getListeners()
  {
    return this.listeners;
  }
}
