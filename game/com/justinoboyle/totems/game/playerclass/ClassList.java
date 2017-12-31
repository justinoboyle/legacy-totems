package com.justinoboyle.totems.game.playerclass;

import com.justinoboyle.totems.game.playerclass.archer.Archer;
import com.justinoboyle.totems.game.playerclass.demoman.ClassDemoman;
import com.justinoboyle.totems.game.playerclass.ghost.ClassGhost;
import com.justinoboyle.totems.game.playerclass.grunt.Grunt;
import com.justinoboyle.totems.game.playerclass.healer.Healer;
import com.justinoboyle.totems.game.playerclass.heavy.Heavy;
import com.justinoboyle.totems.game.playerclass.mage.Mage;
import com.justinoboyle.totems.game.playerclass.soldier.Soldier;

public class ClassList
{
  public static final PlayerClass[] CLASSES = { new Archer(), new ClassDemoman(), new ClassGhost(), new Grunt(), new Healer(), new Heavy(), new Mage(), new Soldier() };
}
