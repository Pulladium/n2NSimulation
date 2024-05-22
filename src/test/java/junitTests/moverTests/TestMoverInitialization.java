package junitTests.moverTests;

import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMoverInitialization {
    @Test
    public void testMoverInitialization() {
        Mover mover = new Mover(0, 0, 1, 1, 10, 20, Color.GREEN);
        assertEquals(new Point2DExt(0, 0), mover.getPosComp().getPosition());
        assertEquals(new Point2DExt(1, 1), mover.getVelComp().getVelocity());
        assertEquals(10, mover.getMassComp().mass.get());
        assertEquals(20, mover.getSize().size);
        assertEquals(Color.GREEN, mover.getColor().color);
    }
}
