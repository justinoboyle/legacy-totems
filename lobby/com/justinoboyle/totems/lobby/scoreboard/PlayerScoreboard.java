package com.justinoboyle.totems.lobby.scoreboard;

import com.justinoboyle.totems.lobby.TotemsLobby;
import com.justinoboyle.totems.util.ServerListener;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class PlayerScoreboard
  implements Listener
{
  private ScoreboardManager manager;
  private Scoreboard board;
  private Map<String, Team> teams = new HashMap();
  Objective bar;
  private OfflinePlayer player;
  private static int id = 0;
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    if (this.player == null)
    {
      System.out.println("[Semi-Severe] On logout, stored OfflinePlayer is null.");
      return;
    }
    if (e.getPlayer().getUniqueId().toString().equals(this.player.getUniqueId().toString())) {
      unregister();
    }
  }
  
  public void unregister()
  {
    TotemsScoreboard.getScoreboards().remove(this);
    Team t;
    for (Iterator localIterator = this.teams.values().iterator(); localIterator.hasNext(); t.unregister()) {
      t = (Team)localIterator.next();
    }
    this.teams = null;
    this.bar = null;
    this.player = null;
    this.manager = null;
    this.board = null;
  }
  
  public PlayerScoreboard(OfflinePlayer player)
  {
    this.player = player;
    this.manager = Bukkit.getScoreboardManager();
    this.board = this.manager.getNewScoreboard();
    Bukkit.getPluginManager().registerEvents(this, TotemsLobby.getInstance());
    TotemsScoreboard.getScoreboards().add(this);
    this.bar = this.board.registerNewObjective("sidebar" + id++, "dummy");
    ServerListener.fireStatUpdate(player.getPlayer());
    updateBoard();
  }
  
  public void setName(Player p, String pfx, String sfx)
  {
    Team t = null;
    if (this.teams.containsKey(p.getUniqueId().toString()))
    {
      t = (Team)this.teams.get(p.getUniqueId().toString());
      this.teams.remove(p.getUniqueId().toString());
    }
    if (t == null) {
      t = this.board.registerNewTeam(p.getName().toString());
    }
    t.setPrefix(pfx);
    t.setSuffix(sfx);
    t.addPlayer(p);
    this.teams.put(p.getUniqueId().toString(), t);
    updateBoard();
  }
  
  public void setSide(String title, List<String> message)
  {
    this.bar.unregister();
    this.bar = this.board.registerNewObjective("sidebar", "dummy");
    this.bar.setDisplayName(title);
    this.bar.setDisplaySlot(DisplaySlot.SIDEBAR);
    Collections.reverse(message);
    int i = 0;
    for (String s : message)
    {
      Score sc = this.bar.getScore(s.substring(0, Math.min(s.length(), 16)));
      sc.setScore(i);
      i++;
    }
    updateBoard();
  }
  
  public void updateBoard()
  {
    this.player.getPlayer().setScoreboard(this.board);
  }
  
  public void hideBoard()
  {
    this.player.getPlayer().setScoreboard(null);
  }
  
  public Scoreboard getBoard()
  {
    return this.board;
  }
  
  public void setBoard(Scoreboard board)
  {
    this.board = board;
  }
  
  public Map<String, Team> getTeams()
  {
    return this.teams;
  }
  
  public OfflinePlayer getPlayer()
  {
    return this.player;
  }
}
