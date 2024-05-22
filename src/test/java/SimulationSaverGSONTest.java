import cz.cvut.fel.pjv.controllers.gsonCtrl.SimulationSaverGSON;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.utils.SimulationState;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//integrcni test 1
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
    public void testSaveSimStateGSON() throws Exception {
        String dirPath = "src/test/resources/saves/";
        Files.createDirectories(Paths.get(dirPath));
        simulationSaverGSON.saveSimStateGSON(dirPath);

        File dir = new File(dirPath);
        File[] files = dir.listFiles((d, name) -> name.startsWith("simulation_state_") && name.endsWith(".json"));
        assertTrue(files != null && files.length > 0);
    }

    @Test
    public void testLoadSimStateGSON() {
        String dirPath = "src/test/resources/saves/";
        simulationSaverGSON.saveSimStateGSON(dirPath);

        SimulationState loadedState = simulationSaverGSON.loadSimStateGSON(dirPath);
        assertNotNull(loadedState);
        assertEquals(simulationState.getMovers().size(), loadedState.getMovers().size());
        assertEquals(simulationState.getSun().getMassComp().mass.get(), loadedState.getSun().getMassComp().mass.get());
    }
}