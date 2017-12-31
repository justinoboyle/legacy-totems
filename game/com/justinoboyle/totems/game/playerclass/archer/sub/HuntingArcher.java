package com.justinoboyle.totems.game.playerclass.archer.sub;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.archer.item.ItemHuntingBow;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HuntingArcher
  extends SubClass
{
  public HuntingArcher(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Hunting Archer";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemHuntingBow().getStack() });
    p.getInventory().setItem(3, new ItemStack(Material.ARROW, 64));
    p.getInventory().setItem(4, new ItemStack(Material.ARROW, 64));
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return ItemUtils.setName(new ItemStack(Material.BOW), "§a" + getName());
  }
}
