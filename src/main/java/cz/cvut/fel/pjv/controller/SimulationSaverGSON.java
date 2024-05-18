package cz.cvut.fel.pjv.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.cvut.fel.pjv.model.JsonAdapters.*;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationSaverGSON {

    SimulationState currentState;

    public void setCurrentState(SimulationState currentState) {
        this.currentState = currentState;
    }

    public void saveSimStateGSON(String path2save){

        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CompColor.class, new ColorCompAdapter())
                .registerTypeAdapter(MassComponent.class, new MassCompAdapter())
                .registerTypeAdapter(Point2DExt.class , new Point2DExtAdapter())
        .create();


        try(FileWriter writer = new FileWriter(path2save)){
            gson.toJson(currentState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimulationState loadSimStateGSON(String path2load) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        SimulationState state = null;
        try (FileReader reader = new FileReader(path2load)) {
            state = gson.fromJson(reader, SimulationState.class);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }
}
