package com.justinoboyle.totems.lobby;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.lobby.scoreboard.PlayerScoreboard;
import com.justinoboyle.totems.lobby.scoreboard.TotemsScoreboard;
import com.justinoboyle.totems.rank.PlayerRank;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldListener
  implements Listener
{
  @EventHandler
  public void onJoin(final PlayerJoinEvent e)
  {
    e.setJoinMessage(null);
    e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
    e.getPlayer().setMaxHealth(6.0D);
    e.getPlayer().setHealthScale(6.0D);
    e.getPlayer().setHealth(6.0D);
    e.getPlayer().setGameMode(GameMode.ADVENTURE);
    e.getPlayer().getInventory().clear();
    
    e.getPlayer().getInventory().setItem(0, ItemUtils.setNameAndLore(new ItemStack(Material.COMPASS), "§d§lGame Menu", new String[0]));
    e.getPlayer().getInventory().setItem(1, ItemUtils.setNameAndLore(new ItemStack(Material.NAME_TAG), "§b§lFriends Menu", new String[0]));
    
    e.getPlayer().getInventory().setItem(7, ItemUtils.setNameAndLore(new ItemStack(Material.CHEST), "§d§lCosmetics", new String[0]));
    e.getPlayer().getInventory().setItem(8, ItemUtils.setNameAndLore(ItemUtils.getSkull(e.getPlayer().getName()), "§a§lPlayer Settings", new String[0]));
    
    Bukkit.broadcastMessage("test");
    
    new BukkitRunnable()
    {
      public void run()
      {
        for (Player p : ) {
          TotemsLobby.getInstance().getScoreboard().updateBoard(p);
        }
        new PlayerScoreboard(e.getPlayer());
        for (Player p : Bukkit.getOnlinePlayers()) {
          TotemsLobby.getInstance().getScoreboard().updateBoard(p);
        }
        for (Player p2 : Bukkit.getOnlinePlayers())
        {
          OfflinePlayer p = p2;
          String message = PlayerRank.getScoreboardPrefix(p);
          TotemsLobby.getInstance().getScoreboard().setName(p2, message, "");
        }
      }
    }.runTaskLater(TotemsLobby.getInstance(), 2L);
  }
  
  @EventHandler
  public void onEntityTarget(EntityTargetEvent ev)
  {
    Entity e = ev.getEntity();
    if ((e instanceof ExperienceOrb))
    {
      ev.setCancelled(true);
      ev.setTarget(null);
    }
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    e.setQuitMessage(null);
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
    e.setCancelled(true);
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
  public void physics(BlockPhysicsEvent e)
  {
    if ((e.getBlock().getType().equals(Material.STONE_BUTTON)) || (e.getBlock().getType().equals(Material.COMMAND))) {
      return;
    }
    e.setCancelled(false);
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
  
  @EventHandler
  public void onBreak(BlockBreakEvent e)
  {
    if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    e.setCancelled(true);
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e)
  {
    if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    e.setCancelled(true);
  }
}
