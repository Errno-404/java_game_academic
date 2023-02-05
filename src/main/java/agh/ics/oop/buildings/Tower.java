package agh.ics.oop.buildings;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Vector;

public class Tower extends AttackingBuilding {

    public Tower(int width, int height, Vector position, int health, Attack attack) {
        super(width, height, position, health, attack);
    }

    @Override
    public void getHit(Attack attack) {
        super.getHit(attack);
        if(this.currentHealth == 0){
            System.out.println("Tower destroyed!");
        }
    }
}
