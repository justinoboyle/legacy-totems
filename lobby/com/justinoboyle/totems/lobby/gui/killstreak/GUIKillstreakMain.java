package com.justinoboyle.totems.lobby.gui.killstreak;

import com.justinoboyle.totems.core.gui.GenericGUIMenu;
import com.justinoboyle.totems.core.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIKillstreakMain
  extends GenericGUIMenu
{
  public boolean onClick(Player p, int slot)
  {
    if (slot == 11) {
      p.openInventory(new GUIKillstreak5().getInventory());
    }
    if (slot == 13) {
      p.openInventory(new GUIKillstreak15().getInventory());
    }
    if (slot == 15) {
      p.openInventory(new GUIKillstreak25().getInventory());
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
    i.setItem(11, ItemUtils.setName(new ItemStack(Material.CLAY_BRICK), "§b5 §7Killstreak"));
    i.setItem(13, ItemUtils.setName(new ItemStack(Material.IRON_INGOT), "§b15 §7Killstreak"));
    i.setItem(15, ItemUtils.setName(new ItemStack(Material.GOLD_INGOT), "§b25 §7Killstreak"));
  }
}
