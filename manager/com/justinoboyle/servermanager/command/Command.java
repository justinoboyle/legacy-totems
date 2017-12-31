package com.justinoboyle.servermanager.command;

public abstract class Command
{
  public abstract String[] getCommands();
  
  public abstract void onCommand(String paramString, String[] paramArrayOfString, CommandInterpreter paramCommandInterpreter);
}
