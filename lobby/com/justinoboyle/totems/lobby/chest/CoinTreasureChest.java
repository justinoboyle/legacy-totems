package com.justinoboyle.totems.lobby.chest;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.db.DBPlayer;
import com.justinoboyle.totems.lobby.TotemsLobby;
import com.justinoboyle.totems.lobby.loc.LocationManager;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CoinTreasureChest
  extends TreasureChest
{
  private Location location;
  private boolean isCancelled = false;
  
  public CoinTreasureChest()
  {
    registerEvents();
    summonHologram();
  }
  
  public void openChest(final Player p)
  {
    setInUse(true);
    changeChestState();
    int amount = new Random().nextInt(5000) + 5000;
    DBPlayer.loadPlayer(p).setCoins(DBPlayer.loadPlayer(p).getCoins() + amount);
    p.sendMessage("§6+" + amount + " Coins");
    final CoinTreasureChest inst = this;
    new BukkitRunnable()
    {
      public void run()
      {
        final byte data = inst.getLocation().getBlock().getData();
        inst.getLocation().getBlock().setType(Material.AIR);
        new BukkitRunnable()
        {
          public void run()
          {
            this.val$inst.getLocation().getBlock().setType(Material.CHEST);
            this.val$inst.getLocation().getBlock().setData(data);
          }
        }.runTaskLater(TotemsLobby.getInstance(), 1L);
        
        inst.setInUse(false);
      }
    }.runTaskLater(TotemsLobby.getInstance(), 60L);
    for (int i = 0; i < 30; i++)
    {
      final int i3 = i;
      new BukkitRunnable()
      {
        public void run()
        {
          ItemStack i2 = ItemUtils.setName(new ItemStack(Material.DOUBLE_PLANT), i3);
          final Item i4 = p.getWorld().dropItem(CoinTreasureChest.this.getLocation().clone().add(0.5D, 1.0D, 0.5D), i2);
          i4.setPickupDelay(Integer.MAX_VALUE);
          i4.setTicksLived(4900);
          i4.setVelocity(new Vector(new Random().nextDouble() * 0.2D * (new Random().nextBoolean() ? 1 : -1), new Random().nextDouble() * 0.7D, new Random().nextDouble() * 0.2D * (new Random().nextBoolean() ? 1 : -1)));
          Player p2;
          for (Iterator localIterator = p.getWorld().getPlayers().iterator(); localIterator.hasNext(); p2.playSound(CoinTreasureChest.this.getLocation(), Sound.ITEM_PICKUP, 1.0F, 0.0F)) {
            p2 = (Player)localIterator.next();
          }
          new BukkitRunnable()
          {
            public void run()
            {
              i4.remove();
            }
          }.runTaskLater(TotemsLobby.getInstance(), 20L);
        }
      }.runTaskLater(TotemsLobby.getInstance(), i * 2);
    }
  }
  
  public String getName()
  {
    return "coins";
  }
  
  public String getDisplayName()
  {
    return "§6Coin Crate";
  }
  
  public Location getLocation()
  {
    if (this.location != null) {
      return this.location;
    }
    try
    {
      return LocationManager.getLocation(((World)Bukkit.getWorlds().get(0)).getName(), "coinschest");
    }
    catch (FileNotFoundException e) {}
    return null;
  }
}
