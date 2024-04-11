package cz.cvut.fel.pjv.controller;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.SelestrialBody;
import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.view.frames.WindowFrame;

import java.util.List;

public class SimulationNplanet {

    List<SelestrialBody> selestrialBodies;
    private final UserControl gui;
    private final WindowFrame window;
    private final Engine engine;

    public SimulationNplanet(UserControl gui, WindowFrame window, Engine engine){
        this.gui = gui;
        this.window = window;
        this.engine = engine;
    }

    public void runRK4(){}
}
