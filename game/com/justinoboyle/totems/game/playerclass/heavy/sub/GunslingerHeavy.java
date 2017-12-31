package com.justinoboyle.totems.game.playerclass.heavy.sub;

import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class GunslingerHeavy
  extends SubClass
{
  public GunslingerHeavy(PlayerClass parent)
  {
    super(parent);
  }
  
  public String getName()
  {
    return "Gunslinger Heavy";
  }
  
  public void onSpawn(Player p)
  {
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BOW) });
    p.getInventory().setItem(4, new ItemStack(Material.ARROW, 64));
    p.getInventory().setItem(7, new ItemStack(Material.WOOD, 64));
    p.getInventory().setItem(8, new ItemStack(Material.WOOD, 64));
  }
  
  public ItemStack getDisplayItem()
  {
    return ItemUtils.setName(new ItemStack(Material.BOW), "§a" + getName());
  }
}
