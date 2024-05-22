package junitTests.moverTests;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoverVelocityUpdateTest {

    @Test
    public void testMoverVelocityUpdate() {
        Mover mover = new Mover(0, 0, 1, 1, 10, 20, Color.GREEN);
        mover.getVelComp().setVelocity(new Point2DExt(5, 5));
        assertEquals(new Point2DExt(5, 5), mover.getVelComp().getVelocity());
    }
}