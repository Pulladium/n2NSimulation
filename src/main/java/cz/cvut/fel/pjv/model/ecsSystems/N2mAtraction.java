package cz.cvut.fel.pjv.model.ecsSystems;

import at.fhooe.mtd.ecs.EngineSystem;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.ecsComponents.MoveableHandl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class N2mAtraction extends EngineSystem {


    private ArrayList<Mover> movers = new ArrayList<>();

    public N2mAtraction(ArrayList<Mover> movers) {
        this.movers = movers;
    }
    public void draw(GraphicsContext gc) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();


        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

//TODO To engine UPS
        for (Mover mover : movers) {
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
//            MoveableHandl sunMoveableHandl = sun.currentEntity.getComponent(MoveableHandl.class);


//            sunMoveableHandl.attract(moveableHandl.getEntity());
            for (Mover other : movers) {
                if (mover != other) {
                    MoveableHandl otherMoveableHandl = other.currentEntity.getComponent(MoveableHandl.class);
                    moveableHandl.attract(otherMoveableHandl.getEntity());
                }
            }

        }
        //TODO To engine finish

        //TODO ToCanvasRenderer FPS
        gc.save();
        gc.translate(WIDTH / 2, HEIGHT / 2);

        for (Mover mover : movers) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
            moveableHandl.update();
            moveableHandl.show(gc);
        }

//        sun.currentEntity.getComponent(MoveableHandl.class).show(gc);

        gc.restore();
//        sun.show(gc, WIDTH / 2, HEIGHT / 2);
    }

}
