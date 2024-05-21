package junitTests.componentTests;

import cz.cvut.fel.pjv.model.ecsComponents.CompAcceleration;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompAccelerationTest {

    @Test
    public void testAccelerationInitialization() {
        CompAcceleration acceleration = new CompAcceleration(2, 4);
        assertEquals(new Point2DExt(2, 4), acceleration);
    }

    @Test
    public void testSetAcceleration() {
        CompAcceleration acceleration = new CompAcceleration(2, 4);
        acceleration.setAcceleration(new Point2DExt(6, 8));
        assertEquals(new Point2D(6, 8), acceleration);
    }
}