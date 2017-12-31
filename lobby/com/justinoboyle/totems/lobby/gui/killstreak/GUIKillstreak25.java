package com.justinoboyle.totems.lobby.gui.killstreak;

import com.justinoboyle.totems.core.gui.GenericGUIMenu;
import com.justinoboyle.totems.core.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIKillstreak25
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
    i.setItem(10, ItemUtils.setName(new ItemStack(Material.FLINT_AND_STEEL), "§aBurn, Baby, Burn!"));
    i.setItem(12, ItemUtils.setName(new ItemStack(Material.DRAGON_EGG), "§aDragon Dash"));
    i.setItem(14, ItemUtils.setName(new ItemStack(Material.BLAZE_ROD), "§aYou're A Wizard!"));
    i.setItem(16, ItemUtils.setName(new ItemStack(Material.CHEST), "§aSupply Drop"));
    i.setItem(18, ItemUtils.setName(new ItemStack(Material.ARROW), "§7<- Back"));
  }
}
