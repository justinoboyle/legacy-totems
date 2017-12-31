package com.justinoboyle.totems.core.utils;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R2.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R2.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BUtils
{
  public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
  {
    CraftPlayer craftplayer = (CraftPlayer)player;
    PlayerConnection connection = craftplayer.getHandle().playerConnection;
    IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
    IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + subtitle + "'}");
    PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
    PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
    connection.sendPacket(titlePacket);
    connection.sendPacket(subtitlePacket);
  }
  
  public static void sendActionBar(Player p, String msg)
  {
    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
  }
  
  public static void sendHeaderAndFooter(Player p, String head, String foot)
  {
    CraftPlayer craftplayer = (CraftPlayer)p;
    PlayerConnection connection = craftplayer.getHandle().playerConnection;
    IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{'color': '', 'text': '" + head + "'}");
    IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{'color': '', 'text': '" + foot + "'}");
    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
    try
    {
      Field headerField = packet.getClass().getDeclaredField("a");
      headerField.setAccessible(true);
      headerField.set(packet, header);
      headerField.setAccessible(!headerField.isAccessible());
      
      Field footerField = packet.getClass().getDeclaredField("b");
      footerField.setAccessible(true);
      footerField.set(packet, footer);
      footerField.setAccessible(!footerField.isAccessible());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    connection.sendPacket(packet);
  }
}
