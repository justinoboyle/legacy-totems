package com.justinoboyle.totems.lobby.chest;

import com.justinoboyle.totems.lobby.TotemsLobby;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

public abstract class TreasureChest
  implements Listener
{
  public static List<TreasureChest> chests = new ArrayList();
  private boolean inUse = false;
  private boolean didRegister = false;
  private ArmorStand a;
  
  public TreasureChest()
  {
    registerEvents();
    summonHologram();
  }
  
  public void summonHologram()
  {
    if (this.a != null) {
      return;
    }
    Location loc = getLocation();
    this.a = ((ArmorStand)loc.getWorld().spawnEntity(loc.clone().add(0.5D, -0.9D, 0.5D), EntityType.ARMOR_STAND));
    this.a.setCustomName(getDisplayName());
    this.a.setGravity(false);
    this.a.setVisible(false);
    this.a.setCustomNameVisible(true);
  }
  
  public void registerEvents()
  {
    if (this.didRegister) {
      return;
    }
    Bukkit.getPluginManager().registerEvents(this, TotemsLobby.getInstance());
    this.didRegister = true;
  }
  
  public static void registerAll()
  {
    chests.add(new CoinTreasureChest());
    chests.add(new XPTreasureChest());
  }
  
  public void unregister()
  {
    HandlerList.unregisterAll(this);
    if (this.a != null) {
      this.a.remove();
    }
  }
  
  public static void unregisterAll()
  {
    for (TreasureChest t : chests) {
      t.unregister();
    }
  }
  
  public abstract String getName();
  
  public abstract Location getLocation();
  
  public abstract String getDisplayName();
  
  public boolean isInUse()
  {
    return this.inUse;
  }
  
  public void setInUse(boolean inUse)
  {
    this.inUse = inUse;
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e)
  {
    if (e.getClickedBlock() == null) {
      return;
    }
    if (!e.getClickedBlock().getLocation().equals(getLocation())) {
      return;
    }
    Player p = e.getPlayer();
    e.setCancelled(true);
    if (this.inUse) {
      return;
    }
    setInUse(true);
    openChest(p);
  }
  
  public void changeChestState()
  {
    for (Player p : getLocation().getWorld().getPlayers())
    {
      UtilPacket.sendPacketPlayOutOpenChest(p, getLocation(), 30);
      p.playSound(getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
    }
  }
  
  public abstract void openChest(Player paramPlayer);
}
