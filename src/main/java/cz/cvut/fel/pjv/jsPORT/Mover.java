package cz.cvut.fel.pjv.jsPORT;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.model.GLOBALS;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static cz.cvut.fel.pjv.model.GLOBALS.G;

public class Mover {

//    @Transient для исключения полей из сериализации.

    public transient Entity currentEntity;


//    Point2D pos;
    @Expose
    CompPosition posComp;
    @Expose
    CompVelocity velComp;
    @Expose
    CompAcceleration accComp;
//    double mass;
    @Expose
    MassComponent massComp;

    @Expose
    CompSize size;
    @Expose
    CompColor color;

    public Mover(double x, double y, double vx, double vy, double m, double size, Color color) {

        this.posComp = new CompPosition(x, y);
        this.velComp = new CompVelocity(vx, vy);
        this.accComp = new CompAcceleration(0, 0);



        this.massComp = new MassComponent(m);
//        this.size = size;
        this.size = new CompSize(size);
        this.color = new CompColor(color);


        currentEntity = newMover();
    }



    public Entity newMover(){
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

    public CompPosition getPosComp() {
        return posComp;
    }
}