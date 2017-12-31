package com.justinoboyle.totems.core.utils;

import org.bukkit.ChatColor;

public class MOTDCenterUtil
{
  public static String center(String s, double maxPerLine)
  {
    int perSide = (int)((maxPerLine - ChatColor.stripColor(s).length()) / 2.0D);
    StringBuilder b = new StringBuilder();
    boolean bo = false;
    for (int i = 0; i < perSide * 2; i++) {
      if (i == perSide)
      {
        b.append(s);
        bo = false;
      }
      else
      {
        b.append(ChatColor.WHITE + " ");
        bo = !bo;
      }
    }
    return b.toString();
  }
  
  public static String center(String s)
  {
    return center(s, 56.25D);
  }
}
