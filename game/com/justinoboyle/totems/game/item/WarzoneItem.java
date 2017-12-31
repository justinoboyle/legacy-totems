package com.justinoboyle.totems.game.item;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public abstract class WarzoneItem
{
  public abstract ItemStack getStack();
  
  public abstract void onRightClickBlock(Block paramBlock);
  
  public abstract void onLeftClickBlock(Block paramBlock);
  
  public abstract void onRightClickEntity(Entity paramEntity);
  
  public abstract boolean onLeftClickEntity(Entity paramEntity);
  
  public abstract boolean onItemDrop(Item paramItem);
}
