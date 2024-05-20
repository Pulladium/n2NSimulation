package cz.cvut.fel.pjv.model.ecsSystems;

import cz.cvut.fel.pjv.controller.SimulationSaverGSON;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.jsPORT.Mover;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationSaverGSONTest {

    private SimulationSaverGSON simulationSaverGSON;
    private SimulationState simulationState;

    @BeforeEach
    public void setUp() {
        simulationSaverGSON = new SimulationSaverGSON();
        ArrayList<Mover> movers = new ArrayList<>();
        movers.add(new Mover(0, 0, 1, 1, 10, 10, Color.RED));
        simulationState = new SimulationState(movers, new Mover(0, 0, 0, 0, 500, 20, Color.YELLOW));
        simulationSaverGSON.setCurrentState(simulationState);
    }

    @Test
    public void testSaveSimStateGSON() {
        String dirPath = "src/test/resources/saves";
        simulationSaverGSON.saveSimStateGSON(dirPath);

        File dir = new File(dirPath);
        assertTrue(dir.exists() && dir.isDirectory());

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        assertNotNull(files);
        assertTrue(files.length > 0);
    }

    @Test
    public void testLoadSimStateGSON() {
        String dirPath = "src/test/resources/saves";
        simulationSaverGSON.saveSimStateGSON(dirPath);

        SimulationState loadedState = simulationSaverGSON.loadSimStateGSON(dirPath);
        assertNotNull(loadedState);
        assertEquals(simulationState.getMovers().size(), loadedState.getMovers().size());
        assertEquals(simulationState.getSun().getMassComp().mass.get(), loadedState.getSun().getMassComp().mass.get());
    }
}