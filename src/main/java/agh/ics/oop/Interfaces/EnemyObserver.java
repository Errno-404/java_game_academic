package agh.ics.oop.Interfaces;

import agh.ics.oop.Enemies.Enemy;
import agh.ics.oop.Vector;

public interface EnemyObserver {
    public void reportNewIndexEnemy(Vector old, Vector newpos, Enemy a);
}
