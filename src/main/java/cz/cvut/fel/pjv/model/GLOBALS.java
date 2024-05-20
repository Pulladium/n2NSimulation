package cz.cvut.fel.pjv.model;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.ecs.EntityFamily;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.view.frames.WindowFrame;

import java.util.ArrayList;
import java.util.List;

public class GLOBALS {
    public static final double G = 5;

    public static List<Entity> getAllEntities(Engine engine) {
        EntityFamily movers = EntityFamily.create(CompAcceleration.class, CompColor.class, CompVelocity.class, CompRadius.class, CompSize.class, MassComponent.class);
        return engine.getEntities(movers);
    }

    public static final int TARGET_FPS = 60;
    public static final long ONE_SECOND_IN_NANOSECONDS = 1_000_000_000L;
    public static final long FRAME_DURATION = ONE_SECOND_IN_NANOSECONDS / TARGET_FPS;


    public static final int SIMULATION_UPDATE_RATE = 60;

    public static WindowFrame windowFrame = WindowFrame.get();
}
