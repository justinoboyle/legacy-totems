package com.justinoboyle.totems.game.arena;

import com.justinoboyle.totems.core.utils.BUtils;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.rotation.RotationManager;
import com.justinoboyle.totems.game.scoreboard.TotemsScoreboard;
import com.justinoboyle.totems.game.team.Team;
import com.justinoboyle.totems.game.util.TitleUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class Arena
{
  private String worldName;
  public boolean inGame = false;
  private boolean inCountdown = false;
  public boolean gameOver = false;
  public final ArrayList<PlayerData> data = new ArrayList();
  public String mapAuthor;
  
  public Arena(String worldName, String mapAuthor)
  {
    this.worldName = worldName;
    this.mapAuthor = mapAuthor;
    for (Entity e : Bukkit.getWorld(getWorldName()).getEntities()) {
      if (!(e instanceof Player))
      {
        Team[] arrayOfTeam;
        int j = (arrayOfTeam = Team.values()).length;
        for (int i = 0; i < j; i++)
        {
          Team t = arrayOfTeam[i];
          if ((e.equals(t.getTotemHologramA())) || (!e.equals(t.getTotemHologramB()))) {}
        }
        e.remove();
      }
    }
  }
  
  public String getWorldName()
  {
    return this.worldName;
  }
  
  public void updateScoreboard()
  {
    ArrayList<String> sidebar = new ArrayList();
    boolean first = true;
    String append = "";
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      if (!first) {
        sidebar.add(append);
      }
      append = append + " ";
      String color = t.color;
      sidebar.add("§" + color + "§l" + t.toString() + " TOTEM: ");
      try
      {
        sidebar.add("§a" + Math.round(t.getTotemHealth()) + "% HEALTH" + "§" + color);
      }
      catch (Exception ex)
      {
        sidebar.add("Error");
      }
      first = false;
    }
    append = append + " ";
    sidebar.add("§c " + append);
    sidebar.add("§d§lCURRENT MAP: ");
    sidebar.add("§b" + Totems.getInstance().getRotationManager().getCurrentWorld().getName());
    sidebar.add("§7by §a" + Totems.getInstance().getRotationManager().getCurrentWorldAuthor());
    Totems.getInstance().getScoreboard().setSide("§b§lGame Stats", sidebar);
  }
  
  public void startCountdown()
  {
    if ((this.inGame) || (this.inCountdown)) {
      return;
    }
    this.inCountdown = true;
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      t.createTotemHolograms();
    }
    assignTeams();
    for (PlayerData d : this.data) {
      d.spawnPlayer();
    }
    int max = 3;
    for (int i = 3; i > -1; i--)
    {
      final int count = i;
      new BukkitRunnable()
      {
        public void run()
        {
          if (!Arena.this.inCountdown)
          {
            cancel();
            return;
          }
          for (Player p : Bukkit.getOnlinePlayers())
          {
            if (count != 0) {
              BUtils.sendTitle(p, "§a" + count, "", 5, 5, 5);
            }
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, count == 0 ? 2 : 1);
            if (count == 0)
            {
              Arena.this.inCountdown = false;
              Arena.this.forceStartGame();
            }
          }
        }
      }.runTaskLater(Totems.getInstance(), 20 * Math.abs(3 - i));
    }
  }
  
  public void forceStartGame()
  {
    if (this.inGame) {
      return;
    }
    this.inGame = true;
    this.inCountdown = false;
    for (Player p : Bukkit.getOnlinePlayers()) {
      BUtils.sendTitle(p, "§cBEGIN!", "§7Map: §b" + this.worldName + "§7 by: §a" + this.mapAuthor, 5, 5, 5);
    }
    new BukkitRunnable()
    {
      public void run()
      {
        Arena.this.updateScoreboard();
      }
    }.runTaskLater(Totems.getInstance(), 5L);
  }
  
  public void assignTeams()
  {
    this.data.clear();
    for (Player p : Bukkit.getOnlinePlayers()) {
      this.data.add(new PlayerData(this, p));
    }
    balanceTeams(true);
  }
  
  public void balanceTeams(boolean shuffle)
  {
    if (shuffle) {
      Collections.shuffle(this.data, new Random());
    }
    int recur = 0;
    while ((doTeamsNeedShuffling()) && (recur < 1000))
    {
      Team biggerTeam = getBiggerTeam();
      Team smallerTeam = getOtherTeam(biggerTeam);
      for (PlayerData d : this.data) {
        if (d.getTeam() == biggerTeam)
        {
          d.setTeam(smallerTeam);
          if (shuffle) {
            break;
          }
          d.spawnPlayer();
          break;
        }
      }
      recur++;
    }
  }
  
  public boolean doTeamsNeedShuffling()
  {
    return Math.abs(getMembers(Team.BLUE).size() - getMembers(Team.RED).size()) > 1;
  }
  
  public Team getOtherTeam(Team team)
  {
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      if (t != team) {
        return t;
      }
    }
    return team;
  }
  
  public Team getBiggerTeam()
  {
    int current = 0;
    Team team = Team.BLUE;
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      int size = getMembers(t).size();
      if (size > current)
      {
        team = t;
        current = size;
      }
    }
    return team;
  }
  
  public List<PlayerData> getMembers(Team team)
  {
    List<PlayerData> list = new ArrayList();
    for (PlayerData d : this.data) {
      if (d.getTeam() == team) {
        list.add(d);
      }
    }
    return list;
  }
  
  public void disable()
  {
    this.gameOver = true;
    this.inGame = false;
    for (PlayerData d : this.data) {
      d.unload();
    }
    this.data.clear();
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      t.reset();
    }
  }
  
  public void gameWin(Team winner, Team loser, Player finalBlow)
  {
    this.gameOver = true;
    resetArena();
    TitleUtils.showWinLossScreen(this, finalBlow, winner);
    for (PlayerData da : this.data)
    {
      if (da.getTeam() == winner) {
        da.shootWinFireworks();
      }
      da.getPlayer().getInventory().clear();
    }
    Totems.getInstance().getRotationManager().rotateToNext(100);
  }
  
  public void resetArena()
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this.data.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      PlayerData d = (PlayerData)localIterator1.next();
      localIterator2 = d.placedBlocks.iterator(); continue;Location l = (Location)localIterator2.next();
      Block b = l.getBlock();
      if (l.getBlock().getType() != Material.AIR)
      {
        Material m = b.getType();
        b.setType(Material.AIR);
        for (Player p : Bukkit.getOnlinePlayers()) {
          p.getWorld().playEffect(l, Effect.STEP_SOUND, m.getId());
        }
      }
    }
  }
}
