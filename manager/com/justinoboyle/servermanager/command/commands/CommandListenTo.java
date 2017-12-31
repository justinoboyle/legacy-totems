package com.justinoboyle.servermanager.command.commands;

import com.justinoboyle.servermanager.command.Command;
import com.justinoboyle.servermanager.command.CommandImplementation;
import com.justinoboyle.servermanager.command.CommandInterpreter;
import com.justinoboyle.servermanager.core.Server;
import com.justinoboyle.servermanager.core.ServerManager;
import com.justinoboyle.servermanager.listener.ServerListener;
import java.util.Iterator;
import java.util.List;

@CommandImplementation
public class CommandListenTo
  extends Command
{
  public String[] getCommands()
  {
    return new String[] { "listento" };
  }
  
  public void onCommand(String commandName, String[] args, CommandInterpreter runner)
  {
    if (args.length == 0)
    {
      runner.println("listento [server/stopall]"); return;
    }
    ServerListener[] arrayOfServerListener;
    int j;
    int i;
    if (args[0].equals("stopall"))
    {
      for (localIterator = runner.getServerManager().getServers().iterator(); localIterator.hasNext(); i < j)
      {
        Server s = (Server)localIterator.next();
        j = (arrayOfServerListener = s.getListeners()).length;i = 0; continue;ServerListener s2 = arrayOfServerListener[i];
        if (s2.getListeners().contains(runner.getOutputStream())) {
          s2.getListeners().remove(runner.getOutputStream());
        }
        i++;
      }
      runner.println("All listeners cleared");
      return;
    }
    for (Iterator localIterator = runner.getServerManager().getServers().iterator(); localIterator.hasNext(); i < j)
    {
      Server s = (Server)localIterator.next();
      j = (arrayOfServerListener = s.getListeners()).length;i = 0; continue;ServerListener s2 = arrayOfServerListener[i];
      if (s2.getUniqueName().equals(args[0]))
      {
        s2.getListeners().add(runner.getOutputStream());
        runner.println("Added listener."); return;
      }
      i++;
    }
    runner.println("Could not find server " + args[0]);
  }
}
