package com.justinoboyle.totems.game.playerclass.healer;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.healer.sub.CombatMedic;
import com.justinoboyle.totems.game.playerclass.healer.sub.DefaultHealer;
import com.justinoboyle.totems.game.playerclass.healer.sub.ShamanMedic;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Healer
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(11685080)), "§aHealer Helmet");
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(15066419)), "§aHealer Chestplate");
    ItemStack leggings = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.fromRGB(11685080)), "§aHealer Trousers");
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(11685080)), "§aHealer Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultHealer(this), new CombatMedic(this), new ShamanMedic(this) };
  }
  
  public float getHealthScale()
  {
    return 0.9F;
  }
  
  public float getAttackScale()
  {
    return 0.3F;
  }
  
  public float getSpeedScale()
  {
    return 0.5F;
  }
}
