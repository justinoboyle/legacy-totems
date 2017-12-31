package com.justinoboyle.totems.game.gui.selectclass;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.gui.GUIMenu;
import com.justinoboyle.totems.game.playerclass.ClassList;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ClassSelectionGUI
  extends GUIMenu
{
  public boolean onClick(final PlayerData p, int slot)
  {
    int[] slots = { 4, 10, 11, 12, 13, 14, 15, 16, 21, 23 };
    for (int count = 0; count < ClassList.CLASSES.length; count++) {
      if (slot == slots[count])
      {
        final PlayerClass c = ClassList.CLASSES[count];
        if (c.getSubClasses().length == 1)
        {
          p.setPlayerClass(c.getDefaultSubClass());
          p.spawnPlayer();
          return false;
        }
        p.getPlayer().closeInventory();
        shutdown();
        new BukkitRunnable()
        {
          public void run()
          {
            p.getPlayer().performCommand("kit " + c.getDefaultSubClass().getName());
          }
        }.runTaskLater(Totems.getInstance(), 2L);
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
    return "Select Kit";
  }
  
  protected void addItems(Inventory i)
  {
    int[] slots = { 4, 10, 11, 12, 13, 14, 15, 16, 21, 23 };
    for (int count = 0; count < ClassList.CLASSES.length; count++) {
      try
      {
        i.setItem(slots[count], ItemUtils.setName(ClassList.CLASSES[count].getDefaultSubClass().getDisplayItem(), "§a" + ClassList.CLASSES[count].getDefaultSubClass().getName()));
      }
      catch (Exception localException) {}
    }
  }
}
