package com.justinoboyle.totems.game.playerclass.demoman;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.demoman.sub.DefaultDemoman;
import com.justinoboyle.totems.game.playerclass.demoman.sub.GrenadiereDemoman;
import com.justinoboyle.totems.game.playerclass.demoman.sub.KamakaziDemoman;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ClassDemoman
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(16777215)), "§aDemoman Helmet");
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(10040115)), "§aDemoman Chestplate");
    ItemStack leggings = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(10040115)), "§aDemoman Trousers");
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(10040115)), "§aDemoman Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultDemoman(this), new GrenadiereDemoman(this), new KamakaziDemoman(this) };
  }
  
  public float getHealthScale()
  {
    return 0.9F;
  }
  
  public float getAttackScale()
  {
    return 0.5F;
  }
  
  public float getSpeedScale()
  {
    return 0.2F;
  }
}
