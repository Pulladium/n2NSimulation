package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.SimulationState;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationStateTest {

    private SimulationState simulationState;

    @BeforeEach
    public void setUp() {
        simulationState = new SimulationState();
    }

    @Test
    public void testCreateDefaultState() {
        simulationState.createDefaultState();
        assertNotNull(simulationState.getMovers());
        assertEquals(100, simulationState.getMovers().size());
        assertNotNull(simulationState.getSun());
        assertEquals(0, simulationState.getSun().getPosComp().position.getX());
        assertEquals(0, simulationState.getSun().getPosComp().position.getY());
    }

    @Test
    public void testCreateWithoutAtractor() {
        simulationState.createNwithoutAtractor(10);
        assertNotNull(simulationState.getMovers());
        assertEquals(10, simulationState.getMovers().size());

        assertNull(simulationState.getSun());
    }

    @Test
    public void testCreateWithAtractor() {
        Mover sun = new Mover(0, 0, 0, 0, 500, 20, Color.GREEN);
        simulationState.createNwithAtractor(10, sun);
        assertNotNull(simulationState.getMovers());
        assertEquals(10, simulationState.getMovers().size());

        assertNotNull(simulationState.getSun());

        assertEquals(0, simulationState.getSun().getVelComp().getVelocity().getX());
    }
}