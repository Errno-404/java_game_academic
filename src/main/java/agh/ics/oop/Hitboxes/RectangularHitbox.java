package agh.ics.oop.Hitboxes;

import agh.ics.oop.Vector;

public  class RectangularHitbox implements Hitbox{
    public Vector upperLeft;
    public Vector lowerRight;
    public Vector centre;

    public RectangularHitbox(Vector ul, Vector lr){ //ul - upperleft, lr - lowerright
        this.upperLeft = ul;
        this.lowerRight = lr;
        this.centre = new Vector((ul.getX()+lr.getX())/2, (ul.getY()+lr.getY())/2);
    }

    public RectangularHitbox(Vector centre, double size){
        this.centre = centre;

        this.upperLeft = new Vector(centre.getX() - size/2,centre.getY() + size/2);
        this.lowerRight = new Vector(centre.getX() + size/2, centre.getY() - size/2);
    }

    /*public boolean collidesWith(RectangularHitbox hb) {
        return (this.upperLeft.getX() >= hb.upperLeft.getX() && this.upperLeft.getX() <= hb.lowerRight.getX() &&
                this.upperLeft.getY() >= hb.upperLeft.getY() && this.upperLeft.getY() <= hb.lowerRight.getY());
    }*/

    @Override
    public boolean collidesWith(Hitbox hb) {
        //System.out.println("checking collision");
        if(hb instanceof RectangularHitbox hb1){
            return (this.upperLeft.getX() >= hb1.upperLeft.getX() && this.lowerRight.getX() <= hb1.lowerRight.getX() &&
                    this.upperLeft.getY() >= hb1.upperLeft.getY() && this.lowerRight.getY() <= hb1.lowerRight.getY());
        }
        else if(hb instanceof CircularHitbox){
            CircularHitbox hb1 = (CircularHitbox) hb;

            return (this.centre.distance(hb1.centre) <= hb1.radius);
        }

        return false;
    }

    @Override
    public void moveAlongVector(Vector direction) {
        this.centre.addVector(direction);
        this.upperLeft.addVector(direction);
        this.lowerRight.addVector(direction);
    }

    @Override
    public boolean isIn(Vector position) {
        return false;
    }
}
