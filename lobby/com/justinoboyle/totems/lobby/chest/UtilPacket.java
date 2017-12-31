package com.justinoboyle.totems.lobby.chest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UtilPacket
{
  public static void sendPacketPlayOutOpenChest(Player player, Location location, int radius)
  {
    try
    {
      Class<?> world = NMSUtils.getNMSClass("World");
      Object ws = NMSUtils.getHandle(location.getWorld());
      
      Class<?> bp = NMSUtils.getNMSClass("BlockPosition");
      Object bp1 = bp.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }).newInstance(new Object[] { Integer.valueOf(location.getBlockX()), Integer.valueOf(location.getBlockY()), Integer.valueOf(location.getBlockZ()) });
      
      Class<?> ppoba = NMSUtils.getNMSClass("PacketPlayOutBlockAction");
      Class<?> bl = NMSUtils.getNMSClass("Block");
      Object iblock = NMSUtils.getMethod(world, "getType", new Class[] { bp }).invoke(ws, new Object[] { bp1 });
      Class<?> iblockd = NMSUtils.getNMSClass("IBlockData");
      Object block = iblockd.getMethod("getBlock", new Class[0]).invoke(iblock, new Object[0]);
      Object packet = ppoba.getConstructor(new Class[] { bp, bl, Integer.TYPE, Integer.TYPE }).newInstance(new Object[] { bp1, block, Integer.valueOf(1), Integer.valueOf(1) });
      sendPacket(player, packet);
      for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
        if ((e instanceof Player)) {
          sendPacket(player, packet);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void sendPacket(Player player, Object packet)
  {
    try
    {
      Object nmsPlayer = NMSUtils.getHandle(player);
      Field con_field = nmsPlayer.getClass().getField("playerConnection");
      con_field.setAccessible(true);
      Object con = con_field.get(nmsPlayer);
      Method sendToPlayer = con.getClass().getMethod("sendPacket", new Class[] { NMSUtils.getNMSClass("Packet") });
      sendToPlayer.invoke(con, new Object[] { packet });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
