package com.justinoboyle.totems.game.rotation;

import com.justinoboyle.totems.core.errors.ErrorReporting;
import com.justinoboyle.totems.game.arena.Arena;
import com.justinoboyle.totems.game.core.Totems;
import com.justinoboyle.totems.game.loc.LocationManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class RotationManager
{
  private final ArrayList<String[]> rotation = new ArrayList();
  private int currentRotation = 0;
  
  public RotationManager()
  {
    init();
  }
  
  private void init()
  {
    try
    {
      File f = new File("./data/rotation.dat");
      f.getParentFile().mkdirs();
      Scanner sc = new Scanner(new FileInputStream(f));
      while (sc.hasNext())
      {
        String line = sc.nextLine();
        String[] ret = { line, LocationManager.getString(line, "author") };
        this.rotation.add(ret);
      }
      sc.close();
      Collections.shuffle(this.rotation);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      ErrorReporting.sendStackTrace(ex);
    }
  }
  
  public World getCurrentWorld()
  {
    return Bukkit.getWorld(((String[])this.rotation.get(this.currentRotation))[0]);
  }
  
  public World getNextWorld()
  {
    return Bukkit.getWorld(((String[])this.rotation.get(this.currentRotation + 1))[0]);
  }
  
  public String getCurrentWorldAuthor()
  {
    return ((String[])this.rotation.get(this.currentRotation))[1];
  }
  
  public String getNextWorldAuthor()
  {
    return ((String[])this.rotation.get(this.currentRotation + 1))[1];
  }
  
  public void rotateToNext(int waitTime)
  {
    new BukkitRunnable()
    {
      public void run()
      {
        RotationManager.this.advanceWorld();
        RotationManager.this.startArena();
      }
    }.runTaskLater(Totems.getInstance(), waitTime);
  }
  
  public World advanceWorld()
  {
    if (Totems.getInstance().getArena() != null) {
      Totems.getInstance().getArena().disable();
    }
    this.currentRotation += 1;
    if (this.currentRotation >= this.rotation.size()) {
      this.currentRotation = 0;
    }
    return getCurrentWorld();
  }
  
  public void startArena()
  {
    try
    {
      Totems.getInstance().setArena(new Arena(getCurrentWorld().getName(), getCurrentWorldAuthor()));
      Totems.getInstance().getArena().startCountdown();
    }
    catch (NullPointerException localNullPointerException) {}
  }
}
