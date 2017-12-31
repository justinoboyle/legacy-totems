package com.justinoboyle.totems.game.core;

import org.bukkit.Bukkit;

public class GameSetup
{
  private static final String[] runCommands = { "gamerule mobGriefing false", "gamerule doFireTick false", "timings on" };
  
  public static void setupPlugin()
  {
    String[] arrayOfString;
    int j = (arrayOfString = runCommands).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
    }
  }
}
