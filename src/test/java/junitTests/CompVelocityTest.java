package junitTests;

import cz.cvut.fel.pjv.model.ecsComponents.CompVelocity;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompVelocityTest {

    @Test
    public void testVelocityInitialization() {
        CompVelocity velocity = new CompVelocity(5, 10);
        assertEquals(new Point2D(5, 10), velocity.getVelocity());
    }

    @Test
    public void testSetVelocity() {
        CompVelocity velocity = new CompVelocity(5, 10);
        velocity.setVelocity(new Point2D(15, 20));
        assertEquals(new Point2D(15, 20), velocity.getVelocity());
    }
}