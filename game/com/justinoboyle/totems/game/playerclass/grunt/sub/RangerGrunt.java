package com.justinoboyle.totems.game.playerclass.grunt.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RangerGrunt
  extends SubClass
{
  public RangerGrunt(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Ranger Grunt";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD_SWORD) });
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BOW) });
    
    p.getInventory().setItem(4, new ItemStack(Material.ARROW, 64));
    
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(Material.BOW);
  }
}
