package com.justinoboyle.servermanager.core;

import com.google.gson.Gson;
import com.justinoboyle.servermanager.util.FileReadUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager
{
  private List<Server> servers = new ArrayList();
  private final Gson gson;
  private static ServerManager instance;
  
  public Gson getGson()
  {
    return this.gson;
  }
  
  public ServerManager()
  {
    instance = this;
    this.gson = new Gson();
  }
  
  public void loadConfigFile(File file)
  {
    try
    {
      registerServer(Server.fromJson(FileReadUtil.readFile(file)));
    }
    catch (FileNotFoundException e) {}
  }
  
  public void loadConfigFolder(File file)
  {
    if (!file.isDirectory()) {
      return;
    }
    try
    {
      file.mkdirs();
      File[] arrayOfFile;
      int j = (arrayOfFile = file.listFiles()).length;
      for (int i = 0; i < j; i++)
      {
        File f = arrayOfFile[i];
        loadConfigFile(f);
      }
    }
    catch (Exception localException) {}
  }
  
  public void registerServer(Server server)
  {
    if (!this.servers.contains(server)) {
      this.servers.add(server);
    }
  }
  
  public List<Server> getServers()
  {
    return this.servers;
  }
  
  public static ServerManager getInstance()
  {
    return instance;
  }
  
  public void startServers()
  {
    for (Server s : this.servers) {
      s.startServers();
    }
  }
  
  public void log(String msg) {}
}
