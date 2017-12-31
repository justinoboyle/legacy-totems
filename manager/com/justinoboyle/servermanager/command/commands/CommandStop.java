package com.justinoboyle.servermanager.command.commands;

import com.justinoboyle.servermanager.command.Command;
import com.justinoboyle.servermanager.command.CommandImplementation;
import com.justinoboyle.servermanager.command.CommandInterpreter;
import com.justinoboyle.servermanager.core.ServerManagerLauncher;

@CommandImplementation
public class CommandStop
  extends Command
{
  public String[] getCommands()
  {
    return new String[] { "stop", "exit" };
  }
  
  public void onCommand(String commandName, String[] args, CommandInterpreter runner)
  {
    runner.println("Starting shutdown procedure...");
    ServerManagerLauncher.shutdown(0);
  }
}
