package agh.ics.oop.Enemies;

import agh.ics.oop.Attacks.Attack;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.HealthChangeObserver;
import agh.ics.oop.Interfaces.Hittable;
import agh.ics.oop.maps.GameMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Enemy implements Hittable {


    double maxHealth;
    public double currentHealth;
    Image sprite;

    HealthChangeObserver hpObs;

    RectangularHitbox hitbox;

    GameMap map;


    @Override
    public void getHit(Attack a) {
        a.hit(this);
    }

    @Override
    public void reduceHealth(double h){
        this.currentHealth-=h;
        this.hpObs.reportHealthChange(currentHealth/maxHealth);
    }

    public void move(){

        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<Pair<Integer, Integer>>() {
            {
                add(new Pair<>(1,0));
                add(new Pair<>(0,1));
                add(new Pair<>(-1,0));
                add(new Pair<>(0,-1));

                add(new Pair<>(1,1));
                add(new Pair<>(1,-1));
                add(new Pair<>(-1,1));
                add(new Pair<>(-1,-1));


            }
        };

        int currX = this.hitbox.centre.getXindex();
        int currY = this.hitbox.centre.getYindex();

        int minIndex = 0;
        double minVal = Integer.MAX_VALUE;
        for(Pair<Integer, Integer> move: moves){
            int nextX = currX + move.getKey();
            int nextY = currY + move.getValue();

            if(this.map.isOnMap(nextX, nextY)){
                if(this.map.map[nextX][nextY].mapWeightValue < minVal){
                    minVal = this.map.map[nextX][nextY].mapWeightValue;
                    minIndex = moves.indexOf(move);
                }
            }
        }

        int nextXsquare = currX + moves.get(minIndex).getKey();
        int nextYsquare = currY + moves.get(minIndex).getValue();


        this.hitbox.moveAlongVector(this.hitbox.centre.getDirectionVector(this.map.map[nextXsquare][nextYsquare].squareCentre));
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(sprite,hitbox.upperLeft.getX(), hitbox.upperLeft.getY());
    }

    public RectangularHitbox getHitbox(){
        return  this.hitbox;
    }
}
