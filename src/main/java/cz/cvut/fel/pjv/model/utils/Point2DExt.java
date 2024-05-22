package cz.cvut.fel.pjv.model.utils;

import javafx.geometry.Point2D;

import java.util.Random;

/**
 * Extenteds Point2d to use {@link #random2D()} {@link #rotate(Point2D, double)}
 */
public class Point2DExt extends Point2D{
    public Point2DExt(double x, double y) {
        super(x, y);
    }
    public Point2DExt(Point2D point){
        super(point.getX(), point.getY());
    }

    /**
     * @deprecated
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static double constrain(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Generates a random 2D point on the unit circle.
     *
     * This method creates a random angle and returns a {@link Point2D} object
     * with coordinates corresponding to that angle on the unit circle.
     *
     * @return a {@link Point2D} object representing a random point on the unit circle.
     */
    public static Point2D random2D() {
        Random random = new Random();
        double angle = random.nextDouble() * 2 * Math.PI;
        return new Point2D(Math.cos(angle), Math.sin(angle));
    }
    /**
     * Rotates a given 2D vector by a specified angle.
     *
     * This method takes a {@link Point2D} vector and an angle in radians,
     * and returns a new {@link Point2D} object representing the rotated vector.
     *
     * @param vector the {@link Point2D} vector to be rotated.
     * @param angle the angle in radians by which to rotate the vector.
     * @return a new {@link Point2D} object representing the rotated vector.
     */
    public static Point2D rotate(Point2D vector, double angle) {
        double x = vector.getX() * Math.cos(angle) - vector.getY() * Math.sin(angle);
        double y = vector.getX() * Math.sin(angle) + vector.getY() * Math.cos(angle);
        return new Point2D(x, y);
    }
}
