package com.justinoboyle.totems.game.playerclass.heavy;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.heavy.sub.DefaultHeavy;
import com.justinoboyle.totems.game.playerclass.heavy.sub.GunslingerHeavy;
import com.justinoboyle.totems.game.playerclass.heavy.sub.PowerhouseHeavy;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Heavy
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(10066329)), "§aHeavy Helmet");
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(10040115)), "§aHeavy Chestplate");
    ItemStack leggings = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(5000268)), "§aHeavy Trousers");
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(1644825)), "§aHeavy Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultHeavy(this), new GunslingerHeavy(this), new PowerhouseHeavy(this) };
  }
  
  public float getHealthScale()
  {
    return 0.9F;
  }
  
  public float getAttackScale()
  {
    return 0.9F;
  }
  
  public float getSpeedScale()
  {
    return 0.3F;
  }
}
