package junitTests.componentTests;

import cz.cvut.fel.pjv.model.ecsComponents.CompPosition;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompPositionTest {

    @Test
    public void testPositionInitialization() {
        CompPosition position = new CompPosition(10, 20);
        assertEquals(new Point2DExt(10, 20), position.getPosition());
    }

    @Test
    public void testSetPosition() {
        CompPosition position = new CompPosition(10, 20);
        position.setPosition(new Point2DExt(30, 40));
        assertEquals(new Point2DExt(30, 40), position.getPosition());
    }
}