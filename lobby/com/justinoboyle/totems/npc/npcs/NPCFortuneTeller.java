package com.justinoboyle.totems.npc.npcs;

import com.justinoboyle.totems.core.utils.YesNoListener;
import com.justinoboyle.totems.npc.NPC;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class NPCFortuneTeller
  extends NPC
{
  private List<String> possibleResponses = new ArrayList();
  private YesNoListener yesNo;
  
  public NPCFortuneTeller(World w)
  {
    super(w);
    this.yesNo = new YesNoListener("§7You understand that you\n§7have a §cchance§7 of either\n§closing §7or §againing §7coins.", "§cWARNING! You may lose coins!", false);
  }
  
  public String getDisplayName()
  {
    return "§eFortune Teller";
  }
  
  public String getSaveName()
  {
    return "fortuneteller";
  }
  
  public EntityType getEntityType()
  {
    return EntityType.VILLAGER;
  }
  
  public void onClick(Player clicker)
  {
    say(clicker, new String[] { "I'm not ready yet! Come back later!" });
    clicker.playSound(clicker.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
  }
  
  public void afterSpawn()
  {
    ((Villager)getEntity()).setProfession(Villager.Profession.PRIEST);
  }
}
