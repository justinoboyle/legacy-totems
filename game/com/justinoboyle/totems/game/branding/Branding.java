package com.justinoboyle.totems.game.branding;

import com.justinoboyle.totems.core.utils.BUtils;
import com.justinoboyle.totems.core.utils.MOTDCenterUtil;
import com.justinoboyle.totems.game.core.Totems;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;

public class Branding
  implements Listener
{
  private static final String tabMenuHeader = "\n§7           Online Players: §b{players}           \n";
  private static final String tabMenuFooter = "\n§7- §etotems.us§7 -\n";
  
  public Branding()
  {
    Bukkit.getPluginManager().registerEvents(this, Totems.getInstance());
    updateHeaderAndFooter();
  }
  
  public String[] getHeaderAndFooter()
  {
    return new String[] { "\n§7           Online Players: §b{players}           \n".replace("{players}", Bukkit.getOnlinePlayers().size()), "\n§7- §etotems.us§7 -\n" };
  }
  
  private void updateHeaderAndFooter()
  {
    String[] a = getHeaderAndFooter();
    for (Player p : Bukkit.getOnlinePlayers()) {
      BUtils.sendHeaderAndFooter(p, a[0], a[1]);
    }
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    updateHeaderAndFooter();
  }
  
  @EventHandler
  public void motd(ServerListPingEvent e)
  {
    e.setMotd(MOTDCenterUtil.center("§btotems.us"));
    e.setMaxPlayers(-1);
  }
}
