package cz.cvut.fel.pjv.jsPORT;

import at.fhooe.mtd.ecs.Engine;
//import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.model.ecsComponents.MoveableHandl;
import cz.cvut.fel.pjv.model.ecsSystems.N2mAtraction;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt.random2D;
import static cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt.rotate;

public class SimpleAtraction {
//    private final double G = 0.4;


//    private final UserControl gui;
    private final WindowFrame window;
    private final Engine engine;

    N2mAtraction n2mAtraction;

    private ArrayList<Mover> movers = new ArrayList<>();
    private  Mover sun;
    public ArrayList<Mover> getMovers() {
        return movers;
    }

    public SimpleAtraction( WindowFrame window, Engine engine) {
//        this.gui = gui;
        this.window = window;
        this.engine = engine;
        setup();
    }


    private void setup() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Point2D pos = random2D().multiply(random.nextDouble() * 50 + 150);
            Point2D vel = pos.normalize().multiply(random.nextDouble() * 5 + 10);
            vel = rotate(vel, Math.PI / 2);
            double m = random.nextDouble() * 5 + 10;
            double size = random.nextDouble() * 10 + 5;



            movers.add(new Mover(pos.getX(), pos.getY(), vel.getX(), vel.getY(), m, size, randomColor()));
        }
        sun = new Mover(0, 0, 0, 0, 500, 20, Color.YELLOW);


        n2mAtraction = new N2mAtraction(movers, sun);
        engine.addSystem(n2mAtraction);
    }
    public void draw(GraphicsContext gc) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();


        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

//ODO To engine UPS

        //ODO To engine finish

        //TODO ToCanvasRenderer FPS
        gc.save();
        gc.translate(WIDTH / 2, HEIGHT / 2);

        for (Mover mover : movers) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
            moveableHandl.update();
            moveableHandl.show(gc);
        }

        sun.currentEntity.getComponent(MoveableHandl.class).show(gc);

        gc.restore();
//        sun.show(gc, WIDTH / 2, HEIGHT / 2);
    }


    private Color randomColor() {
        Random random = new Random();
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }


}
