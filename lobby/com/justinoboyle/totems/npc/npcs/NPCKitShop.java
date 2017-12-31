package com.justinoboyle.totems.npc.npcs;

import com.justinoboyle.totems.core.gui.GenericGUIMenu;
import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.db.DBPlayer;
import com.justinoboyle.totems.game.playerclass.ClassList;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.lobby.TotemsLobby;
import com.justinoboyle.totems.npc.NPC;
import com.justinoboyle.totems.util.UtilGlow;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

public class NPCKitShop
  extends NPC
  implements Listener
{
  public NPCKitShop(World w)
  {
    super(w);
    Bukkit.getPluginManager().registerEvents(this, TotemsLobby.getInstance());
  }
  
  public String getDisplayName()
  {
    return "§eKit Shop";
  }
  
  public String getSaveName()
  {
    return "kitshop";
  }
  
  public EntityType getEntityType()
  {
    return EntityType.VILLAGER;
  }
  
  public void onClick(Player clicker)
  {
    final DBPlayer db = DBPlayer.loadPlayer(clicker);
    clicker.openInventory(new GenericGUIMenu()
    {
      final String[] unlockedLore = "§aYou already have\n§athis kit unlocked!\n\n§7Click to view\n§7sub-kits.".split("\n");
      
      public boolean onClose(Player p)
      {
        return false;
      }
      
      public boolean onClick(Player p, int slot)
      {
        return false;
      }
      
      protected int getInventorySize()
      {
        return 27;
      }
      
      protected String getInventoryName()
      {
        return "Kits";
      }
      
      protected void addItems(Inventory i)
      {
        PlayerClass[] arrayOfPlayerClass;
        int j = (arrayOfPlayerClass = ClassList.CLASSES).length;
        for (int i = 0; i < j; i++)
        {
          PlayerClass c = arrayOfPlayerClass[i];
          String[] lockedLore = ("§cThis kit is locked.\n\n§7Click to pay\n§6" + c.getPrice() + " Coins\n§7to unlock.").split("\n");
          boolean unlocked = db.hasUnlockedClass(c);
          String[] lore = unlocked ? this.unlockedLore : lockedLore;
          String name = (unlocked ? "§a" : "§c") + WordUtils.capitalize(c.getDefaultSubClass().getName().replace("_", " "));
          ItemStack i2 = ItemUtils.setNameAndLore(c.getDefaultSubClass().getDisplayItem(), name, lore);
          if (unlocked) {
            i2 = UtilGlow.addGlow(i2);
          }
          i.addItem(new ItemStack[] { i2 });
        }
      }
    }.getInventory());
  }
  
  public void afterSpawn()
  {
    ((Villager)getEntity()).setProfession(Villager.Profession.FARMER);
  }
}
