package com.justinoboyle.totems.game.playerclass.soldier;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.soldier.sub.DefaultSoldier;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Soldier
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(6717235)), "§aSoldier Helmet");
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(6717235)), "§aSoldier Chestplate");
    ItemStack leggings = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(6717235)), "§aSoldier Trousers");
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(1644825)), "§aSoldier Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultSoldier(this) };
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
