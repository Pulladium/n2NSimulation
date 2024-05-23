package cz.cvut.fel.pjv.controllers.gsonCtrl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters.ColorCompAdapter;
import cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters.MassCompAdapter;
import cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters.Point2DExtAdapter;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters.*;
import cz.cvut.fel.pjv.model.utils.SimulationState;
import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
import cz.cvut.fel.pjv.model.utils.Point2DExt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.stream.Stream;

import static cz.cvut.fel.pjv.model.GLOBALS.log;

/**
 * Handles saving and loading of the simulation state using GSON.
 * <p>
 * Uses {@link Gson} for serialization and deserialization of {@link SimulationState}.
 * Registers custom adapters for components like {@link CompColor}, {@link MassComponent}, and {@link Point2DExt}.
 */
public class SimulationSaverGSON {

    SimulationState currentState;

    /**
     * Sets the current simulation state to be saved.
     *
     * @param currentState the {@link SimulationState} to be set.
     */
    public void setCurrentState(SimulationState currentState) {
        this.currentState = currentState;
    }

    /**
     * Saves the current simulation state to a JSON file in the specified directory.
     * <p>
     * Uses {@link GsonBuilder} to create a GSON instance with pretty printing and custom adapters.
     * Generates a unique filename based on the current timestamp.
     * Creates the directory if it does not exist.
     *
     * @param dirPath the directory path where the JSON file will be saved.
     */
    public void saveSimStateGSON(String dirPath){


        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CompColor.class, new ColorCompAdapter())
                .registerTypeAdapter(MassComponent.class, new MassCompAdapter())
                .registerTypeAdapter(Point2DExt.class , new Point2DExtAdapter())
        .create();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //not actualy a dirPath now
        String filePath = dirPath + "/simulation_state_" + timestamp + ".json";

        Path dir = Paths.get(dirPath);
        if(!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter writer = new FileWriter(filePath)){
            gson.toJson(currentState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads the most recent simulation state from a JSON file in the specified directory.
     * <p>
     * Uses {@link GsonBuilder} to create a GSON instance with custom adapters.
     * Searches for the latest JSON file in the directory and deserializes it into a {@link SimulationState}.
     * Recreates the entities for each {@link Mover} in the loaded state.
     *
     * @param dirPath the directory path from where the JSON file will be loaded.
     * @return the loaded {@link SimulationState}, or null if an error occurs.
     */
    public SimulationState loadSimStateGSON(String dirPath) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CompColor.class, new ColorCompAdapter())
                .registerTypeAdapter(MassComponent.class, new MassCompAdapter())
                .registerTypeAdapter(Point2DExt.class , new Point2DExtAdapter()).
        create();

        // Проверка существования директории
        Path directory = Paths.get(dirPath);
        if (!Files.exists(directory)) {
            log("Directory does not exist: " + directory, Level.WARNING);
            return null;
        }

        SimulationState state = null;
        // Поиск последнего созданного файла в директории
        try (Stream<Path> paths = Files.list(directory)) {
            Path lastFilePath = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .max((p1, p2) -> {
                        String fileName1 = p1.getFileName().toString();
                        String fileName2 = p2.getFileName().toString();
                        String timestamp1 = fileName1.substring(fileName1.lastIndexOf('_') + 1, fileName1.lastIndexOf('.'));
                        String timestamp2 = fileName2.substring(fileName2.lastIndexOf('_') + 1, fileName2.lastIndexOf('.'));
                        return timestamp1.compareTo(timestamp2);
                    })
                    .orElseThrow(() -> new IOException("No JSON files found in directory"));

            try (FileReader reader = new FileReader(dirPath + lastFilePath.getFileName().toString())) {


                state = gson.fromJson(reader, SimulationState.class);


                // Создание новых Entity для каждого Mover
                for (Mover mover : state.getMovers()) {
                    mover.currentEntity = mover.newMover();
                }
                if (state.getSun() != null) {
                    state.getSun().currentEntity = state.getSun().newMover();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            log("Simulation state loaded", Level.INFO);
        }
        return state;
    }
}
