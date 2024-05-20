package cz.cvut.fel.pjv.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.GLOBALS.*;
import cz.cvut.fel.pjv.model.JsonAdapters.*;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;


public class SimulationSaverGSON {

    SimulationState currentState;

    public void setCurrentState(SimulationState currentState) {
        this.currentState = currentState;
    }

    public void saveSimStateGSON(String dirPath){


        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CompColor.class, new ColorCompAdapter())
                .registerTypeAdapter(MassComponent.class, new MassCompAdapter())
                .registerTypeAdapter(Point2DExt.class , new Point2DExtAdapter())
        .create();

// Создание уникального имени файла на основе текущей даты и времени
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //not actualy a dirPath now
        String filePath = dirPath + "/simulation_state_" + timestamp + ".json";

        try(FileWriter writer = new FileWriter(filePath)){
            gson.toJson(currentState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimulationState loadSimStateGSON(String dirPath) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CompColor.class, new ColorCompAdapter())
                .registerTypeAdapter(MassComponent.class, new MassCompAdapter())
                .registerTypeAdapter(Point2DExt.class , new Point2DExtAdapter()).
        create();

        // Проверка существования директории
        Path directory = Paths.get(dirPath);
        if (!Files.exists(directory)) {
            System.err.println("Directory does not exist: " + directory);
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
        return state;
    }
}
