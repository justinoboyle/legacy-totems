package com.justinoboyle.totems.game.playerclass.mage.sub;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.mage.item.ItemMageWand;
import com.justinoboyle.totems.game.playerclass.mage.item.ItemWaterPowder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class WaterMage
  extends SubClass
{
  public WaterMage(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Water Mage";
  }
  
  public void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemMageWand().getStack() });
    p.getInventory().setItem(1, new ItemWaterPowder().getStack());
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return ItemUtils.setName(new ItemWaterPowder().getStack(), "§a" + getName());
  }
}
