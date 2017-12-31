package com.justinoboyle.totems.core.errors;

import java.io.PrintStream;

public class ErrorReporting
{
  public static void sendStackTrace(Exception ex)
  {
    System.out.println("Error: " + ex.getMessage());
  }
}
