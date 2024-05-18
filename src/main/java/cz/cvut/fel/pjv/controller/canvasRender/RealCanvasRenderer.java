package cz.cvut.fel.pjv.controller.canvasRender;

import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.GLOBALS;
import cz.cvut.fel.pjv.model.ecsComponents.MoveableHandl;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RealCanvasRenderer {



GraphicsContext gc;

    public RealCanvasRenderer(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
    }


    public void clear() {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

    }
    public void draw(Engine engine) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();

        gc.save();
        gc.translate(WIDTH / 2, HEIGHT / 2);

//        for (Mover mover : movers) {
//            //changePos
//            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
//            moveableHandl.update();
//            moveableHandl.show(gc);
//        }

        for(Entity mover: GLOBALS.getAllEntities(engine)){
            MoveableHandl moveableHandl = mover.getComponent(MoveableHandl.class);
            moveableHandl.update();
            moveableHandl.show(gc);
        }



//        sun.currentEntity.getComponent(MoveableHandl.class).show(gc, WIDTH / 2, HEIGHT / 2);
        gc.restore();
//        sun.show(gc, WIDTH / 2, HEIGHT / 2);

    }
    /*
    gc.save();
        gc.translate(WIDTH / 2, HEIGHT / 2);

        for (Mover mover : movers) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
            moveableHandl.update();
            moveableHandl.show(gc);
        }

        gc.restore();
     */
}
