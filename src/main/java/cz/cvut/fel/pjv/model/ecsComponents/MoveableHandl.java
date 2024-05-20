package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import static cz.cvut.fel.pjv.model.GLOBALS.G;

public class MoveableHandl extends Component {
    public boolean moveable;
    Entity currEntity;
//TODO
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

    private void setupComponents(){
        massComp = currEntity.getComponent(MassComponent.class);
        color = currEntity.getComponent(CompColor.class);
        size = currEntity.getComponent(CompSize.class);
        posComp = currEntity.getComponent(CompPosition.class);
        velComp = currEntity.getComponent(CompVelocity.class);
        accComp = currEntity.getComponent(CompAcceleration.class);
    }




    void applyForce(Point2D force) {
        Point2D f = force.multiply(1 / this.massComp.mass.doubleValue());
//        this.accComp = this.accComp.acceleration.add(f);
        this.accComp = new CompAcceleration(this.accComp.acceleration.add(f));
    }



    //applys acceleration to the mover
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

    //Changes the velocity and 0s out the acceleration
    public void update() {
//        setupComponents();
        this.velComp.setVelocity( this.velComp.getVelocity().add(this.accComp.acceleration));
        this.posComp.position =new Point2DExt( this.posComp.position.add(this.velComp.getVelocity()));
        this.velComp.setVelocity(limit(this.velComp.getVelocity(), 15));
        this.accComp = new CompAcceleration(0, 0);
    }

    public void show(GraphicsContext gc) {
//        setupComponents();
        gc.setFill(this.color.color);
        gc.fillOval(this.posComp.position.getX() - this.size.size / 2, this.posComp.position.getY() - this.size.size / 2, this.size.size, this.size.size);
    }

//    public void show(GraphicsContext gc, double offsetX, double offsetY) {
//        gc.setFill(this.color.color);
//        gc.fillOval(offsetX - this.size.size / 2, offsetY - this.size.size / 2, this.size.size, this.size.size);
//    }

    Point2D limit(Point2D vector, double max) {
        if (vector.magnitude() > max) {
            return vector.normalize().multiply(max);
        }
        return vector;
    }

}
