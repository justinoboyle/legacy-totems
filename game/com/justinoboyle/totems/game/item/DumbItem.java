package com.justinoboyle.totems.game.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class DumbItem
  extends WarzoneItem
{
  private ItemStack stack;
  
  public DumbItem(ItemStack stack)
  {
    this.stack = stack;
  }
  
  public ItemStack getStack()
  {
    return this.stack;
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
