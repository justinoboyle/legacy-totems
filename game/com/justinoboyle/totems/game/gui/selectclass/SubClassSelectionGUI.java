package com.justinoboyle.totems.game.gui.selectclass;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.gui.GUIMenu;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SubClassSelectionGUI
  extends GUIMenu
{
  private PlayerClass c;
  
  public SubClassSelectionGUI(PlayerClass c)
  {
    this.c = c;
  }
  
  public boolean onClick(PlayerData p, int slot)
  {
    SubClass[] arrayOfSubClass;
    int j = (arrayOfSubClass = this.c.getSubClasses()).length;
    for (int i = 0; i < j; i++)
    {
      SubClass c2 = arrayOfSubClass[i];
      if (ChatColor.stripColor(getInventory().getItem(slot).getItemMeta().getDisplayName()).equalsIgnoreCase(c2.getName()))
      {
        p.setPlayerClass(c2);
        p.spawnPlayer();
      }
    }
    return false;
  }
  
  public boolean onClose(PlayerData p)
  {
    return false;
  }
  
  protected int getInventorySize()
  {
    return 27;
  }
  
  protected String getInventoryName()
  {
    return this.c.getDefaultSubClass().getName();
  }
  
  protected void addItems(Inventory i)
  {
    int i2 = 0;
    SubClass[] arrayOfSubClass;
    int j = (arrayOfSubClass = this.c.getSubClasses()).length;
    for (int i = 0; i < j; i++)
    {
      SubClass c2 = arrayOfSubClass[i];
      i.setItem(i2 + 9, ItemUtils.setName(c2.getDisplayItem(), "§a" + c2.getName()));
      i2++;
    }
  }
}
