package com.justinoboyle.totems.lobby.scoreboard;

import com.justinoboyle.totems.db.DBPlayer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bukkit.entity.Player;

public class LobbyScoreboard
{
  public static void updateScoreboard(Player player)
  {
    DBPlayer dbPlayer = DBPlayer.loadPlayer(player);
    List<String> sidebar = new ArrayList();
    NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
    sidebar.add("§7-----------§f");
    sidebar.add(" ");
    sidebar.add("§6§lCoins");
    sidebar.add("§7" + format.format(dbPlayer.getCoins()) + "§f");
    
    sidebar.add(" §f");
    sidebar.add("§d§lLevel");
    sidebar.add("§7" + dbPlayer.getLevel() + "§a");
    
    sidebar.add("§1§1§1§r§r§r§1");
    sidebar.add("§a§lTotal Kills");
    sidebar.add("§7" + format.format(dbPlayer.getTotalKills()) + "§c");
    
    sidebar.add("    §a");
    sidebar.add("§7-----------");
    
    String pad = "§btotems§7.§bus";
    TotemsScoreboard.getScoreboard(player).setSide("   " + pad + "   ", sidebar);
  }
}
