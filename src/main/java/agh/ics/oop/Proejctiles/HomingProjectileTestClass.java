package agh.ics.oop.Proejctiles;

import agh.ics.oop.Enemy;
import agh.ics.oop.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HomingProjectileTestClass extends Projectile{
    Vector target;


    public HomingProjectileTestClass(Vector position, double velocity) {
        super(position, velocity);
        this.target = new Vector(500,500);

        try {
            this.sprite = new ImageView(new Image(new FileInputStream("src/main/resources/yellowRect.png")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void move() {
        Vector oldPos = this.getCentre();
        int oldposX = hitbox.centre.getXindex();
        int oldposY = hitbox.centre.getYindex();

        Vector direction = this.hitbox.centre.getDirectionVector(this.target);
        direction.multiplyScalar(this.velocity);
        this.hitbox.moveAlongVector(direction);

        int newposX = this.hitbox.centre.getXindex();
        int newposY = this.hitbox.centre.getYindex();


        if(!(oldposX == newposX && oldposY == newposY)){
            this.pobs.reportNewIndexProjectile(new Vector(oldposX, oldposY),new Vector(newposX, newposY), this);
        }
    }

    public Vector getCentre(){
        return this.hitbox.centre;
    }

    public void updateTarget(Vector newt){
        this.target = newt;
    }

    @Override
    public void hit(Enemy collided) {

    }
}
