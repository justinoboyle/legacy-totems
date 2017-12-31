package com.justinoboyle.totems.game.gui;

import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.core.Totems;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class GUIMenu
  implements Listener
{
  private Inventory inv;
  
  public abstract boolean onClick(PlayerData paramPlayerData, int paramInt);
  
  public abstract boolean onClose(PlayerData paramPlayerData);
  
  protected abstract int getInventorySize();
  
  protected abstract String getInventoryName();
  
  protected abstract void addItems(Inventory paramInventory);
  
  public Inventory getInventory()
  {
    if (this.inv == null)
    {
      this.inv = Bukkit.createInventory(null, getInventorySize(), getInventoryName());
      addItems(getInventory());
      listen();
    }
    return this.inv;
  }
  
  private void listen()
  {
    if (this.inv != null) {
      Bukkit.getPluginManager().registerEvents(this, Totems.getInstance());
    }
  }
  
  public void shutdown()
  {
    try
    {
      for (HumanEntity p : this.inv.getViewers()) {
        p.closeInventory();
      }
    }
    catch (NullPointerException localNullPointerException) {}
    HandlerList.unregisterAll(this);
    this.inv = null;
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e)
  {
    if (e.getInventory().hashCode() != this.inv.hashCode()) {
      return;
    }
    Player p = (Player)e.getWhoClicked();
    for (PlayerData d : Totems.getInstance().getArena().data) {
      if (d.getPlayer().equals(p)) {
        try
        {
          onClick(d, e.getSlot());
          e.setCancelled(true);
        }
        catch (Exception localException) {}
      }
    }
  }
  
  @EventHandler
  public void onClose(InventoryCloseEvent e)
  {
    if (e.getInventory().hashCode() != this.inv.hashCode()) {
      return;
    }
    Player p = (Player)e.getPlayer();
    for (PlayerData d : Totems.getInstance().getArena().data) {
      if (d.getPlayer().equals(p))
      {
        if (onClose(d))
        {
          e.getPlayer().openInventory(this.inv);
          return;
        }
        new BukkitRunnable()
        {
          public void run()
          {
            try
            {
              if ((GUIMenu.this.inv.getViewers() == null) || (GUIMenu.this.inv.getViewers().size() == 0)) {
                GUIMenu.this.shutdown();
              }
            }
            catch (NullPointerException e)
            {
              GUIMenu.this.shutdown();
            }
          }
        }.runTaskLater(Totems.getInstance(), 5L);
      }
    }
  }
}
