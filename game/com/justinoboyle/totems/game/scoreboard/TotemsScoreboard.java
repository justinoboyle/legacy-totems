package com.justinoboyle.totems.game.scoreboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TotemsScoreboard
{
  private ScoreboardManager manager;
  private Scoreboard board;
  private Map<String, Team> teams = new HashMap();
  Objective bar;
  Objective health;
  
  public TotemsScoreboard()
  {
    this.manager = Bukkit.getScoreboardManager();
    this.board = this.manager.getNewScoreboard();
    this.bar = this.board.registerNewObjective("sidebar", "dummy");
    updateHealth();
    updateBoard();
  }
  
  private void updateHealth()
  {
    this.health = this.board.registerNewObjective("health", "dummy");
    this.health.setDisplayName("§7% §c❤");
    this.health.setDisplaySlot(DisplaySlot.BELOW_NAME);
    for (Player p : Bukkit.getOnlinePlayers()) {
      this.health.getScore(p).setScore((int)(p.getHealth() / p.getMaxHealth() * 100.0D));
    }
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
    for (Player p : ) {
      p.setScoreboard(this.board);
    }
  }
  
  public void hideBoard()
  {
    for (Player p : ) {
      p.setScoreboard(null);
    }
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
}
