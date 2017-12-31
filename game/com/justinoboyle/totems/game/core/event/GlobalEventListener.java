package com.justinoboyle.totems.game.core.event;

import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.team.Team;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

public class GlobalEventListener
  implements Listener
{
  public GlobalEventListener()
  {
    Bukkit.getPluginManager().registerEvents(this, Totems.getInstance());
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    e.setJoinMessage("§8[§2+§8] §a" + e.getPlayer().getName() + " §7joined the match.");
    Team t = Totems.getInstance().getArena().getOtherTeam(Totems.getInstance().getArena().getBiggerTeam());
    PlayerData d = new PlayerData(Totems.getInstance().getArena(), e.getPlayer());
    d.setTeam(t);
    Totems.getInstance().getArena().data.add(d);
    d.spawnPlayer();
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    e.setQuitMessage("§8[§4-§8] §c" + e.getPlayer().getName() + " §7left the match.");
    new BukkitRunnable()
    {
      public void run()
      {
        if (Totems.getInstance().getArena().doTeamsNeedShuffling()) {
          Totems.getInstance().getArena().balanceTeams(false);
        }
      }
    }.runTaskLater(Totems.getInstance(), 5L);
  }
  
  @EventHandler
  public void spawn(CreatureSpawnEvent e)
  {
    if ((e.getEntityType() != EntityType.ARMOR_STAND) && (e.getEntityType() != EntityType.ARROW)) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPhysics(BlockPhysicsEvent e)
  {
    Material[] ok = { Material.GRASS, Material.DIRT, Material.WATER, Material.LAVA };
    Material[] arrayOfMaterial1;
    int j = (arrayOfMaterial1 = ok).length;
    for (int i = 0; i < j; i++)
    {
      Material m = arrayOfMaterial1[i];
      if (e.getBlock().getType().equals(m)) {
        return;
      }
    }
    e.setCancelled(true);
  }
  
  @EventHandler
  public void decay(BlockFadeEvent e)
  {
    e.setCancelled(true);
  }
  
  @EventHandler
  public void leafDecay(LeavesDecayEvent e)
  {
    e.setCancelled(true);
  }
  
  @EventHandler
  public void fromTo(BlockFromToEvent e)
  {
    e.setCancelled(true);
  }
  
  @EventHandler
  public void onDamage(EntityDamageEvent e)
  {
    if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
      e.setCancelled(true);
    }
    try
    {
      if ((Totems.getInstance().getArena() == null) || (Totems.getInstance().getArena().gameOver) || (!Totems.getInstance().getArena().inGame)) {
        e.setCancelled(true);
      }
    }
    catch (Exception localException) {}
  }
  
  @EventHandler
  public void hunger(FoodLevelChangeEvent e)
  {
    e.setCancelled(true);
  }
  
  @EventHandler
  public void drop(PlayerDropItemEvent e)
  {
    e.setCancelled(true);
  }
  
  @EventHandler
  public void onWeatherChange(WeatherChangeEvent event)
  {
    event.setCancelled(true);
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent e)
  {
    if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    Material[] cancel = { Material.CHEST, Material.ANVIL, Material.WORKBENCH };
    Material[] arrayOfMaterial1;
    int j = (arrayOfMaterial1 = cancel).length;
    for (int i = 0; i < j; i++)
    {
      Material m = arrayOfMaterial1[i];
      if (e.getClickedBlock().getType().equals(m)) {
        e.setCancelled(true);
      }
    }
  }
}
