package cz.cvut.fel.pjv.jsPORT;

import at.fhooe.mtd.ecs.Engine;
//import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.controller.SimulationSaverGSON;
import cz.cvut.fel.pjv.model.SimulationState;
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

    public SimulationState simulationState;
//    private ArrayList<Mover> movers = new ArrayList<>();
//    private  Mover sun;
//    public ArrayList<Mover> getMovers() {
//        return movers;
//    }

    public SimpleAtraction( WindowFrame window, Engine engine) {
//        this.gui = gui;
        this.window = window;
        this.engine = engine;
        setup();
    }


    public void saveSimState(){
        //game will be paused
        System.out.println("Saving sim state");

        SimulationSaverGSON simulationSaverGSON = new SimulationSaverGSON();
        simulationSaverGSON.setCurrentState(simulationState);
        simulationSaverGSON.saveSimStateGSON("src/main/resources/saves/");

    }

    public void loadSimState(){
        //game will be paused
        System.out.println("Loading sim state");

        SimulationSaverGSON simulationSaverGSON = new SimulationSaverGSON();
        simulationState = simulationSaverGSON.loadSimStateGSON("src/main/resources/saves/");


        if( simulationState == null){
            System.out.println("Simulation state is null");
            return;
        }
        if(n2mAtraction != null){
            engine.removeSystem(n2mAtraction);
        }
        //redraw after load
        draw(window.getGameLayoutCanvas().getGraphicsContext2D());
        n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
        engine.addSystem(n2mAtraction);


    }
    public Mover selectMover(Point2D point){
//        point = new Point2D(point.getX() - (double) window.getWidth() /2 - window.offsetX, point.getY() - (double) window.getHeight() /2 - window.offsetY);
        for (Mover mover : simulationState.getMovers()) {
            System.out.println("Mover: " + mover.getPosComp().position.toString() + " point: " + point.toString());
            if(point.getX() <=  mover.getPosComp().position.getX() + mover.size.size  &&
                    point.getX() >=  mover.getPosComp().position.getX() - mover.size.size &&
                    point.getY() <=  mover.getPosComp().position.getY() + mover.size.size &&
                    point.getY() >=  mover.getPosComp().position.getY() - mover.size.size){
                return mover;
            }
        }
        return null;
    }

    private void setup() {
        simulationState = new SimulationState();
//        simulationState.createDefaultState();

//        simulationState.createNwithoutAtractor(50);

//        simulationState.createDefaultState();
        simulationState.createNwithoutAtractor(3);
        n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
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
        gc.translate(WIDTH / 2 + window.offsetX, HEIGHT / 2 + window.offsetY);

        gc.scale(window.getCanvasScale() , window.getCanvasScale() );

        for (Mover mover : simulationState.getMovers()) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
            moveableHandl.update();
            moveableHandl.show(gc);
        }

        if(simulationState.getSun() != null) {
            simulationState.getSun().currentEntity.getComponent(MoveableHandl.class).show(gc);
        }
        gc.restore();
//        sun.show(gc, WIDTH / 2, HEIGHT / 2);
    }

    public void redraw(GraphicsContext gc) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

//ODO To engine UPS

        //ODO To engine finish

        //TODO ToCanvasRenderer FPS
        gc.save();
        gc.translate(WIDTH / 2 + window.offsetX, HEIGHT / 2 + window.offsetY);

        gc.scale(window.getCanvasScale() , window.getCanvasScale() );

        for (Mover mover : simulationState.getMovers()) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
//            moveableHandl.update();
            moveableHandl.show(gc);
        }

        if(simulationState.getSun() != null) {
            simulationState.getSun().currentEntity.getComponent(MoveableHandl.class).show(gc);
        }
        gc.restore();
//        sun.show(gc, WIDTH / 2, HEIGHT / 2);
    }




}
