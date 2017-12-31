package com.justinoboyle.totems.game.playerclass.archer;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.archer.sub.DefaultArcher;
import com.justinoboyle.totems.game.playerclass.archer.sub.HuntingArcher;
import com.justinoboyle.totems.game.playerclass.archer.sub.LongrangeArcher;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Archer
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = null;
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(5000268)), "§aArcher Chestplate");
    ItemStack leggings = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(5000268)), "§aArcher Trousers");
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(10066329)), "§aArcher Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultArcher(this), new LongrangeArcher(this), new HuntingArcher(this) };
  }
  
  public float getHealthScale()
  {
    return 0.5F;
  }
  
  public float getAttackScale()
  {
    return 0.5F;
  }
  
  public float getSpeedScale()
  {
    return 0.5F;
  }
}
