package cz.cvut.fel.pjv.controllers;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.controllers.gsonCtrl.SimulationSaverGSON;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.utils.SimulationState;
import cz.cvut.fel.pjv.controllers.ecsHandlerComp.MoveableHandl;
import cz.cvut.fel.pjv.model.ecsSystems.N2mAtraction;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.*;


/**
 * Manages the simple attraction simulation.
 * <p>
 * Handles the setup, saving, loading, and drawing of the simulation state.
 * Interacts with {@link WindowFrame}, {@link Engine}, {@link SimulationState}, and {@link N2mAtraction}.
 */
public class SimpleAtraction {


    private final WindowFrame window;
    private final Engine engine;
    public SimulationState simulationState;

    /**
     * Constructs a new {@link SimpleAtraction} with the specified window and engine.
     *
     * @param window the {@link WindowFrame} to be used.
     * @param engine the {@link Engine} to be used.
     */
    public SimpleAtraction( WindowFrame window, Engine engine) {
        this.window = window;
        this.engine = engine;
        setup();
    }
    /**
     * Sets up the initial {@link #simulationState} with movers and adds the attraction system to the engine.
     */
    private void setup() {
        simulationState = new SimulationState();

        simulationState.createNwithoutAtractor(100);
        N2mAtraction n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
        engine.addSystem(n2mAtraction);
    }

    /**
     * Saves the current {@link #simulationState} using {@link SimulationSaverGSON}.
     */
    public void saveSimState(){

        SimulationSaverGSON simulationSaverGSON = new SimulationSaverGSON();
        simulationSaverGSON.setCurrentState(simulationState);
        simulationSaverGSON.saveSimStateGSON("src/main/resources/saves/");

    }
    /**
     * Loads the simulation state using {@link SimulationSaverGSON} and updates the engine.
     */
    public void loadSimState(){


        SimulationSaverGSON simulationSaverGSON = new SimulationSaverGSON();
        simulationState = simulationSaverGSON.loadSimStateGSON("src/main/resources/saves/");


        if( simulationState == null){
            log("Simulation state is null", Level.WARNING);
            return;
        }

        engine.removeAll();//only entities
        int sysCnt = engine.getNumOfSystems();

        log("Systems count: " + sysCnt, Level.INFO);
        for (int i = 0; i < sysCnt; i++) {
            engine.removeSystem(engine.getSystem(0));
        }
        sysCnt = engine.getNumOfSystems();

        log("Systems count: " + sysCnt, Level.INFO);
        //redraw after load
        draw(window.getGameLayoutCanvas().getGraphicsContext2D());
        N2mAtraction n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
        engine.addSystem(n2mAtraction);


    }
    /**
     * Selects a mover based on the given point.
     *
     * @param point the {@link Point2D} to check for a mover.
     * @return the selected {@link Mover}, or null if no mover is found.
     */
    public Mover selectMover(Point2D point){
       for (Mover mover : simulationState.getMovers()) {

          if(point.getX() <=  mover.getPosComp().position.getX() + mover.getSize().size  &&
                    point.getX() >=  mover.getPosComp().position.getX() - mover.getSize().size &&
                    point.getY() <=  mover.getPosComp().position.getY() + mover.getSize().size &&
                    point.getY() >=  mover.getPosComp().position.getY() - mover.getSize().size){
                return mover;
            }
        }
        return null;
    }


  /**
           * Draws the current {@link #simulationState} on the given {@link GraphicsContext}.
            * <p>
     * Clears the canvas, sets the background color, and translates and scales the canvas.
     * Iterates through all movers and updates their positions using {@link MoveableHandl}.
            * If the sun is present, it is also drawn.
            *
            * @param gc the {@link GraphicsContext} to draw on.
     */
    public void draw(GraphicsContext gc) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();


        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);


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
    }
    /**
     * Redraws the current {@link  #simulationState} on the given {@link GraphicsContext}.
     * <p>
     * Similar to {@link #draw(GraphicsContext)}, but does not update the positions of movers.
     * Clears the canvas, sets the background color, and translates and scales the canvas.
     * Iterates through all movers and draws them using {@link MoveableHandl}.
     * If the sun is present, it is also drawn.
     *
     * @param gc the {@link GraphicsContext} to redraw on.
     */
    public void redraw(GraphicsContext gc) {
        double WIDTH = gc.getCanvas().getWidth();
        double HEIGHT = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);


        gc.save();
        gc.translate(WIDTH / 2 + window.offsetX, HEIGHT / 2 + window.offsetY);

        gc.scale(window.getCanvasScale() , window.getCanvasScale() );

//        }
        for (Mover mover : simulationState.getMovers()) {
            //changePos
            MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);

            moveableHandl.show(gc);
        }

        if(simulationState.getSun() != null) {
            simulationState.getSun().currentEntity.getComponent(MoveableHandl.class).show(gc);
        }
        gc.restore();
    }


    public SimulationState getSimulationState() {
        return simulationState;
    }




}
