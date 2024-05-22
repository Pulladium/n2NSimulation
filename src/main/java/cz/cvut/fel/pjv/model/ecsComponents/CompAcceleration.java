package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.geometry.Point2D;
/**
 * Represents an acceleration {@link Component} in the ECS (Entity Component System) framework.
 * <p>
 * Contains an acceleration property.
 */
public class CompAcceleration extends Component {
    @Expose
    public Point2DExt acceleration;

    public CompAcceleration(double ax, double ay) {
        this.acceleration = new Point2DExt(ax, ay);
    }
    public CompAcceleration(Point2DExt acceleration) {
        this.acceleration = acceleration;
    }
    public CompAcceleration(Point2D acceleration) {
        this.acceleration = new Point2DExt(acceleration);
    }

    public void setAcceleration(Point2DExt acceleration) {
        this.acceleration = acceleration;
    }
}
