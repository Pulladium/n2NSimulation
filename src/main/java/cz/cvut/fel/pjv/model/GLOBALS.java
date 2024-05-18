package cz.cvut.fel.pjv.model;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.ecs.EntityFamily;
import cz.cvut.fel.pjv.model.ecsComponents.*;

import java.util.ArrayList;
import java.util.List;

public class GLOBALS {
    public static final double G = 0.4;

    public static List<Entity> getAllEntities(Engine engine) {
        EntityFamily movers = EntityFamily.create(CompAcceleration.class, CompColor.class, CompVelocity.class, CompRadius.class, CompSize.class, MassComponent.class);
        return engine.getEntities(movers);
    }

}
