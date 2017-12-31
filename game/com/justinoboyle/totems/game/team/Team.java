package com.justinoboyle.totems.game.team;

import com.justinoboyle.totems.core.errors.ErrorReporting;
import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.loc.LocationManager;
import com.justinoboyle.totems.game.rotation.RotationManager;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public enum Team
{
  RED("c", Material.REDSTONE_BLOCK),  BLUE("3", Material.LAPIS_BLOCK);
  
  private Location spawn = null;
  private Location totem = null;
  public String color;
  private static final double TOTEM_MAX_HEALTH = 100.0D;
  private double totemHealth = 100.0D;
  private ArmorStand totemHologramA;
  private ArmorStand totemHologramB;
  private int damagesDone = 0;
  private Material breakMaterial;
  
  private Team(String s, Material m)
  {
    this.color = s;
    this.breakMaterial = m;
  }
  
  public void reset()
  {
    killHolograms();
    this.spawn = null;
    this.totem = null;
    this.totemHealth = 100.0D;
    this.totemHologramA = null;
    this.totemHologramB = null;
    this.damagesDone = 0;
  }
  
  public Location getSpawnLocation()
  {
    if (this.spawn != null) {
      return this.spawn;
    }
    try
    {
      this.spawn = LocationManager.getLocation(Totems.getInstance().getRotationManager().getCurrentWorld().getName(), toString().toLowerCase() + "spawn");
    }
    catch (Exception ex)
    {
      this.spawn = ((World)Bukkit.getWorlds().get(0)).getSpawnLocation();
      ErrorReporting.sendStackTrace(ex);
    }
    return this.spawn;
  }
  
  public Location getTotemLocation()
  {
    if (this.totem != null) {
      return this.totem;
    }
    try
    {
      this.totem = LocationManager.getLocation(Totems.getInstance().getRotationManager().getCurrentWorld().getName(), toString().toLowerCase() + "totem");
    }
    catch (Exception ex)
    {
      this.totem = ((World)Bukkit.getWorlds().get(0)).getSpawnLocation();
      ErrorReporting.sendStackTrace(ex);
    }
    return this.totem;
  }
  
  public void setTotemHealth(double newTotemHealth)
  {
    this.totemHealth = newTotemHealth;
    if (this.totemHealth > 100.0D) {
      this.totemHealth = 100.0D;
    }
    if (this.totemHealth < 0.0D) {
      this.totemHealth = 0.0D;
    }
    updateHealth();
  }
  
  public double getTotemHealth()
  {
    return this.totemHealth;
  }
  
  public void sendTeamMessage(Arena a, String s)
  {
    for (PlayerData d : a.data) {
      if (d.getTeam() == this) {
        d.getPlayer().sendMessage(s);
      }
    }
  }
  
  public void damageTotem(PlayerData d)
  {
    if (d.arena != Totems.getInstance().getArena()) {
      return;
    }
    if (d.arena.gameOver) {
      return;
    }
    this.damagesDone += 1;
    if (this.damagesDone == 1) {
      sendTeamMessage(d.arena, "§c§lYOUR TOTEM IS UNDER ATTACK!");
    }
    if (this.damagesDone % 6 == 0) {
      sendTeamMessage(d.arena, "§cYour totem is at " + Math.round(this.totemHealth) + "% health!");
    }
    setTotemHealth(getTotemHealth() - 3.299999952316284D);
    for (Player p : Bukkit.getOnlinePlayers())
    {
      p.getWorld().playEffect(getTotemLocation(), Effect.STEP_SOUND, this.breakMaterial.getId());
      p.getWorld().playEffect(getTotemLocation().clone().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, this.breakMaterial.getId());
      p.getWorld().playSound(getTotemLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);
    }
    d.arena.updateScoreboard();
    if (getTotemHealth() == 0.0D) {
      d.arena.gameWin(d.arena.getOtherTeam(this), this, d.getPlayer());
    }
  }
  
  public ArmorStand getTotemHologramA()
  {
    createTotemHologramA();
    return this.totemHologramA;
  }
  
  public void setTotemHologramA(ArmorStand totemHologramA)
  {
    this.totemHologramA = totemHologramA;
  }
  
  public void killHologramA()
  {
    if (this.totemHologramA != null) {
      this.totemHologramA.remove();
    }
  }
  
  private void createTotemHologramA()
  {
    if ((this.totemHologramA != null) && (!this.totemHologramA.isDead())) {
      return;
    }
    this.totemHologramA = ((ArmorStand)getTotemLocation().getWorld().spawnEntity(getTotemLocation().clone().add(0.5D, 0.0D, 0.5D), EntityType.ARMOR_STAND));
    this.totemHologramA.setGravity(false);
    this.totemHologramA.setVisible(false);
    this.totemHologramA.setCustomNameVisible(true);
    updateHealth();
  }
  
  public void updateHealth()
  {
    this.totemHologramA.setCustomName("§a" + Math.round(this.totemHealth) + "§7%§e HEALTH");
  }
  
  public void killHologramB()
  {
    if (this.totemHologramB != null) {
      this.totemHologramB.remove();
    }
  }
  
  private void createTotemHologramB()
  {
    if ((this.totemHologramB != null) && (!this.totemHologramB.isDead())) {
      return;
    }
    this.totemHologramB = ((ArmorStand)getTotemLocation().getWorld().spawnEntity(getTotemLocation().clone().add(0.5D, 0.3D, 0.5D), EntityType.ARMOR_STAND));
    this.totemHologramB.setGravity(false);
    this.totemHologramB.setVisible(false);
    this.totemHologramB.setCustomNameVisible(true);
    this.totemHologramB.setCustomName("§" + this.color + "§l" + toString() + " TOTEM");
  }
  
  public void createTotemHolograms()
  {
    createTotemHologramA();
    createTotemHologramB();
  }
  
  public void killHolograms()
  {
    killHologramA();
    killHologramB();
  }
  
  public ArmorStand getTotemHologramB()
  {
    createTotemHologramB();
    return this.totemHologramB;
  }
}
