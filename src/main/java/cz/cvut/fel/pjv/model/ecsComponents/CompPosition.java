package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;

public class CompPosition extends Component {
    @Expose
    public Point2DExt position;

    public CompPosition(double x, double y) {
        this.position = new Point2DExt(x, y);
    }
    public CompPosition(Point2DExt position) {
        this.position = position;
    }
    public CompPosition(Point2D position) {
        this.position = new Point2DExt(position);
    }
    public void setPosition(Point2DExt position) {
        this.position = position;
    }

    public Point2DExt getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "CompPosition{" +
                "position=" + position.toString() +
                '}';
    }
}
