package com.justinoboyle.totems.game.command;

import com.justinoboyle.totems.core.errors.ErrorReporting;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.gui.selectclass.ClassSelectionGUI;
import com.justinoboyle.totems.game.gui.selectclass.SubClassSelectionGUI;
import com.justinoboyle.totems.game.loc.LocationManager;
import com.justinoboyle.totems.game.playerclass.ClassList;
import com.justinoboyle.totems.game.playerclass.PlayerClass;
import com.justinoboyle.totems.game.playerclass.SubClass;
import java.io.FileNotFoundException;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager
{
  public static boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (label.equalsIgnoreCase("kit"))
    {
      if (Totems.getInstance().getArena() == null)
      {
        ErrorReporting.sendStackTrace(new NullPointerException("arena is null."));
        return true;
      }
      if (!(sender instanceof Player)) {
        return true;
      }
      Player p = (Player)sender;
      if (args.length != 0)
      {
        PlayerClass[] arrayOfPlayerClass;
        int j = (arrayOfPlayerClass = ClassList.CLASSES).length;
        for (int i = 0; i < j; i++)
        {
          PlayerClass c = arrayOfPlayerClass[i];
          if (c.getDefaultSubClass().getName().equalsIgnoreCase(args[0]))
          {
            p.openInventory(new SubClassSelectionGUI(c).getInventory());
            return true;
          }
        }
      }
      p.openInventory(new ClassSelectionGUI().getInventory());
      return true;
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
      if (args.length == 0) {
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
    if (label.equalsIgnoreCase("tpto"))
    {
      if (!(sender instanceof Player)) {
        return true;
      }
      Player p = (Player)sender;
      if (!p.isOp()) {
        return true;
      }
      if (args.length == 0) {
        return true;
      }
      String locName = args[0].toLowerCase();
      String worldName = args.length > 1 ? args[1] : p.getWorld().getName();
      try
      {
        p.teleport(LocationManager.getLocation(worldName, locName));
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
      }
      catch (Exception e)
      {
        sender.sendMessage("§cError > §7An error occured: " + e.getMessage());
        return true;
      }
      return true;
    }
    return false;
  }
}
