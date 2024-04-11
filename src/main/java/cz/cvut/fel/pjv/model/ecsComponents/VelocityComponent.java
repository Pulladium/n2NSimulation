package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Vector2D;

public class VelocityComponent extends Component {
//
//    private Point2D.Double velocity;

    private Vector2D velocity;

    public VelocityComponent(double vx, double vy) {
        this.velocity = new Vector2D(vx, vy);
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double vx, double vy) {
        this.velocity = new Vector2D(vx, vy);
    }


    @Override
    public String toString() {
        return "VelocityComponent{" +
                "velocity=" + velocity.toString() +
                '}';
    }
}
