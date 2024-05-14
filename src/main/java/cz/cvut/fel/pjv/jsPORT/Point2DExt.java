package cz.cvut.fel.pjv.jsPORT;

import javafx.geometry.Point2D;

public class Point2DExt extends Point2D{
    public Point2DExt(double x, double y) {
        super(x, y);
    }
    public Point2DExt (Point2D point){
        super(point.getX(), point.getY());
    }

    public static double constrain(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static Point2D random2D() {
        double angle = Math.random() * Math.PI * 2;
        return new Point2D(Math.cos(angle), Math.sin(angle));
    }
    public Point2DExt rotate(double angle){
        double x = getX() * Math.cos(angle) - getY() * Math.sin(angle);
        double y = getX() * Math.sin(angle) + getY() * Math.cos(angle);

        return new Point2DExt(x, y);
    }
}
