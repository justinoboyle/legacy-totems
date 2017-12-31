package com.justinoboyle.totems.lobby;

import com.justinoboyle.totems.core.utils.BungeeTalk;
import com.justinoboyle.totems.core.utils.BungeeTalkSpecialPlayerCount;
import com.justinoboyle.totems.db.DBPlayer;
import com.justinoboyle.totems.lobby.chest.TreasureChest;
import com.justinoboyle.totems.lobby.loc.LocationManager;
import com.justinoboyle.totems.lobby.loc.Team;
import com.justinoboyle.totems.lobby.scoreboard.LobbyScoreboard;
import com.justinoboyle.totems.lobby.scoreboard.LobbyStatListener;
import com.justinoboyle.totems.lobby.scoreboard.PlayerScoreboard;
import com.justinoboyle.totems.lobby.scoreboard.TotemsScoreboard;
import com.justinoboyle.totems.npc.NPC;
import com.justinoboyle.totems.npc.npcs.NPCBookKeeper;
import com.justinoboyle.totems.npc.npcs.NPCFortuneTeller;
import com.justinoboyle.totems.npc.npcs.NPCGadgetDealer;
import com.justinoboyle.totems.npc.npcs.NPCGameManager;
import com.justinoboyle.totems.npc.npcs.NPCKillstreakShop;
import com.justinoboyle.totems.npc.npcs.NPCKitShop;
import com.justinoboyle.totems.npc.npcs.NPCPlayerStats;
import com.justinoboyle.totems.rank.PlayerRank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemsLobby
  extends JavaPlugin
{
  private static TotemsLobby instance;
  private List<NPC> npcList;
  private BungeeTalk bungee;
  private TotemsScoreboard scoreboard;
  
  public void onEnable()
  {
    instance = this;
    this.scoreboard = new TotemsScoreboard();
    
    this.npcList = new ArrayList();
    Bukkit.getServer().getPluginManager().registerEvents(new SlimeLauncher(), this);
    Iterator localIterator = Bukkit.getWorlds().iterator();
    Object localObject2;
    if (localIterator.hasNext())
    {
      World w = (World)localIterator.next();
      for (localObject2 = w.getEntities().iterator(); ((Iterator)localObject2).hasNext();)
      {
        e = (Entity)((Iterator)localObject2).next();
        if ((!e.getType().equals(EntityType.ITEM_FRAME)) && (!e.getType().equals(EntityType.PLAYER))) {
          e.remove();
        }
      }
      this.npcList.add(new NPCBookKeeper(w));
      this.npcList.add(new NPCFortuneTeller(w));
      this.npcList.add(new NPCGadgetDealer(w));
      this.npcList.add(new NPCGameManager(w));
      this.npcList.add(new NPCKillstreakShop(w));
      this.npcList.add(new NPCKitShop(w));
      this.npcList.add(new NPCPlayerStats(w));
    }
    Bukkit.getServer().getPluginManager().registerEvents(new WorldListener(), this);
    
    this.bungee = new BungeeTalk(this);
    new BukkitRunnable()
    {
      public void run()
      {
        if (TotemsLobby.this.bungee.getResponses().containsKey("GetServers"))
        {
          String[] spl = TotemsLobby.this.bungee.getResponse("GetServers").split(", ");
          String[] arrayOfString1;
          int j = (arrayOfString1 = spl).length;
          for (int i = 0; i < j; i++)
          {
            String s = arrayOfString1[i];
            TotemsLobby.this.bungee.getCountListener().ask(new String[] { "PlayerCount", s });
          }
        }
        else
        {
          TotemsLobby.this.bungee.ask("GetServers");
        }
      }
    }.runTaskTimer(this, 2L, 5L);
    new BukkitRunnable()
    {
      private HashMap<OfflinePlayer, String> values = new HashMap();
      
      public void run()
      {
        for (Player p2 : )
        {
          OfflinePlayer p = p2;
          String message = PlayerRank.getScoreboardPrefix(p);
          if ((!this.values.containsKey(p)) || (!((String)this.values.get(p)).equals(message)))
          {
            if (this.values.containsKey(p)) {
              this.values.remove(p);
            }
            this.values.put(p, message);
            TotemsLobby.this.scoreboard.setName(p2, message, "");
          }
        }
        Iterator<OfflinePlayer> listIterator = this.values.keySet().iterator();
        while (listIterator.hasNext())
        {
          OfflinePlayer p = (OfflinePlayer)listIterator.next();
          if (!p.isOnline()) {
            this.values.remove(p);
          }
        }
      }
    }.runTaskTimer(this, 2L, 2L);
    Entity e = (localObject2 = Team.values()).length;
    for (Object localObject1 = 0; localObject1 < e; localObject1++)
    {
      Team t = localObject2[localObject1];t.createTotemHolograms();
    }
    new LobbyStatListener();
    
    TreasureChest.registerAll();
    Player p;
    for (localObject1 = Bukkit.getOnlinePlayers().iterator(); ((Iterator)localObject1).hasNext(); LobbyScoreboard.updateScoreboard(p)) {
      p = (Player)((Iterator)localObject1).next();
    }
  }
  
  public void onDisable()
  {
    Object localObject;
    int j = (localObject = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = localObject[i];t.reset();
    }
    for (NPC npc : this.npcList) {
      npc.destroy();
    }
    this.npcList.clear();
    this.npcList = null;
    for (??? = Bukkit.getWorlds().iterator(); ???.hasNext(); ((Iterator)localObject).hasNext())
    {
      World w = (World)???.next();
      localObject = w.getEntities().iterator(); continue;Entity e = (Entity)((Iterator)localObject).next();
      if ((!e.getType().equals(EntityType.ITEM_FRAME)) && (!e.getType().equals(EntityType.PLAYER))) {
        e.remove();
      }
    }
    HandlerList.unregisterAll(this);
    instance = null;
  }
  
  public static TotemsLobby getInstance()
  {
    return instance;
  }
  
  public BungeeTalk getBungee()
  {
    return this.bungee;
  }
  
  public TotemsScoreboard getScoreboard()
  {
    return this.scoreboard;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (label.equalsIgnoreCase("downto0"))
    {
      if (!(sender instanceof Player)) {
        return false;
      }
      Player p = (Player)sender;
      if (!PlayerRank.hasRank(p, PlayerRank.ADMIN)) {
        return false;
      }
      DBPlayer.loadPlayer(p).setExpScaled(0);
      DBPlayer.loadPlayer(p).save();
    }
    if (label.equalsIgnoreCase("roadto75"))
    {
      if (!(sender instanceof Player)) {
        return false;
      }
      final Player p = (Player)sender;
      if (!PlayerRank.hasRank(p, PlayerRank.ADMIN)) {
        return false;
      }
      DBPlayer.loadPlayer(p).setExpScaled(0);
      DBPlayer.loadPlayer(p).save();
      new BukkitRunnable()
      {
        public void run()
        {
          int level = DBPlayer.loadPlayer(p).getLevel();
          if (level >= 75)
          {
            cancel();
            return;
          }
          DBPlayer.loadPlayer(p).setExpScaled(DBPlayer.loadPlayer(p).getExpScaled() + 60000, false);
        }
      }.runTaskTimer(this, 0L, 1L);
    }
    if (label.equalsIgnoreCase("xp")) {
      sender.sendMessage("§dYou have " + DBPlayer.loadPlayer((Player)sender).getExpScaled() + " experience.");
    }
    String title;
    if (label.equals("sidebar"))
    {
      File f = new File("sidebar");
      if (f.exists()) {
        f.delete();
      }
      try
      {
        PrintWriter pw = new PrintWriter(f);
        String[] arrayOfString1;
        int j = (arrayOfString1 = args).length;
        for (int i = 0; i < j; i++)
        {
          String s = arrayOfString1[i];pw.println(s);
        }
        pw.close();
      }
      catch (Exception localException) {}
      int i = 0;
      title = "";
      Object messages = new ArrayList();
      String[] arrayOfString2;
      int m = (arrayOfString2 = args).length;
      for (int k = 0; k < m; k++)
      {
        String s = arrayOfString2[k];
        if (s.length() > 16)
        {
          sender.sendMessage("Trimming \"" + s + "\" to 16 characters!");
          s = s.substring(0, 16);
        }
        if (i == 0) {
          title = ChatColor.translateAlternateColorCodes('&', s.replace("_", " "));
        } else {
          ((List)messages).add(ChatColor.translateAlternateColorCodes('&', s.replace("_", " ")));
        }
        i++;
      }
      for (PlayerScoreboard s : TotemsScoreboard.getScoreboards())
      {
        Object messages2 = new ArrayList();
        ((List)messages2).addAll((Collection)messages);
        s.setSide(title, (List)messages2);
      }
    }
    if (label.equalsIgnoreCase("saveloc"))
    {
      if (!(sender instanceof Player)) {
        return true;
      }
      Player p = (Player)sender;
      if (!p.isOp()) {
        return true;
      }
      if (args.length == 0)
      {
        p.sendMessage("/saveloc [locname] (-target/-standing)");
        return true;
      }
      if (args[0].equalsIgnoreCase("list"))
      {
        try
        {
          for (String s : LocationManager.getLocations(p.getWorld().getName())) {
            sender.sendMessage(s);
          }
        }
        catch (FileNotFoundException e)
        {
          sender.sendMessage("error");
        }
        return true;
      }
      Location loc = p.getLocation();
      if ((args.length > 1) && 
        (args[1].equalsIgnoreCase("-target"))) {
        loc = p.getTargetBlock(null, 100).getLocation();
      }
      String locName = args[0].toLowerCase();
      try
      {
        LocationManager.saveLocation(loc, locName);
      }
      catch (FileNotFoundException e)
      {
        sender.sendMessage("§cError > §7An error occured: " + e.getMessage());
        return true;
      }
      sender.sendMessage("§aSuccess > §7Saved successfully.");
      return true;
    }
    return false;
  }
}
