package com.justinoboyle.servermanager.core;

import com.google.gson.Gson;
import com.justinoboyle.servermanager.listener.ListenType;
import com.justinoboyle.servermanager.listener.ServerListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class Server
{
  private File storageFolder;
  private String name;
  private int amount;
  private boolean startWithManager;
  private String launchCommand;
  private List<String> launchOptions;
  private boolean autoRelaunch;
  private boolean hasHeartbeat;
  private File template;
  private List<File> includedFiles;
  private ListenType listenerType;
  private String shutdownCommand;
  private List<String> doneStarting;
  private transient ServerListener[] listeners;
  
  public Server(String name, File storageFolder)
  {
    this.name = name;
    this.storageFolder = storageFolder;
    storageFolder.mkdirs();
    this.amount = 0;
    this.startWithManager = true;
    this.launchCommand = "java -jar launch.jar";
    this.launchOptions = new ArrayList();
    this.launchOptions.add("-Xmx1G");
    this.launchOptions.add("-Xms1G");
    this.autoRelaunch = true;
    this.hasHeartbeat = true;
    this.template = null;
    this.includedFiles = new ArrayList();
    this.listenerType = ListenType.JAVA;
    this.shutdownCommand = "stop";
    this.doneStarting = new ArrayList();
    this.doneStarting.add("s)! For help, type \"help\" or \"?\"");
    this.doneStarting.add("Listening on /0.0.0.0:");
  }
  
  public Server(String name)
  {
    this(name, new File("servers/" + name));
  }
  
  public static Server fromJson(String input)
  {
    return (Server)ServerManager.getInstance().getGson().fromJson(input, Server.class);
  }
  
  public String toString()
  {
    return ServerManager.getInstance().getGson().toJson(this);
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public File getStorageFolder()
  {
    return this.storageFolder;
  }
  
  public void setStorageFolder(File storageFolder)
  {
    this.storageFolder = storageFolder;
  }
  
  public int getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(int amount)
  {
    this.amount = amount;
  }
  
  public List<String> getLaunchOptions()
  {
    return this.launchOptions;
  }
  
  public boolean isAutoRelaunch()
  {
    return this.autoRelaunch;
  }
  
  public boolean doesStartWithManager()
  {
    return this.startWithManager;
  }
  
  public String getLaunchCommand()
  {
    return this.launchCommand;
  }
  
  public boolean hasHeartbeat()
  {
    return this.hasHeartbeat;
  }
  
  public File getTemplate()
  {
    return this.template;
  }
  
  public List<File> getIncludedFiles()
  {
    return this.includedFiles;
  }
  
  public String compileLaunchArguments(int id)
  {
    StringBuilder b = new StringBuilder();
    b.append(this.launchCommand);
    for (String s : this.launchOptions) {
      b.append(" ".concat(s.replace("$id", id)));
    }
    return b.toString();
  }
  
  public void startServers()
  {
    if (this.listeners == null) {
      this.listeners = new ServerListener[this.amount];
    }
    for (int i = 0; i < this.amount; i++)
    {
      File f = new File(this.storageFolder.getAbsolutePath() + "\\" + i);
      f.mkdirs();
      if (this.template != null)
      {
        if (f.exists()) {
          f.delete();
        }
        f.mkdirs();
        try
        {
          FileUtils.copyDirectory(this.template, f);
        }
        catch (IOException localIOException) {}
      }
      for (File fi : this.includedFiles) {
        try
        {
          Files.copy(fi.toPath(), new File(f, fi.getName()).toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
        }
        catch (IOException localIOException1) {}
      }
      try
      {
        ServerListener l = (ServerListener)this.listenerType.getListenerClass().newInstance();
        l.setup(this, i, f, this.shutdownCommand);
        this.listeners[i] = l;
        l.start();
      }
      catch (InstantiationException|IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void forceShutdownAllServers()
  {
    ServerListener[] arrayOfServerListener;
    int j = (arrayOfServerListener = this.listeners).length;
    for (int i = 0; i < j; i++)
    {
      ServerListener l = arrayOfServerListener[i];
      l.forceShutdown();
    }
  }
  
  public ServerListener[] getListeners()
  {
    return this.listeners;
  }
}
