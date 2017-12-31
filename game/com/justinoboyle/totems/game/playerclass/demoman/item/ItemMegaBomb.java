package com.justinoboyle.totems.game.playerclass.demoman.item;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.WarzoneItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemMegaBomb
  extends WarzoneItem
{
  public ItemStack getStack()
  {
    return ItemUtils.setName(new ItemStack(Material.TNT), "§cMega-Bomb");
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
