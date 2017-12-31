package com.justinoboyle.totems.game.core;

import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.branding.Branding;
import com.justinoboyle.totems.game.command.CommandManager;
import com.justinoboyle.totems.game.core.event.GlobalEventListener;
import com.justinoboyle.totems.game.rotation.RotationManager;
import com.justinoboyle.totems.game.scoreboard.TotemsScoreboard;
import com.justinoboyle.totems.game.team.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Totems
  extends JavaPlugin
  implements Listener
{
  private static Totems instance;
  private Arena arena;
  private RotationManager rotationManager;
  private TotemsScoreboard scoreboard;
  
  public void onEnable()
  {
    setup();
    this.rotationManager.startArena();
    this.scoreboard = new TotemsScoreboard();
  }
  
  public void onDisable()
  {
    if (this.arena != null) {
      this.arena.resetArena();
    }
    Team[] arrayOfTeam;
    int j = (arrayOfTeam = Team.values()).length;
    for (int i = 0; i < j; i++)
    {
      Team t = arrayOfTeam[i];
      t.killHolograms();
    }
    this.rotationManager = null;
    this.scoreboard = null;
    instance = null;
    this.arena = null;
  }
  
  private void setup()
  {
    instance = this;
    this.rotationManager = new RotationManager();
    GameSetup.setupPlugin();
    new GlobalEventListener();
    new Branding();
  }
  
  public static Totems getInstance()
  {
    return instance;
  }
  
  public Arena getArena()
  {
    return this.arena;
  }
  
  public void setArena(Arena arena)
  {
    this.arena = arena;
  }
  
  public RotationManager getRotationManager()
  {
    return this.rotationManager;
  }
  
  public TotemsScoreboard getScoreboard()
  {
    if (this.scoreboard == null) {
      this.scoreboard = new TotemsScoreboard();
    }
    return this.scoreboard;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    return CommandManager.onCommand(sender, command, label, args);
  }
}
