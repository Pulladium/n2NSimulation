package junitTests.moverTests;

import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoverMassUpdateTest {

    @Test
    public void testMoverMassUpdate() {
        Mover mover = new Mover(0, 0, 1, 1, 10, 20, Color.GREEN);
        mover.getMassComp().mass.set(50);
        assertEquals(50, mover.getMassComp().mass.get());
    }
}