package agh.ics.oop.maps;

import agh.ics.oop.Enemy;
import agh.ics.oop.Constants;
import agh.ics.oop.Hitboxes.RectangularHitbox;
import agh.ics.oop.Interfaces.BuildingDestroyedObserver;
import agh.ics.oop.Interfaces.EnemyObserver;
import agh.ics.oop.Interfaces.ProjectileObserver;
import agh.ics.oop.Proejctiles.Projectile;
import agh.ics.oop.Vector;
import agh.ics.oop.buildings.AttackingBuilding;
import agh.ics.oop.buildings.Building;
import agh.ics.oop.buildings.DefensiveBuilding;
import agh.ics.oop.gui.GameScreen;


public class GameMap implements ProjectileObserver, EnemyObserver, BuildingDestroyedObserver {
    protected int width;
    protected int height;
    GameScreen gameScreen;

    public mapElement[][] map;



    public GameMap(int width, int height, GameScreen gs){
        this.height = height;
        this.width = width;
        this.gameScreen = gs;

        this.map = new mapElement[Constants.boxNoWidth+1][Constants.boxNoHeight+1];

        for(int i = 0;i<Constants.boxNoWidth+1;i++){
            for(int j = 0;j<Constants.boxNoHeight+1;j++){
                map[i][j] = new mapElement(i,j, gs.elements[i][j]);
            }
        }
    }

    public boolean canPlace(RectangularHitbox hb){
        for(int i = (int) hb.upperLeft.getXindex(); i<hb.lowerRight.getXindex(); i++){
            for(int j = (int) hb.upperLeft.getYindex(); j<hb.lowerRight.getYindex(); j++){
                if(i < 0 || i >= Constants.boxNoWidth || j < 0 || j >= Constants.boxNoHeight){
                    System.out.println("cant place for " + i + "   " + j);
                    return false;
                }
                if (!map[i][j].placeable){
                    System.out.println("is occupied:   " + i + " " + j);
                    return false;
                }
            }
        }
        return true;
    }

    public void placeMap(Building building){
        Vector anchorPoint = building.getAnchorPosition();

        int xIndex = anchorPoint.getXindex();
        int yIndex = anchorPoint.getYindex();

        if(!canPlace(building.hitbox)){
            System.out.println("cant place!!!");
            return;
        }

        for(int i = xIndex;i< xIndex + building.getWidth();i++){
            for(int j = yIndex;j< yIndex + building.getHeight();j++){
                System.out.println("updating element: " + i + " " + j);
                this.map[i][j].updateCanvas(building.getView(i- xIndex,j - yIndex));
                this.map[i][j].placeable = false;
                this.map[i][j].reachable = false;
            }
        }

        if(building instanceof AttackingBuilding){
            //Add building to inRangeOf lists
        }
    }

    public void addProjectile(Projectile p){
        int xIndex = p.position.getXindex();
        int yIndex = p.position.getYindex();

        this.map[xIndex][yIndex].projectileList.add(p);
    }

    @Override
    public void reportNewIndexProjectile(Vector old, Vector newpos, Projectile p) {
        try {

            if(!this.map[(int) old.getX()][(int) old.getY()].projectileList.remove(p)){
                System.out.println("removed from  " + old.getXindex() + " " + old.getYindex() + p);
                System.out.println(this.map[old.getXindex()][old.getYindex()].projectileList);
                System.out.println(this.map[old.getXindex()-1][old.getYindex()-1].projectileList);
                System.out.println(this.map[old.getXindex()-1][old.getYindex()].projectileList);
                System.out.println(this.map[old.getXindex()][old.getYindex()-1].projectileList);
                System.out.println("\n");
            }
            this.map[(int) newpos.getX()][(int) newpos.getY()].projectileList.add(p);

        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(old.getX() +" " + old.getXindex() + " " + old.getY() + " " + old.getYindex());
            System.out.println(newpos.getX() +" " + newpos.getXindex() + " " + newpos.getY() + " " + newpos.getYindex());
        }
    }

    @Override
    public void reportNewIndexEnemy(Vector old, Vector newpos, Enemy a) {
        this.map[old.getXindex()][old.getYindex()].enemyList.remove(a);
        this.map[newpos.getXindex()][newpos.getYindex()].enemyList.add(a);
    }

    @Override
    public void reportBuildingDestroyed(Building b) {
        int upx = b.hitbox.upperLeft.getXindex();
        int upy = b.hitbox.upperLeft.getYindex();

        System.out.println(upx + " " + upy);

        for(int i = upx; i<upx+b.getWidth();i++){
            for(int j = upy;j<upy + b.getHeight();j++){
                this.gameScreen.elements[i][j].setOriginalView();
                this.map[i][j].reachable = true;
                this.map[i][j].placeable = true;
            }
        }

        if(b instanceof AttackingBuilding){
            //remove building from all inRangeOf lists
        }
    }

    public int sumProj(){
        int acc = 0;
        for(int i = 0;i<Constants.boxNoWidth+1;i++){
            for(int j = 0;j<Constants.boxNoHeight+1;j++){
                acc+=map[i][j].projectileList.size();
            }
        }

        return acc;
    }
}
