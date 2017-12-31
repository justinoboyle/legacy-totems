package com.justinoboyle.totems.game.coin;

public enum CoinGainReason
{
  KILL(10),  KILLSTREAK_THREE(30),  KILLSTREAK_FIVE(50),  KILLSTREAK_NINE(90),  TOTEM_HIT(25),  TOTEM_KILL(100),  TEAM_WIN(50),  TRAP_KILL(15),  TRAP_KILL_DOUBLE(45),  NO_DEATH(30);
  
  public int amount;
  
  private CoinGainReason(int amount)
  {
    this.amount = amount;
  }
}
