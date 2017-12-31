package com.justinoboyle.totems.lobby.scoreboard;

import com.justinoboyle.totems.util.ServerListener;
import org.bukkit.entity.Player;

public class LobbyStatListener
  extends ServerListener
{
  public LobbyStatListener()
  {
    ServerListener.registerListener(this);
  }
  
  public void statUpdate(Player p)
  {
    LobbyScoreboard.updateScoreboard(p);
  }
}
