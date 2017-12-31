package com.justinoboyle.totems.game.playerclass;

import com.justinoboyle.totems.game.item.WarzoneItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class SubClass
{
  private PlayerClass parent;
  
  public SubClass(PlayerClass parent)
  {
    this.parent = parent;
  }
  
  public abstract String getName();
  
  protected abstract void onSpawn(Player paramPlayer);
  
  public abstract ItemStack getDisplayItem();
  
  public void spawnPlayer(Player p)
  {
    onSpawn(p);
    WarzoneItem[] armor = this.parent.getArmorContents();
    p.getInventory().setHelmet(armor[0].getStack());
    p.getInventory().setChestplate(armor[1].getStack());
    p.getInventory().setLeggings(armor[2].getStack());
    p.getInventory().setBoots(armor[3].getStack());
  }
  
  public PlayerClass getParent()
  {
    return this.parent;
  }
}
