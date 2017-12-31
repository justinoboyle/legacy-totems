package com.justinoboyle.totems.npc.npcs;

import com.justinoboyle.totems.npc.NPC;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class NPCGadgetDealer
  extends NPC
{
  public NPCGadgetDealer(World w)
  {
    super(w);
  }
  
  public String getDisplayName()
  {
    return "§eGadget Dealer";
  }
  
  public String getSaveName()
  {
    return "gadgetdealer";
  }
  
  public EntityType getEntityType()
  {
    return EntityType.VILLAGER;
  }
  
  public void onClick(Player clicker)
  {
    say(clicker, new String[] { "Coming soon!" });
    clicker.playSound(clicker.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
  }
  
  public void afterSpawn()
  {
    ((Villager)getEntity()).setProfession(Villager.Profession.BLACKSMITH);
  }
}
