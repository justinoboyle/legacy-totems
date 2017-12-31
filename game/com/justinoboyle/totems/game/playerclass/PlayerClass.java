package com.justinoboyle.totems.game.playerclass;

import com.justinoboyle.totems.game.item.WarzoneItem;
import com.justinoboyle.totems.game.playerclass.soldier.Soldier;

public abstract class PlayerClass
{
  public static final PlayerClass DEFAULT = new Soldier();
  
  public abstract WarzoneItem[] getArmorContents();
  
  public abstract SubClass[] getSubClasses();
  
  public SubClass getDefaultSubClass()
  {
    return getSubClasses()[0];
  }
  
  public abstract float getHealthScale();
  
  public abstract float getAttackScale();
  
  public abstract float getSpeedScale();
}
