package us.totems.redirect;

import java.io.PrintStream;
import java.util.Scanner;
import us.totems.redirect.webserver.ServerHandler;

public class Main
{
  public static final String AUTHOR = "www.justinoboyle.com";
  public static int PORT;
  private static ServerHandler server;
  
  public static void main(String[] args)
  {
    if (args.length == 0) {
      PORT = 80;
    } else {
      PORT = Integer.parseInt(args[0]);
    }
    System.out.println("Using port " + PORT);
    server = new ServerHandler();
    server.enable();
    System.out.println("Done");
    Scanner sc = new Scanner(System.in);
    boolean stayAlive = true;
    while (stayAlive) {
      if (sc.nextLine().equalsIgnoreCase("stop")) {
        stayAlive = false;
      }
    }
    server.disable();
    server = null;
    System.out.println("Server shutting down");
  }
}
