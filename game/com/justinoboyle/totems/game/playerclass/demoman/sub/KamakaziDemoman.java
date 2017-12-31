package com.justinoboyle.totems.game.playerclass.demoman.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.demoman.item.ItemSuicideBomb;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KamakaziDemoman
  extends SubClass
{
  public KamakaziDemoman(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Kamakazi Demoman";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD, 1));
    p.getInventory().addItem(new ItemStack[] { new ItemSuicideBomb().getStack() });
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(new ItemStack(Material.TNT));
  }
}
