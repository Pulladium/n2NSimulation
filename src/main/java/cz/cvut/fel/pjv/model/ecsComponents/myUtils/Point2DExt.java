package cz.cvut.fel.pjv.model.ecsComponents.myUtils;

import javafx.geometry.Point2D;

import java.util.Random;

public class Point2DExt extends Point2D{
    public Point2DExt(double x, double y) {
        super(x, y);
    }
    public Point2DExt(Point2D point){
        super(point.getX(), point.getY());
    }

    public static double constrain(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static Point2D random2D() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        return new Point2D(Math.cos(angle), Math.sin(angle));
    }

    public static Point2D rotate(Point2D vector, double angle) {
        double x = vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle);
        double y = vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle);
        return new Point2D(x, y);
    }
}
