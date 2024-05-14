package cz.cvut.fel.pjv.model.ecsComponents;

import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;

public class PositionComp {
    public Point2DExt position;

    public PositionComp(double x, double y) {
        this.position = new Point2DExt(x, y);
    }
    public PositionComp(Point2DExt position) {
        this.position = position;
    }
    public PositionComp(Point2D position) {
        this.position = new Point2DExt(position);
    }


}
