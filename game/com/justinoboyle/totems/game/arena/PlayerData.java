package com.justinoboyle.totems.game.arena;

import com.justinoboyle.totems.core.utils.BUtils;
import com.justinoboyle.totems.core.utils.Defaults;
import com.justinoboyle.totems.core.utils.UtilFirework;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.scoreboard.TotemsScoreboard;
import com.justinoboyle.totems.game.team.Team;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerData
  implements Listener
{
  private String playerID;
  private Team t = Team.BLUE;
  private SubClass playerClass = PlayerClass.DEFAULT.getDefaultSubClass();
  public Arena arena;
  private boolean attackCooldown = false;
  public double totemDamageDone = 0.0D;
  public List<Location> placedBlocks = new ArrayList();
  private Player player;
  
  public PlayerData(Arena a, final Player player)
  {
    this.arena = a;
    this.player = player;
    this.playerID = player.getUniqueId().toString();
    Bukkit.getPluginManager().registerEvents(this, Totems.getInstance());
    new BukkitRunnable()
    {
      public void run()
      {
        player.performCommand("kit");
      }
    }.runTaskLater(Totems.getInstance(), 5L);
  }
  
  public void unload()
  {
    if (this.arena.data.contains(this)) {
      this.arena.data.remove(this);
    }
    HandlerList.unregisterAll(this);
    this.playerID = "";
    this.t = null;
    this.player = null;
  }
  
  public Player getPlayer()
  {
    if (!this.player.isOnline()) {
      unload();
    }
    return this.player;
  }
  
  public Team getTeam()
  {
    return this.t;
  }
  
  public void setTeam(Team t)
  {
    this.t = t;
  }
  
  public void shootWinFireworks()
  {
    for (int i = 0; i < 50; i++) {
      new BukkitRunnable()
      {
        public void run()
        {
          UtilFirework.shootFirework(
            PlayerData.this.getPlayer().getLocation().clone().add(new Random().nextInt(5) * (new Random().nextBoolean() ? -1 : 1), new Random().nextInt(1) * (new Random().nextBoolean() ? -1 : 1), new Random().nextInt(5) * (new Random().nextBoolean() ? -1 : 1)));
        }
      }.runTaskLater(Totems.getInstance(), 2L * i);
    }
  }
  
  public void spawnPlayer()
  {
    getPlayer().getInventory().clear();
    
    getPlayer().setFoodLevel(20);
    new BukkitRunnable()
    {
      public void run()
      {
        PlayerData.this.getPlayer().setFireTicks(0);
        PlayerData.this.getPlayer().setHealth(PlayerData.this.playerClass.getParent().getHealthScale() * 60.0F);
      }
    }.runTaskLater(Totems.getInstance(), 2L);
    
    getPlayer().setFireTicks(0);
    getPlayer().setMaxHealth(20.0D);
    getPlayer().setFallDistance(0.0F);
    getPlayer().setVelocity(new Vector(0, 0, 0));
    getPlayer().setBedSpawnLocation(getTeam().getSpawnLocation());
    getPlayer().getActivePotionEffects().clear();
    try
    {
      getPlayer().setMaxHealth(this.playerClass.getParent().getHealthScale() * 60.0F);
      getPlayer().setHealth(this.playerClass.getParent().getHealthScale() * 60.0F);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    String newName = "§" + this.t.color + getPlayer().getName();
    newName = newName.substring(0, Math.min(newName.length(), 16));
    getPlayer().setCustomName(newName);
    getPlayer().setPlayerListName(getPlayer().getName());
    Totems.getInstance().getScoreboard().setName(getPlayer(), "§" + this.t.color, "");
    Location spawn = getTeam().getSpawnLocation();
    int i = 0;
    while ((spawn.getBlock().getType() != Material.AIR) && (i < 256))
    {
      spawn = spawn.add(0.0D, 1.0D, 0.0D);
      i++;
    }
    getPlayer().teleport(getTeam().getSpawnLocation());
    getPlayer().playSound(getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    getPlayer().setWalkSpeed(getPlayerClass().getParent().getSpeedScale() * Defaults.getPlayerWalkScale());
    new BukkitRunnable()
    {
      public void run()
      {
        PlayerData.this.playerClass.spawnPlayer(PlayerData.this.getPlayer());
        PlayerData.this.getPlayer().updateInventory();
      }
    }.runTaskLater(Totems.getInstance(), 5L);
  }
  
  public SubClass getPlayerClass()
  {
    return this.playerClass;
  }
  
  public void setPlayerClass(SubClass playerClass)
  {
    this.playerClass = playerClass;
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent e)
  {
    if (e.getPlayer().getUniqueId().toString().equals(this.playerID)) {
      unload();
    }
  }
  
  @EventHandler
  public void onDeath(PlayerDeathEvent e)
  {
    if (!e.getEntity().getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    e.getDrops().clear();
    Bukkit.broadcastMessage("§9Death > §7" + e.getDeathMessage());
    e.setDeathMessage(null);
    spawnPlayer();
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e)
  {
    if (this.arena.gameOver) {
      unload();
    }
    if (!e.getPlayer().getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    if (e.getPlayer().getLocation().getY() < 0.0D) {
      e.getPlayer().damage(20.0D);
    }
  }
  
  @EventHandler
  public void attack(EntityDamageByEntityEvent e)
  {
    if (this.arena.gameOver)
    {
      e.setCancelled(true);
      return;
    }
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    Player damager = (Player)e.getDamager();
    if (!damager.getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    if ((getTeam().getTotemHologramA().equals(e.getEntity())) || (getTeam().getTotemHologramB().equals(e.getEntity())))
    {
      damager.sendMessage("§c§lDon't damage your own team's totem!");
      damager.playSound(damager.getLocation(), Sound.NOTE_BASS, 5.0F, 0.0F);
      e.setCancelled(true);
      return;
    }
    if ((this.arena.getOtherTeam(getTeam()).getTotemHologramA().equals(e.getEntity())) || (this.arena.getOtherTeam(getTeam()).getTotemHologramB().equals(e.getEntity())))
    {
      if (this.attackCooldown) {
        return;
      }
      double currentDamage = this.arena.getOtherTeam(getTeam()).getTotemHealth();
      this.attackCooldown = true;
      this.arena.getOtherTeam(getTeam()).damageTotem(this);
      damager.playSound(damager.getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
      this.totemDamageDone += currentDamage - this.arena.getOtherTeam(getTeam()).getTotemHealth();
      new BukkitRunnable()
      {
        public void run()
        {
          PlayerData.this.attackCooldown = false;
        }
      }.runTaskLater(Totems.getInstance(), 5L);
      e.setCancelled(true);
      return;
    }
  }
  
  @EventHandler
  public void blockFriendlyFire(EntityDamageByEntityEvent e)
  {
    if (this.arena.gameOver) {
      return;
    }
    if (!(e.getDamager() instanceof Player)) {
      return;
    }
    Player damager = null;
    if (!(e.getEntity() instanceof Player))
    {
      if ((e.getEntity() instanceof Arrow)) {
        damager = (Player)((Arrow)e.getEntity()).getShooter();
      }
      return;
    }
    damager = (Player)e.getDamager();
    Player victim = (Player)e.getEntity();
    if (!damager.getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    for (PlayerData d : this.arena.data) {
      if (d.getPlayer().equals(victim))
      {
        if (d.getTeam() == getTeam())
        {
          e.setCancelled(true);
          BUtils.sendActionBar(damager, "§cHey, calm down on the friendly fire!");
          return;
        }
        damager.playSound(damager.getLocation(), Sound.STEP_STONE, 10.0F, 1.0F);
        damager.playSound(damager.getLocation(), Sound.FALL_BIG, 10.0F, 0.0F);
        return;
      }
    }
    e.setCancelled(true);
  }
  
  @EventHandler
  public void breakBlock(BlockBreakEvent e)
  {
    if (!e.getPlayer().getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    if (e.getBlock().getLocation().distance(getTeam().getTotemLocation()) <= 7.0D)
    {
      e.setCancelled(true);
      return;
    }
    if (e.getBlock().getLocation().distance(getTeam().getSpawnLocation()) <= 10.0D)
    {
      e.setCancelled(true);
      return;
    }
    if ((e.getBlock().getMetadata("placedby") == null) || (e.getBlock().getMetadata("placedby").size() == 0) || (e.getBlock().getType() != Material.WOOD))
    {
      e.setCancelled(true);
      return;
    }
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e)
  {
    if (!e.getPlayer().getUniqueId().toString().equals(this.playerID)) {
      return;
    }
    if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
      return;
    }
    if (e.getBlock().getType() != Material.WOOD)
    {
      e.setCancelled(true); return;
    }
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      if (e.getBlock().getLocation().distance(t.getTotemLocation()) <= 7.0D)
      {
        e.setCancelled(true);
        return;
      }
      if (e.getBlock().getLocation().distance(t.getSpawnLocation()) <= 10.0D)
      {
        e.setCancelled(true);
        return;
      }
    }
    e.getBlock().setMetadata("placedby", new FixedMetadataValue(Totems.getInstance(), "true"));
    this.placedBlocks.add(e.getBlock().getLocation());
  }
}
