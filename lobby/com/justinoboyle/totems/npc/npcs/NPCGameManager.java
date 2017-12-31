package com.justinoboyle.totems.npc.npcs;

import com.justinoboyle.totems.lobby.gui.GUIGameJoin;
import com.justinoboyle.totems.npc.NPC;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class NPCGameManager
  extends NPC
{
  public NPCGameManager(World w)
  {
    super(w);
  }
  
  public String getDisplayName()
  {
    return "§eGame Manager";
  }
  
  public String getSaveName()
  {
    return "gamemanager";
  }
  
  public EntityType getEntityType()
  {
    return EntityType.VILLAGER;
  }
  
  public void onClick(Player clicker)
  {
    GUIGameJoin i = new GUIGameJoin();
    clicker.openInventory(i.getInventory());
  }
  
  public void afterSpawn()
  {
    ((Villager)getEntity()).setProfession(Villager.Profession.LIBRARIAN);
  }
}
