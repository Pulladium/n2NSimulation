package cz.cvut.fel.pjv.controllers.ecsHandlerComp;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import static cz.cvut.fel.pjv.model.GLOBALS.G;

/**
 * Handles the movement and attraction of entities in the ECS framework.
 * <p>
 * Extends {@link Component} and manages the components required for movement.
 */
public class MoveableHandl extends Component {
    public boolean moveable;
    Entity currEntity;
/**
 * Constructs a {@link MoveableHandl} for the given entity.
 * <p>
 * Checks if the entity has all required components and sets up the components.
 *
 * @param entity the {@link Entity} to be handled.
 */
    public MoveableHandl(Entity entity){
        if (entity.hasComponent(MassComponent.class) &&
                entity.hasComponent(CompColor.class) &&
                entity.hasComponent(CompSize.class)
                && entity.hasComponent(CompPosition.class)
                && entity.hasComponent(CompVelocity.class)
                && entity.hasComponent(CompAcceleration.class))
        {
            moveable = true;
            currEntity = entity;
            setupComponents();
        } else {
            moveable = false;
        }
    }


    MassComponent massComp ;
    CompColor color;
    CompSize size;
    CompPosition posComp;
    CompVelocity velComp;
    CompAcceleration accComp;

    /**
     * Sets up the components for the entity.
     * <p>
     * Retrieves and stores references to the required components.
     */
    private void setupComponents(){
        massComp = currEntity.getComponent(MassComponent.class);
        color = currEntity.getComponent(CompColor.class);
        size = currEntity.getComponent(CompSize.class);
        posComp = currEntity.getComponent(CompPosition.class);
        velComp = currEntity.getComponent(CompVelocity.class);
        accComp = currEntity.getComponent(CompAcceleration.class);
    }




    /**
     * Applies a force to the entity.
     * <p>
     * Calculates the acceleration based on the force and mass, and updates the acceleration component.
     *
     * @param force the {@link Point2D} force to be applied.
     */
    void applyForce(Point2D force) {
        Point2D f = force.multiply(1 / this.massComp.mass.doubleValue());
        this.accComp = new CompAcceleration(this.accComp.acceleration.add(f));
    }


    /**
     * Attracts another entity towards this entity.
     * <p>
     * Calculates the gravitational force between the entities and applies it to the other entity.
     *
     * @param otherMover the other {@link Entity} to be attracted.
     */
    public void attract(Entity otherMover) {


        CompPosition otherPos = otherMover.getComponent(CompPosition.class);
        CompVelocity otherVel = otherMover.getComponent(CompVelocity.class);
        MassComponent otherMass = otherMover.getComponent(MassComponent.class);

        Point2D force = this.posComp.position.subtract(otherPos.position);
        double distanceSq = Math.max(100, Math.min(force.magnitude() * force.magnitude(), 1000));
        double strength = (G * (this.massComp.mass.doubleValue() * otherMass.mass.doubleValue())) / distanceSq;
        force = force.normalize().multiply(strength);
        otherMover.getComponent(MoveableHandl.class).applyForce(force);
    }


    /**
     * Updates the entity's position and velocity.
     * <p>
     * Applies the acceleration to the velocity, updates the position, limits the velocity, and resets the acceleration.
     */
    public void update() {

        this.velComp.setVelocity( this.velComp.getVelocity().add(this.accComp.acceleration));
        this.posComp.position =new Point2DExt( this.posComp.position.add(this.velComp.getVelocity()));
        this.velComp.setVelocity(limit(this.velComp.getVelocity(), 15));
        this.accComp = new CompAcceleration(0, 0);
    }
    public void update(double dt) {
        this.velComp.setVelocity(this.velComp.getVelocity().add(this.accComp.acceleration.multiply(dt)));
        this.posComp.position = new Point2DExt(this.posComp.position.add(this.velComp.getVelocity().multiply(dt)));
        this.velComp.setVelocity(limit(this.velComp.getVelocity(), 15));
        this.accComp = new CompAcceleration(0, 0);
    }


    /**
     * Draws the entity on the given {@link GraphicsContext}.
     * <p>
     * Draws the entity as a filled oval based on its position and size.
     *
     * @param gc the {@link GraphicsContext} to draw on.
     */
    public void show(GraphicsContext gc) {
        gc.setFill(this.color.color);
        gc.fillOval(this.posComp.position.getX() - this.size.size / 2, this.posComp.position.getY() - this.size.size / 2, this.size.size, this.size.size);
    }


    /**
     * Limits the magnitude of a vector to a maximum value.
     *
     * @param vector the {@link Point2D} vector to be limited.
     * @param max the maximum magnitude.
     * @return the limited {@link Point2D} vector.
     */
    Point2D limit(Point2D vector, double max) {
        if (vector.magnitude() > max) {
            return vector.normalize().multiply(max);
        }
        return vector;
    }

}
