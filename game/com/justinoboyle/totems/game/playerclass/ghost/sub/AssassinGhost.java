package com.justinoboyle.totems.game.playerclass.ghost.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.ghost.item.ItemAssassinsBlade;
import com.justinoboyle.totems.game.playerclass.ghost.item.ItemInvisibilityPowder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AssassinGhost
  extends SubClass
{
  public AssassinGhost(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Assassin Ghost";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
    p.getInventory().addItem(new ItemStack[] { new ItemInvisibilityPowder().getStack() });
    p.getInventory().addItem(new ItemStack[] { new ItemAssassinsBlade().getStack() });
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(Material.INK_SACK, 1, (short)8);
  }
}
