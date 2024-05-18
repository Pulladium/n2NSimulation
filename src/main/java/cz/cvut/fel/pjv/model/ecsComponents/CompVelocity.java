package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;

public class CompVelocity  extends Component {
    @Expose
    Point2DExt velocity;

    public CompVelocity(double vx, double vy) {
        this.velocity = new Point2DExt(vx, vy);
    }
    public CompVelocity(Point2DExt velocity) {
        this.velocity = velocity;
    }






    public Point2DExt getVelocity() {
        return velocity;
    }



    public void setVelocity(Point2DExt velocity) {
        this.velocity = new Point2DExt(velocity);
    }
    public void setVelocity(Point2D velocity) {
        this.velocity = new Point2DExt(velocity);
    }
    public void setVelocity(double vx, double vy) {
        this.velocity = new Point2DExt(vx, vy);
    }

}
