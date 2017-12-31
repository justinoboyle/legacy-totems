package com.justinoboyle.totems.npc;

import com.justinoboyle.totems.core.utils.EntityUtils;
import com.justinoboyle.totems.lobby.TotemsLobby;
import com.justinoboyle.totems.lobby.loc.LocationManager;
import java.io.FileNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.PluginManager;

public abstract class NPC
  implements Listener
{
  private LivingEntity entity;
  private ArmorStand astand;
  private Location spawnLocation;
  
  public abstract String getDisplayName();
  
  public abstract String getSaveName();
  
  public abstract EntityType getEntityType();
  
  public abstract void onClick(Player paramPlayer);
  
  public NPC(World w)
  {
    try
    {
      setSpawnLocation(LocationManager.getLocation(w.getName(), "npc" + getSaveName()));
    }
    catch (FileNotFoundException localFileNotFoundException) {}
    try
    {
      spawn();
    }
    catch (Exception localException) {}
    registerListeners();
  }
  
  public void spawn()
  {
    if (getEntity() != null)
    {
      getEntity().remove();
      setEntity(null);
    }
    if (this.astand != null)
    {
      this.astand.remove();
      this.astand = null;
    }
    setEntity((LivingEntity)getSpawnLocation().getWorld().spawnEntity(getSpawnLocation(), getEntityType()));
    this.astand = ((ArmorStand)this.spawnLocation.getWorld().spawnEntity(this.spawnLocation.clone().add(0.0D, -0.1D, 0.0D), EntityType.ARMOR_STAND));
    this.astand.setVisible(false);
    this.astand.setGravity(false);
    this.astand.setCustomName(getDisplayName());
    this.astand.setCustomNameVisible(true);
    
    EntityUtils.noAI(this.entity);
    afterSpawn();
  }
  
  public abstract void afterSpawn();
  
  private void registerListeners()
  {
    Bukkit.getPluginManager().registerEvents(this, TotemsLobby.getInstance());
  }
  
  public void destroy()
  {
    if (getEntity() != null) {
      getEntity().remove();
    }
    if (this.astand != null) {
      this.astand.remove();
    }
    setEntity(null);
    this.astand = null;
    setSpawnLocation(null);
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler
  public void onAttack(EntityDamageEvent e)
  {
    if ((!e.getEntity().equals(getEntity())) && (!e.getEntity().equals(this.astand))) {
      return;
    }
    e.setCancelled(true);
  }
  
  @EventHandler
  public void rightClick(PlayerInteractEntityEvent e)
  {
    if ((!e.getRightClicked().equals(getEntity())) && (!e.getRightClicked().equals(this.astand))) {
      return;
    }
    e.setCancelled(true);
    onClick(e.getPlayer());
  }
  
  public void say(Player p, String... message)
  {
    String[] arrayOfString;
    int j = (arrayOfString = message).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      p.sendMessage(getDisplayName() + "§7: §f" + s);
    }
  }
  
  public Location getSpawnLocation()
  {
    return this.spawnLocation;
  }
  
  public void setSpawnLocation(Location spawnLocation)
  {
    this.spawnLocation = spawnLocation;
  }
  
  public LivingEntity getEntity()
  {
    return this.entity;
  }
  
  private void setEntity(LivingEntity entity)
  {
    this.entity = entity;
  }
}
