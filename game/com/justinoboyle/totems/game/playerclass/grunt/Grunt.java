package com.justinoboyle.totems.game.playerclass.grunt;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.grunt.sub.DefaultGrunt;
import com.justinoboyle.totems.game.playerclass.grunt.sub.RangerGrunt;
import com.justinoboyle.totems.game.playerclass.grunt.sub.SwordsmenGrunt;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Grunt
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = null;
    ItemStack chestplate = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.fromRGB(14188339)), "§aHealer Chestplate");
    ItemStack leggings = null;
    ItemStack boots = null;
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultGrunt(this), new RangerGrunt(this), new SwordsmenGrunt(this) };
  }
  
  public float getHealthScale()
  {
    return 0.1F;
  }
  
  public float getAttackScale()
  {
    return 0.3F;
  }
  
  public float getSpeedScale()
  {
    return 1.0F;
  }
}
