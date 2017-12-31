package com.justinoboyle.servermanager.command;

import com.justinoboyle.servermanager.command.commands.CommandExecute;
import com.justinoboyle.servermanager.command.commands.CommandListenTo;
import com.justinoboyle.servermanager.command.commands.CommandStop;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CommandManager
{
  private static CommandManager instance;
  private List<Command> commands;
  
  private CommandManager()
  {
    this.commands = new ArrayList();
    
    init();
  }
  
  public static CommandManager getInstance()
  {
    if (instance == null) {
      instance = new CommandManager();
    }
    return instance;
  }
  
  public List<Command> getCommands()
  {
    return this.commands;
  }
  
  public void addCommand(Class<?> c)
  {
    try
    {
      this.commands.add((Command)c.newInstance());
    }
    catch (Exception e)
    {
      System.err.println("[SEVERE] Could not instantiate class " + c.getName());
    }
  }
  
  private void init()
  {
    this.commands.add(new CommandStop());
    this.commands.add(new CommandListenTo());
    this.commands.add(new CommandExecute());
  }
}
