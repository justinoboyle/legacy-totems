package com.justinoboyle.servermanager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReadUtil
{
  public static String readFile(File f)
    throws FileNotFoundException
  {
    Scanner sc = new Scanner(new FileInputStream(f));
    StringBuilder b = new StringBuilder();
    while (sc.hasNext()) {
      b.append(sc.nextLine());
    }
    sc.close();
    return b.toString();
  }
}
