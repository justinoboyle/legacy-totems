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
public class CommandExecute
  extends Command
{
  public String[] getCommands()
  {
    return new String[] { "execute" };
  }
  
  public void onCommand(String commandName, String[] args, CommandInterpreter runner)
  {
    if (args.length < 2)
    {
      runner.println("execute [server] [cmd]");
      return;
    }
    StringBuilder b = new StringBuilder();
    for (int i = 1; i < args.length; i++) {
      b.append(args[i] + " ");
    }
    String cmd = b.toString().trim();
    int j;
    int i;
    for (Iterator localIterator = runner.getServerManager().getServers().iterator(); localIterator.hasNext(); i < j)
    {
      Server s = (Server)localIterator.next();
      ServerListener[] arrayOfServerListener;
      j = (arrayOfServerListener = s.getListeners()).length;i = 0; continue;ServerListener s2 = arrayOfServerListener[i];
      if (s2.getUniqueName().equals(args[0]))
      {
        s2.write(cmd.split(";")); return;
      }
      i++;
    }
    runner.println("Could not find server " + args[0]);
  }
}
