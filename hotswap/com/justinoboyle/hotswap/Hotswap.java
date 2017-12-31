package com.justinoboyle.hotswap;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Hotswap
  extends JavaPlugin
{
  public static boolean hotswap()
  {
    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("Totems"));
    File currentPlugin = new File("./plugins/Totems.jar");
    File newFile = new File("./hotswap/plugins/Totems.jar");
    Path p = currentPlugin.toPath();
    newFile.getParentFile().mkdirs();
    currentPlugin.delete();
    currentPlugin.setWritable(true, false);
    try
    {
      currentPlugin.renameTo(new File("./plugins/Totems-old.jar"));
      Files.move(newFile.toPath(), p, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
    }
    catch (IOException e)
    {
      System.out.println("[WRAPPER] HOTSWAP ERROR: Could not move file!");
      e.printStackTrace();
      return false;
    }
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rl");
    System.out.println("[WRAPPER] HOTSWAP SUCCESS");
    return true;
  }
}
