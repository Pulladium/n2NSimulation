// File: src/test/java/cz/cvut/fel/pjv/jsPORT/SimpleAtractionSelectMoverTest.java


import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Itegracni test2
public class SimpleAtractionSelectMoverTest {

    private SimpleAtraction simpleAtraction;
    private Engine engine;
    private WindowFrame windowFrame;

    @BeforeEach
    public void setUp() {
        windowFrame = WindowFrame.get();
        engine = new Engine();
        simpleAtraction = new SimpleAtraction(windowFrame, engine);

        // Add some movers to the simulation state for testing
        SimulationState simulationState = new SimulationState();
        simulationState.getMovers().add(new Mover(100, 100, 0, 0, 10, 20, Color.RED));
        simulationState.getMovers().add(new Mover(200, 200, 0, 0, 10, 20, Color.BLUE));
        simpleAtraction.simulationState = simulationState;
    }

    @Test
    public void testSelectMoverWithinBounds() {
        Point2D point = new Point2D(100, 100);
        Mover selectedMover = simpleAtraction.selectMover(point);
        assertNotNull(selectedMover);
        assertEquals(100, selectedMover.getPosComp().getPosition().getX());
        assertEquals(100, selectedMover.getPosComp().getPosition().getY());
    }

    @Test
    public void testSelectMoverOutsideBounds() {
        Point2D point = new Point2D(300, 300);
        Mover selectedMover = simpleAtraction.selectMover(point);
        assertNull(selectedMover);
    }

    @Test
    public void testSelectMoverOnEdge() {
        Point2D point = new Point2D(120, 100); // On the edge of the first mover
        Mover selectedMover = simpleAtraction.selectMover(point);
        assertNotNull(selectedMover);
        assertEquals(100, selectedMover.getPosComp().getPosition().getX());
        assertEquals(100, selectedMover.getPosComp().getPosition().getY());
    }
}