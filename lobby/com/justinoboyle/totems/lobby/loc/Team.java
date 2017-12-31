package com.justinoboyle.totems.lobby.loc;

import com.justinoboyle.totems.core.errors.ErrorReporting;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public enum Team
{
  RED("c"),  BLUE("3");
  
  private Location totem = null;
  public String color;
  private static final double TOTEM_MAX_HEALTH = 100.0D;
  private double totemHealth = 100.0D;
  private ArmorStand totemHologramA;
  private ArmorStand totemHologramB;
  
  private Team(String s)
  {
    this.color = s;
  }
  
  public void reset()
  {
    killHolograms();
    this.totem = null;
    this.totemHealth = 100.0D;
    this.totemHologramA = null;
    this.totemHologramB = null;
  }
  
  public Location getTotemLocation()
  {
    if (this.totem != null) {
      return this.totem;
    }
    try
    {
      this.totem = LocationManager.getLocation(((World)Bukkit.getWorlds().get(0)).getName(), toString().toLowerCase() + "totem");
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
