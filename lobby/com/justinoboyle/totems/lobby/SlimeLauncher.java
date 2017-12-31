package com.justinoboyle.totems.lobby;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class SlimeLauncher
  implements Listener
{
  @EventHandler
  public void move(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    if (!p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK)) {
      return;
    }
    p.setVelocity(p.getLocation().getDirection().multiply(2.4D).setY(1.2D));
  }
}
