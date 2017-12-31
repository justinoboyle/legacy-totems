package com.justinoboyle.totems.lobby.gui.killstreak;

import com.justinoboyle.totems.core.gui.GenericGUIMenu;
import com.justinoboyle.totems.core.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIKillstreak5
  extends GenericGUIMenu
{
  public boolean onClick(Player p, int slot)
  {
    if (slot == 18) {
      p.openInventory(new GUIKillstreakMain().getInventory());
    }
    return false;
  }
  
  public boolean onClose(Player p)
  {
    return false;
  }
  
  protected int getInventorySize()
  {
    return 27;
  }
  
  protected String getInventoryName()
  {
    return "§eKillstreak Shop";
  }
  
  protected void addItems(Inventory i)
  {
    i.setItem(10, ItemUtils.setName(new ItemStack(Material.SNOW_BALL), "§aSnowball Fight!"));
    i.setItem(11, ItemUtils.setName(new ItemStack(Material.MONSTER_EGG, 1, (short)98), "§aCats!"));
    i.setItem(12, ItemUtils.setName(new ItemStack(Material.DIAMOND), "§aDiamonds!"));
    i.setItem(13, ItemUtils.setName(new ItemStack(Material.PISTON_BASE), "§aDouble Jumper"));
    i.setItem(14, ItemUtils.setName(new ItemStack(Material.MONSTER_EGG, 1, (short)90), "§aPig Rain!"));
    i.setItem(15, ItemUtils.setName(new ItemStack(Material.NETHER_STAR), "§aMidnight Strike!"));
    i.setItem(16, ItemUtils.setName(new ItemStack(Material.RED_ROSE), "§aMidnight Strike!"));
    i.setItem(18, ItemUtils.setName(new ItemStack(Material.ARROW), "§7<- Back"));
  }
}
