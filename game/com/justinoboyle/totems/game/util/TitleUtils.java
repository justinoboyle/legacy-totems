package com.justinoboyle.totems.game.util;

import com.justinoboyle.totems.core.utils.BUtils;
import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.arena.PlayerData;
import com.justinoboyle.totems.game.team.Team;
import org.bukkit.entity.Player;

public class TitleUtils
{
  public static void showWinLossScreen(Arena a, Player finalBlow, Team winner)
  {
    for (PlayerData da : a.data)
    {
      Player p = da.getPlayer();
      if (da.getTeam() == winner) {
        BUtils.sendTitle(da.getPlayer(), "§aVICTORY!", "§8- §7Finishing Blow: §b" + finalBlow.getName() + " §8-", 5, 5, 5);
      } else {
        BUtils.sendTitle(da.getPlayer(), "§cLOSS!", "§8- §7Finishing Blow: §b" + finalBlow.getName() + " §8-", 5, 5, 5);
      }
    }
  }
}
