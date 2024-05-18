package cz.cvut.fel.pjv.jsPORT;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import cz.cvut.fel.pjv.model.GLOBALS;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.cvut.fel.pjv.model.GLOBALS.G;

public class Mover {


    public Entity currentEntity;


//    Point2D pos;
    CompPosition posComp;
    CompVelocity velComp;
    CompAcceleration accComp;
//    double mass;
    MassComponent massComp;


    CompSize size;
    CompColor color;

    Mover(double x, double y, double vx, double vy, double m, double size, Color color) {

        this.posComp = new CompPosition(x, y);
        this.velComp = new CompVelocity(vx, vy);
        this.accComp = new CompAcceleration(0, 0);



        this.massComp = new MassComponent(m);
//        this.size = size;
        this.size = new CompSize(size);
        this.color = new CompColor(color);


        currentEntity = newMover();
    }



    private Entity newMover(){
        Entity newEnt = new Entity();
        newEnt.addComponent(posComp);
        newEnt.addComponent(velComp);
        newEnt.addComponent(accComp);
        newEnt.addComponent(massComp);

        newEnt.addComponent(new CompSize(size.size));
        newEnt.addComponent(new CompColor(color.color));
        newEnt.addComponent(new CompRadius(size.size/2));

        newEnt.addComponent(new MoveableHandl(newEnt));
        return newEnt;
    }

}