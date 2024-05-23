package cz.cvut.fel.pjv.model;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.ecs.EntityFamily;
import cz.cvut.fel.pjv.Main;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Global constants and methods
 */
public class GLOBALS {
    public static double G = 0.4;

    public static void setG(double g) {
        G = g;
    }

    public static final Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); Level level = Level.INFO;

    public static void log(String message, Level level) {
        globalLogger.setLevel(level);
        if(level == Level.WARNING){
            showAlert(Alert.AlertType.WARNING, "Warning", "Warning", message);
        }else {
            globalLogger.log(level, message);
        }
    }
    private static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }


    public static List<Entity> getAllEntities(Engine engine) {
        EntityFamily movers = EntityFamily.create(CompAcceleration.class, CompColor.class, CompVelocity.class, CompSize.class, MassComponent.class);
        return engine.getEntities(movers);
    }

    public static final int TARGET_FPS = 60;
    public static final long ONE_SECOND_IN_NANOSECONDS = 1_000_000_000L;
    public static final long FRAME_DURATION = ONE_SECOND_IN_NANOSECONDS / TARGET_FPS;


    public static final int SIMULATION_UPDATE_RATE = 60;

    public static WindowFrame windowFrame = WindowFrame.get();

    public final static String dataDirParh = "data/";
}
