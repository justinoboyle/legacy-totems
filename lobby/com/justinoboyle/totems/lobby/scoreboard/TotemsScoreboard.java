package com.justinoboyle.totems.lobby.scoreboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TotemsScoreboard
  implements Listener
{
  private static List<PlayerScoreboard> scoreboards = new ArrayList();
  
  public TotemsScoreboard()
  {
    for (Player p : Bukkit.getOnlinePlayers()) {
      new PlayerScoreboard(p);
    }
  }
  
  @EventHandler
  public void join(PlayerJoinEvent e)
  {
    new PlayerScoreboard(e.getPlayer());
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    try
    {
      scoreboards.remove(getScoreboard(e.getPlayer()));
    }
    catch (Exception localException) {}
  }
  
  public static List<PlayerScoreboard> getScoreboards()
  {
    return scoreboards;
  }
  
  public void setName(Player p, String pfx, String sfx)
  {
    PlayerScoreboard sc;
    for (Iterator localIterator = scoreboards.iterator(); localIterator.hasNext(); sc.setName(p, pfx, sfx)) {
      sc = (PlayerScoreboard)localIterator.next();
    }
  }
  
  public void setSide(String title, List<String> message)
  {
    PlayerScoreboard sc;
    for (Iterator localIterator = scoreboards.iterator(); localIterator.hasNext(); sc.setSide(title, message)) {
      sc = (PlayerScoreboard)localIterator.next();
    }
  }
  
  public void updateBoard()
  {
    PlayerScoreboard sc;
    for (Iterator localIterator = scoreboards.iterator(); localIterator.hasNext(); sc.updateBoard()) {
      sc = (PlayerScoreboard)localIterator.next();
    }
  }
  
  public void updateBoard(Player p)
  {
    for (PlayerScoreboard sc : scoreboards) {
      if (sc.getPlayer().getUniqueId().equals(p.getUniqueId())) {
        sc.updateBoard();
      }
    }
  }
  
  public void hideBoard()
  {
    PlayerScoreboard sc;
    for (Iterator localIterator = scoreboards.iterator(); localIterator.hasNext(); sc.hideBoard()) {
      sc = (PlayerScoreboard)localIterator.next();
    }
  }
  
  public static PlayerScoreboard getScoreboard(OfflinePlayer p)
  {
    for (PlayerScoreboard sc : scoreboards) {
      if (sc.getPlayer().getUniqueId().equals(p.getUniqueId())) {
        return sc;
      }
    }
    return new PlayerScoreboard(p);
  }
}
