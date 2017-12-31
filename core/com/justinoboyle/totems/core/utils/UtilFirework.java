package com.justinoboyle.totems.core.utils;

import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class UtilFirework
{
  public static void shootFirework(Location loc)
  {
    Firework firework = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
    FireworkMeta fm = firework.getFireworkMeta();
    Random r = new Random();
    FireworkEffect.Type type = null;
    int fType = r.nextInt(5) + 1;
    switch (fType)
    {
    case 1: 
    default: 
      type = FireworkEffect.Type.BALL;
      break;
    case 2: 
      type = FireworkEffect.Type.BALL_LARGE;
      break;
    case 3: 
      type = FireworkEffect.Type.BURST;
      break;
    case 4: 
      type = FireworkEffect.Type.CREEPER;
      break;
    case 5: 
      type = FireworkEffect.Type.STAR;
    }
    int c1i = r.nextInt(16) + 1;
    int c2i = r.nextInt(16) + 1;
    Color c1 = getColor(c1i);
    Color c2 = getColor(c2i);
    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
    fm.addEffect(effect);
    int power = r.nextInt(2) + 1;
    fm.setPower(power);
    firework.setFireworkMeta(fm);
  }
  
  private static Color getColor(int c)
  {
    switch (c)
    {
    case 1: 
    default: 
      return Color.AQUA;
    case 2: 
      return Color.BLACK;
    case 3: 
      return Color.BLUE;
    case 4: 
      return Color.FUCHSIA;
    case 5: 
      return Color.GRAY;
    case 6: 
      return Color.GREEN;
    case 7: 
      return Color.LIME;
    case 8: 
      return Color.MAROON;
    case 9: 
      return Color.NAVY;
    case 10: 
      return Color.OLIVE;
    case 11: 
      return Color.ORANGE;
    case 12: 
      return Color.PURPLE;
    case 13: 
      return Color.RED;
    case 14: 
      return Color.SILVER;
    case 15: 
      return Color.TEAL;
    }
    return Color.WHITE;
  }
}
