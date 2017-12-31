package com.justinoboyle.totems.game.playerclass.mage;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.mage.sub.DefaultMage;
import com.justinoboyle.totems.game.playerclass.mage.sub.FireMage;
import com.justinoboyle.totems.game.playerclass.mage.sub.WaterMage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mage
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(8339378)), "§aMage Helmet");
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(8339378)), "§aMage Chestplate");
    ItemStack leggings = null;
    ItemStack boots = null;
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultMage(this), new FireMage(this), new WaterMage(this) };
  }
  
  public float getHealthScale()
  {
    return 0.2F;
  }
  
  public float getAttackScale()
  {
    return 0.8F;
  }
  
  public float getSpeedScale()
  {
    return 0.5F;
  }
}
