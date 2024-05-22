package cz.cvut.fel.pjv.model.ecsPrepearedObjects;

import at.fhooe.mtd.ecs.Entity;
import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.controllers.ecsHandlerComp.MoveableHandl;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import javafx.scene.paint.Color;

/**
 * Represents a mover entity in the ECS (Entity Component System) framework.
 * <p>
 * Contains components for position, velocity, acceleration, mass, size, and color.
 * Uses {@link Entity} to manage these components and provides methods to access them.
 */
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

    /**
     * Constructs a new {@link Mover} with the specified parameters.
     *
     * @param x the x-coordinate of the position.
     * @param y the y-coordinate of the position.
     * @param vx the velocity in the x direction.
     * @param vy the velocity in the y direction.
     * @param m the mass of the mover.
     * @param size the size of the mover.
     * @param color the color of the mover.
     */
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


    /**
     * Creates a new {@link Entity} for the mover and adds all components created in constructor to it.
     *{@link CompPosition}
     * {@link CompVelocity}
     * {@link CompAcceleration}
     * {@link MassComponent}
     * {@link CompSize}
     * {@link CompColor}
     * <br> Adds Handler component{@link MoveableHandl}
     *
     * @return the newly created entity.
     */
    public Entity newMover(){
        Entity newEnt = new Entity();
        newEnt.addComponent(posComp);
        newEnt.addComponent(velComp);
        newEnt.addComponent(accComp);
        newEnt.addComponent(massComp);

        newEnt.addComponent(new CompSize(size.size));
        newEnt.addComponent(new CompColor(color.color));


        newEnt.addComponent(new MoveableHandl(newEnt));
        return newEnt;
    }

    public CompPosition getPosComp() {
        return posComp;
    }
    public CompSize getSize() {
        return currentEntity.getComponent(CompSize.class);
    }

    public Entity getCurrentEntity() {
        return currentEntity;
    }

    public CompVelocity getVelComp() {
        return currentEntity.getComponent(CompVelocity.class);
    }

    public CompAcceleration getAccComp() {
        return currentEntity.getComponent(CompAcceleration.class);
    }

    public MassComponent getMassComp() {
        return currentEntity.getComponent(MassComponent.class);
    }

    public CompColor getColor() {
        return currentEntity.getComponent(CompColor.class);
    }

}