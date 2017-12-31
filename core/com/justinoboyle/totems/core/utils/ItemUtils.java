package com.justinoboyle.totems.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils
{
  public static ItemStack setName(ItemStack is, String name)
  {
    name = ChatColor.translateAlternateColorCodes('&', name);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(name);
    is.setItemMeta(im);
    return is;
  }
  
  public static ItemStack setLore(ItemStack is, String... lore)
  {
    ItemMeta meta = is.getItemMeta();
    List<String> newLore = new ArrayList();
    if (lore.length >= 1)
    {
      List<String> lines = Arrays.asList(lore);
      for (String s : lines) {
        newLore.add(ChatColor.translateAlternateColorCodes('&', s));
      }
    }
    meta.setLore(newLore);
    is.setItemMeta(meta);
    return is;
  }
  
  public static ItemStack setNameAndLore(ItemStack itemStack, String name, String... lore)
  {
    itemStack = setName(itemStack, name);
    itemStack = setLore(itemStack, lore);
    return itemStack;
  }
  
  public static ItemStack coloredArmor(ItemStack i, Color c)
  {
    LeatherArmorMeta lam = (LeatherArmorMeta)i.getItemMeta();
    lam.setColor(c);
    i.setItemMeta(lam);
    return i;
  }
  
  public static ItemStack getSkull(String playerName)
  {
    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta meta3 = (SkullMeta)skull.getItemMeta();
    meta3.setOwner(playerName);
    skull.setItemMeta(meta3);
    return skull;
  }
}
