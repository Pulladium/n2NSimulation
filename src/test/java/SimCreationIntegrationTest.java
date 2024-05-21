import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimCreationIntegrationTest {
    private SimpleAtraction simpleAtraction;
    private WindowFrame windowFrame;

    @BeforeEach
    public void setUp() {
        windowFrame = WindowFrame.get();
        simpleAtraction = new SimpleAtraction(windowFrame, new Engine());
    }

    @Test
    public void testSimulationWithSun() {
        // Step 1: Set parameter "Existuje slunce v simulaci" to true
        boolean sunPresent = true;
        simpleAtraction.simulationState = new SimulationState();

        // Step 2: Set sun position
        Mover sun = new Mover(0, 0, 0, 0, 100, 100, Color.YELLOW);
//        simpleAtraction.simulationState.setSun(sun);
        assertEquals(new Point2DExt(0, 0), sun.getPosComp().getPosition());

        // Step 3: Set sun velocity
        sun.getVelComp().setVelocity(new Point2D(0, 0));
        assertEquals(new Point2DExt(0, 0), sun.getVelComp().getVelocity());

        // Step 4: Set sun mass
        sun.getMassComp().mass.set(100);
        assertEquals(100, sun.getMassComp().mass.get());

        // Step 5: Set sun size
        sun.getSize().size = 100;
        assertEquals(100, sun.getSize().size);

        // Step 6: Set number of bodies
        int numBodies = 5;
        simpleAtraction.simulationState.createNwithAtractor(numBodies, sun);
        assertEquals(numBodies, simpleAtraction.simulationState.getMovers().size());

        // Step 7: Start new simulation
        windowFrame.loop(new Engine(), simpleAtraction);
        assertTrue(windowFrame.isRunning());

        // Step 8: Verify simulation is running and sun is present
        assertNotNull(simpleAtraction.simulationState.getSun());
    }

    @Test
    public void testSimulationWithoutSun() {
        // Step 9: Set parameter "Existuje slunce v simulaci" to false
        boolean sunPresent = false;
        simpleAtraction.simulationState = new SimulationState();


        // Step 10: Set number of bodies
        int numBodies = 5;
        simpleAtraction.simulationState.createNwithoutAtractor(numBodies);
        assertEquals(numBodies, simpleAtraction.simulationState.getMovers().size());

        // Step 11: Start new simulation
        windowFrame.loop(new Engine(), simpleAtraction);
        assertTrue(windowFrame.isRunning());

        // Step 12: Verify simulation is running and sun is not present
        assertNull(simpleAtraction.simulationState.getSun());
    }
}
