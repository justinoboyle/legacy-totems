package com.justinoboyle.totems.game.playerclass.demoman.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.demoman.item.ItemMegaBomb;
import com.justinoboyle.totems.game.playerclass.demoman.item.ItemMiniBomb;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DefaultDemoman
  extends SubClass
{
  public DefaultDemoman(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Demoman";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
    for (int i = 0; i < 6; i++) {
      p.getInventory().addItem(new ItemStack[] { new ItemMiniBomb().getStack() });
    }
    p.getInventory().addItem(new ItemStack[] { new ItemMegaBomb().getStack() });
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(Material.TNT);
  }
}
