package com.justinoboyle.totems.game.playerclass.ghost.sub;

import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import com.justinoboyle.totems.game.playerclass.ghost.item.ItemInvisibilityPowder;
import com.justinoboyle.totems.game.playerclass.ghost.item.ItemTeamSwapPowder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SwapperGhost
  extends SubClass
{
  public SwapperGhost(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Swapper Ghost";
  }
  
  protected void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
    p.getInventory().addItem(new ItemStack[] { new ItemInvisibilityPowder().getStack() });
    p.getInventory().addItem(new ItemStack[] { new ItemTeamSwapPowder().getStack() });
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return new ItemStack(Material.INK_SACK, 1, (short)8);
  }
}
