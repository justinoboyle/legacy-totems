package com.justinoboyle.totems.game.playerclass.soldier.sub;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DefaultSoldier
  extends SubClass
{
  public DefaultSoldier(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Soldier";
  }
  
  public void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return ItemUtils.setName(new ItemStack(Material.STONE_SWORD), "§a" + getName());
  }
}
