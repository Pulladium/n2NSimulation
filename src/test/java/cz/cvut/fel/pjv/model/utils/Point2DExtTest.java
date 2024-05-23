package cz.cvut.fel.pjv.model.utils;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point2DExtTest {

    @Test
    void constrain() {
        assertEquals(1, Point2DExt.constrain(1, 0, 2));
        assertEquals(0, Point2DExt.constrain(-1, 0, 2));
        assertEquals(2, Point2DExt.constrain(3, 0, 2));
    }

    @Test
    void random2D() {
        for (int i = 0; i < 100; i++) {
           Point2D point = Point2DExt.random2D();

           Point2D point2 = Point2DExt.random2D();
              assertNotEquals(point, point2);
              assertNotEquals(point.getX(), point2.getX());
                assertNotEquals(point.getY(), point2.getY());
        }
    }

    @Test
    void rotate() {
        Point2D point = new Point2D(1, 0);
        Point2D rotatedPoint = Point2DExt.rotate(point, Math.PI / 2);
        assertEquals(0, rotatedPoint.getX(), 1e-10);
        assertEquals(1, rotatedPoint.getY(), 1e-10);
    }
}