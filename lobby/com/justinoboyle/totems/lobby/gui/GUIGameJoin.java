package com.justinoboyle.totems.lobby.gui;

import com.justinoboyle.totems.core.gui.GenericGUIMenu;
import com.justinoboyle.totems.core.utils.BungeeTalk;
import com.justinoboyle.totems.core.utils.BungeeTalkSpecialPlayerCount;
import com.justinoboyle.totems.core.utils.BungeeUtils;
import com.justinoboyle.totems.core.utils.ItemUtils;
import com.justinoboyle.totems.lobby.TotemsLobby;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIGameJoin
  extends GenericGUIMenu
{
  public GUIGameJoin()
  {
    TotemsLobby.getInstance().getBungee().ask("GetServers");
  }
  
  public boolean onClick(Player p, int slot)
  {
    if (slot == 64537) {
      return true;
    }
    ItemStack i = getInventory().getItem(slot);
    if (i == null) {
      return true;
    }
    String serverid = "game";
    if (!i.hasItemMeta()) {
      return true;
    }
    if (!i.getItemMeta().hasDisplayName()) {
      return true;
    }
    serverid = serverid + (Integer.parseInt(org.bukkit.ChatColor.stripColor(i.getItemMeta().getDisplayName()).split(" ")[1]) - 1);
    BungeeUtils.sendToServer(p, serverid);
    return false;
  }
  
  public boolean onClose(Player p)
  {
    return false;
  }
  
  protected int getInventorySize()
  {
    return 36;
  }
  
  protected String getInventoryName()
  {
    return "Games";
  }
  
  protected void addItems(final Inventory i)
  {
    String response = TotemsLobby.getInstance().getBungee().getResponse("GetServers");
    if (response == null) {
      new BukkitRunnable()
      {
        String responseB = TotemsLobby.getInstance().getBungee().getResponse("GetServers");
        
        public void run()
        {
          this.responseB = TotemsLobby.getInstance().getBungee().getResponse("GetServers");
          if (this.responseB != null)
          {
            cancel();
            GUIGameJoin.this.add(i, this.responseB);
          }
          if ((i.getViewers() != null) && (i.getViewers().size() == 0)) {
            cancel();
          }
        }
      }.runTaskTimer(TotemsLobby.getInstance(), 1L, 2L);
    }
    if (response != null) {
      add(i, response);
    }
  }
  
  private void add(Inventory i, String input)
  {
    List<String> games = new ArrayList();
    String[] arrayOfString;
    int j = (arrayOfString = input.split(", ")).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      if (s.startsWith("game")) {
        games.add(s);
      }
    }
    Collections.sort(games);
    for (String s : games)
    {
      int displayID = Integer.parseInt(s.substring(4)) + 1;
      int onlinePlayers = TotemsLobby.getInstance().getBungee().getCountListener().getResponse(s);
      int maxPlayers = 20;
      i.addItem(new ItemStack[] { ItemUtils.setNameAndLore(new ItemStack(Material.STAINED_CLAY, 1, getData(onlinePlayers, maxPlayers)), "§7Game §b" + displayID, new String[] { "§7Online Players: §a" + onlinePlayers + "§7/" + maxPlayers }) });
    }
  }
  
  private byte getData(int online, int max)
  {
    if (online == 20) {
      return 14;
    }
    if (online >= 15) {
      return 6;
    }
    if (online >= 5) {
      return 5;
    }
    if (online >= 0) {
      return 13;
    }
    return 13;
  }
}
