package com.justinoboyle.totems.game.playerclass.archer.item;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.WarzoneItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemHuntingBow
  extends WarzoneItem
{
  public ItemStack getStack()
  {
    ItemStack i = ItemUtils.setName(new ItemStack(Material.BOW), "§bHunting Bow");
    i.addUnsafeEnchantment(Enchantment.THORNS, 3);
    return i;
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
