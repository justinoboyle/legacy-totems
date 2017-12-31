package com.justinoboyle.totems.game.playerclass.healer.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.healer.item.ItemBandage;
import com.justinoboyle.totems.game.playerclass.healer.item.ItemMedicKit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CombatMedic
  extends SubClass
{
  public CombatMedic(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Combat Medic";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_SWORD) });
    p.getInventory().setItem(1, new ItemMedicKit().getStack());
    p.getInventory().setItem(2, new ItemBandage().getStack());
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(Material.IRON_SWORD);
  }
}
