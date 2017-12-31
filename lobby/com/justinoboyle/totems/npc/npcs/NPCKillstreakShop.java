package com.justinoboyle.totems.npc.npcs;

import com.justinoboyle.totems.lobby.gui.killstreak.GUIKillstreakMain;
import com.justinoboyle.totems.npc.NPC;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class NPCKillstreakShop
  extends NPC
{
  public NPCKillstreakShop(World w)
  {
    super(w);
  }
  
  public String getDisplayName()
  {
    return "§eKillstreak Shop";
  }
  
  public String getSaveName()
  {
    return "killstreakshop";
  }
  
  public EntityType getEntityType()
  {
    return EntityType.VILLAGER;
  }
  
  public void onClick(Player clicker)
  {
    clicker.playSound(clicker.getLocation(), Sound.LEVEL_UP, 2.0F, 2.0F);
    clicker.openInventory(new GUIKillstreakMain().getInventory());
  }
  
  public void afterSpawn()
  {
    ((Villager)getEntity()).setProfession(Villager.Profession.BLACKSMITH);
  }
}
