package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;

public class CompPosition extends Component {
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


}