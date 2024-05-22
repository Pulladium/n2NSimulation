package junitTests.moverTests;

import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoverPositionUpdateTest {

    @Test
    public void testMoverPositionUpdate() {
        Mover mover = new Mover(0, 0, 1, 1, 10, 20, Color.GREEN);
        mover.getPosComp().setPosition(new Point2DExt(10, 10));
        assertEquals(new Point2DExt(10, 10), mover.getPosComp().getPosition());
    }
}