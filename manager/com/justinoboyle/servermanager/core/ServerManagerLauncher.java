package com.justinoboyle.servermanager.core;

import com.justinoboyle.servermanager.command.CommandInterpreter;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class ServerManagerLauncher
{
  private static CommandInterpreter interpreter;
  private static ServerManager manager;
  private static boolean done = false;
  
  public static void main(String[] args)
  {
    manager = new ServerManager();
    manager.loadConfigFolder(new File("./config/servers/"));
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      public void run()
      {
        if (!ServerManagerLauncher.done) {
          ServerManagerLauncher.shutdown(0);
        }
      }
    });
    manager.startServers();
    interpreter = new CommandInterpreter(System.out, manager);
    interpreter.startListening(new Scanner(System.in));
  }
  
  public static void shutdown(int id)
  {
    System.out.println("Shutting down servers...");
    new Thread()
    {
      public void run()
      {
        for (Server s : ServerManager.getInstance().getServers()) {
          s.forceShutdownAllServers();
        }
        System.exit(0);
      }
    }.run();
    System.out.println("Servers have been shut down.");
    System.out.println("Goodbye! :(");
    done = true;
    new Thread()
    {
      public void run()
      {
        long time = System.currentTimeMillis();
        if (System.currentTimeMillis() - time >= 50L) {
          System.exit(0);
        }
      }
    }.run();
  }
}
