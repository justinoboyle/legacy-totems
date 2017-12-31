package com.justinoboyle.totems.lobby.loc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationManager
{
  public static void saveLocation(Location loc, String name)
    throws FileNotFoundException
  {
    File[] arrayOfFile;
    int j = (arrayOfFile = Bukkit.getWorldContainer().listFiles()).length;
    for (int i = 0; i < j; i++)
    {
      File f = arrayOfFile[i];
      if ((f.isDirectory()) && (f.getName().equals(loc.getWorld().getName())))
      {
        String newPath = f.getPath();
        newPath = newPath.replace("\\", "/");
        if (!newPath.endsWith("/")) {
          newPath = newPath + "/";
        }
        newPath = newPath + "/totems/saved_locations/" + name.toLowerCase() + ".loc";
        File f2 = new File(newPath);
        f2.getParentFile().mkdirs();
        if (f2.exists()) {
          f2.delete();
        }
        PrintWriter pw = new PrintWriter(f2);
        pw.write(toString(loc));
        pw.close();
        return;
      }
    }
    throw new FileNotFoundException("Could not find world container");
  }
  
  public static String toString(Location loc)
  {
    return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
  }
  
  public static Location toLocation(World world, String location)
  {
    String[] spl = location.split(",");
    return new Location(world, Double.parseDouble(spl[0]), Double.parseDouble(spl[1]), Double.parseDouble(spl[2]), Float.parseFloat(spl[3]), Float.parseFloat(spl[4]));
  }
  
  public static Location getLocation(String worldName, String name)
    throws FileNotFoundException
  {
    File[] arrayOfFile;
    int j = (arrayOfFile = Bukkit.getWorldContainer().listFiles()).length;
    for (int i = 0; i < j; i++)
    {
      File f = arrayOfFile[i];
      if ((f.isDirectory()) && (f.getName().equals(worldName)))
      {
        String newPath = f.getPath();
        newPath = newPath.replace("\\", "/");
        if (!newPath.endsWith("/")) {
          newPath = newPath + "/";
        }
        newPath = newPath + "/totems/saved_locations/" + name.toLowerCase() + ".loc";
        File f2 = new File(newPath);
        f2.getParentFile().mkdirs();
        if (!f2.exists()) {
          throw new FileNotFoundException("Could not find location file.");
        }
        String in = "";
        Scanner sc = new Scanner(new FileInputStream(f2));
        while (sc.hasNext()) {
          in = in + sc.nextLine();
        }
        sc.close();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
          throw new NullPointerException("Could not find world!");
        }
        return toLocation(world, in);
      }
    }
    throw new FileNotFoundException("Could not find world container");
  }
  
  public static List<String> getLocations(String worldName)
    throws FileNotFoundException
  {
    List<String> locs = new ArrayList();
    File[] arrayOfFile1;
    int j = (arrayOfFile1 = Bukkit.getWorldContainer().listFiles()).length;
    for (int i = 0; i < j; i++)
    {
      File f = arrayOfFile1[i];
      if ((f.isDirectory()) && (f.getName().equals(worldName)))
      {
        String newPath = f.getPath();
        newPath = newPath.replace("\\", "/");
        if (!newPath.endsWith("/")) {
          newPath = newPath + "/";
        }
        newPath = newPath + "/totems/saved_locations/";
        File f2 = new File(newPath);
        f2.getParentFile().mkdirs();
        File[] arrayOfFile2;
        int m = (arrayOfFile2 = f2.listFiles()).length;
        for (int k = 0; k < m; k++)
        {
          File f3 = arrayOfFile2[k];
          locs.add(f3.getName() + " | " + toString(getLocation(worldName, f3.getName().replace(".loc", ""))));
        }
      }
    }
    return locs;
  }
  
  public static String getString(String worldName, String name)
  {
    File[] arrayOfFile;
    int j = (arrayOfFile = Bukkit.getWorldContainer().listFiles()).length;
    for (int i = 0; i < j; i++)
    {
      File f = arrayOfFile[i];
      if ((f.isDirectory()) && (f.getName().equals(worldName)))
      {
        String newPath = f.getPath();
        newPath = newPath.replace("\\", "/");
        if (!newPath.endsWith("/")) {
          newPath = newPath + "/";
        }
        newPath = newPath + "/totems/" + name.toLowerCase();
        File f2 = new File(newPath);
        f2.getParentFile().mkdirs();
        if (!f2.exists()) {
          return "null";
        }
        String in = "";
        try
        {
          Scanner sc = new Scanner(new FileInputStream(f2));
          while (sc.hasNext()) {
            in = in + sc.nextLine();
          }
          sc.close();
        }
        catch (FileNotFoundException e)
        {
          return "null";
        }
        Scanner sc;
        return in;
      }
    }
    return "null";
  }
}
