package com.justinoboyle.totems.game.playerclass.ghost.item;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.WarzoneItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemInvisibilityPowder
  extends WarzoneItem
{
  public ItemStack getStack()
  {
    return ItemUtils.setName(new ItemStack(Material.INK_SACK, 1, (short)8), "§eInvisibility Powder §8(§b3 §7uses§8)");
  }
  
  public void onRightClickBlock(Block b) {}
  
  public void onLeftClickBlock(Block b) {}
  
  public void onRightClickEntity(Entity e) {}
  
  public boolean onLeftClickEntity(Entity e)
  {
    return false;
  }
  
  public boolean onItemDrop(Item itemEntity)
  {
    return false;
  }
}
