package com.justinoboyle.totems.game.playerclass.ghost;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.item.DumbItem;
import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.ghost.sub.AssassinGhost;
import com.justinoboyle.totems.game.playerclass.ghost.sub.DefaultGhost;
import com.justinoboyle.totems.game.playerclass.ghost.sub.SwapperGhost;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ClassGhost
  extends PlayerClass
{
  public WarzoneItem[] getArmorContents()
  {
    ItemStack helmet = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_HELMET), Color.fromRGB(5000268)), "§aGhost Helmet");
    ItemStack chestplate = null;
    ItemStack leggings = null;
    ItemStack boots = ItemUtils.setName(ItemUtils.coloredArmor(new ItemStack(Material.LEATHER_BOOTS), Color.fromRGB(5000268)), "§aGhost Boots");
    return new WarzoneItem[] { new DumbItem(helmet), new DumbItem(chestplate), new DumbItem(leggings), new DumbItem(boots) };
  }
  
  public SubClass[] getSubClasses()
  {
    return new SubClass[] { new DefaultGhost(this), new AssassinGhost(this), new SwapperGhost(this) };
  }
  
  public float getHealthScale()
  {
    return 0.5F;
  }
  
  public float getAttackScale()
  {
    return 0.7F;
  }
  
  public float getSpeedScale()
  {
    return 0.5F;
  }
}
