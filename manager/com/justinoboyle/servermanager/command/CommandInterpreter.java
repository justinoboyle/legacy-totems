package com.justinoboyle.servermanager.command;

import com.justinoboyle.servermanager.core.ServerManager;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CommandInterpreter
{
  private PrintStream outputStream;
  private ServerManager serverManager;
  private boolean listening;
  
  public CommandInterpreter(PrintStream stream, ServerManager manager)
  {
    this.outputStream = stream;
    this.serverManager = manager;
    this.listening = false;
  }
  
  public PrintStream getOutputStream()
  {
    return this.outputStream;
  }
  
  public boolean isListening()
  {
    return this.listening;
  }
  
  public void setListening(boolean listening)
  {
    this.listening = listening;
  }
  
  public ServerManager getServerManager()
  {
    return this.serverManager;
  }
  
  public void startListening(Scanner in)
  {
    this.listening = true;
    while (this.listening) {
      interpret(in.nextLine());
    }
  }
  
  public boolean interpret(String input)
  {
    String commandName = input.split(" ")[0];
    int j;
    int i;
    for (Iterator localIterator = CommandManager.getInstance().getCommands().iterator(); localIterator.hasNext(); i < j)
    {
      Command c = (Command)localIterator.next();
      String[] arrayOfString;
      j = (arrayOfString = c.getCommands()).length;i = 0; continue;String s = arrayOfString[i];
      if (commandName.equalsIgnoreCase(s))
      {
        c.onCommand(commandName, shiftArray(input.split(" ")), this);
        return true;
      }
      i++;
    }
    println("Command \"" + input + "\" not found!");
    return false;
  }
  
  public void print(Object msg)
  {
    try
    {
      getOutputStream().write(msg.toString().getBytes());
      getOutputStream().flush();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void println(Object msg)
  {
    try
    {
      getOutputStream().write((msg.toString() + "\n").getBytes());
      getOutputStream().flush();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public String[] shiftArray(String[] in)
  {
    String[] out = new String[Math.max(in.length - 1, 0)];
    for (int i = 0; i < out.length; i++) {
      out[i] = in[(i + 1)];
    }
    return out;
  }
}
