package cz.cvut.fel.pjv.view.frames;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.controllers.SimpleAtraction;
import cz.cvut.fel.pjv.model.GLOBALS;
import cz.cvut.fel.pjv.view.ecsViewGUI.UiNewSim;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.*;
/**
 * Singleton class for the main window frame.
 * contains javafx {@link Parent}'s and view logic
 * contains animation loop and simulation thread
 */
public class WindowFrame {

    private int width, height;

    private String title;

    public int offsetX, offsetY = 0;

    Scene currentScene;

    HBox appHBox;

    AnchorPane appAncorPane;
    Canvas gameLayoutCanvas;
    Pane guiLayoutPane;
    private double canvasScale = 0.5;


    private Engine engine;

    UiNewSim uiNewSim = UiNewSim.get();

    private ScheduledExecutorService simulationExecutor;

    private volatile boolean running = true;

    private static AnimationTimer gameLoopAnim;


    private SimpleAtraction simpleAtraction;

    private static WindowFrame instance;

    /**
     * Private constructor for the WindowFrame singleton.
     */
    private WindowFrame() {
        this.width = 1200;
        this.height = 1000;
        this.title = "EngineFX";

    }

    /**
     * Creates the GUI layout pane.
     *
     * @return The parent node of the GUI layout pane.
     */
    private Parent createGUI(){
        guiLayoutPane = new Pane();

        guiLayoutPane.setBackground(new Background(new BackgroundFill(Color.web("#1E1E1E"), CornerRadii.EMPTY, null)));
        guiLayoutPane.setViewOrder(0.0);



//        userControl.setWindow(this);
        appHBox.getChildren().add(guiLayoutPane);
        guiLayoutPane.setVisible(false);
//        guiLayoutPane on width change
        guiLayoutPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                uiNewSim.sliderPane.setPrefWidth(newWidth.doubleValue());
                uiNewSim.sliderPane.setMaxWidth(newWidth.doubleValue());
                uiNewSim.sliderPane.setMinWidth(newWidth.doubleValue());
            }
        });
        return null;
    }

    /**
     * Creates the content of the window.
     *
     *
     *
 *          {@link HBox} {@link #appHBox} contains: <br>
     *
 *          {@link Pane} {@link #guiLayoutPane} {@link #createGUI()} <br>
 *          {@link AnchorPane} {@link #appAncorPane} contains:

     *      {@link Canvas} {@link #gameLayoutCanvas}
     *
     *
     *
     * @return The parent node of the window.
     */
    private Parent createContent() {



        gameLayoutCanvas = new Canvas(width, height);



        GraphicsContext gc = gameLayoutCanvas.getGraphicsContext2D();
//#011412
        gc.setFill(Color.web("#011412"));
        gc.fillRect(0, 0, gameLayoutCanvas.getWidth(), gameLayoutCanvas.getHeight()); // Заливаем весь Canvas черным цветом


        gc.save();
        gc.scale(canvasScale, canvasScale); // Масштабирование Canvas
        gc.restore();


        gameLayoutCanvas.setViewOrder(2.0);

        // Make the Canvas focusable
        gameLayoutCanvas.setFocusTraversable(true);


        appAncorPane = new AnchorPane();

        appAncorPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        appAncorPane.getChildren().add(gameLayoutCanvas);
//




        appHBox = new HBox();

        appHBox.getChildren().add(appAncorPane);

        createGUI();
        uiNewSim.setWindow(this);

        log("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth(), Level.INFO);






        // Add width and height listeners to the Hbox
        appHBox.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {

                log("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth(), Level.INFO);
//                System.out.println("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth());

                if(guiLayoutPane.isVisible()) {
                    appAncorPane.setMinWidth(newWidth.doubleValue() - guiLayoutPane.getWidth());
                    appAncorPane.setMaxWidth(newWidth.doubleValue() - guiLayoutPane.getWidth());
                }
                else{
                    appAncorPane.setMinWidth(newWidth.doubleValue());
                    appAncorPane.setMaxWidth(newWidth.doubleValue());
                }
            }
        });

        appHBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {


                log("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth(), Level.INFO);
//                System.out.println("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth());
                appAncorPane.setMinHeight(newHeight.doubleValue());
                appAncorPane.setMaxHeight(newHeight.doubleValue());


            }
        });
        // Add width and height listeners to the anchor pane
        appAncorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                gameLayoutCanvas.setWidth(newWidth.doubleValue());
                if(!running && simpleAtraction != null){
//                    simpleAtraction.draw(gameLayoutCanvas.getGraphicsContext2D());
                    simpleAtraction.redraw(gameLayoutCanvas.getGraphicsContext2D());
                }


            }
        });

        appAncorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
                gameLayoutCanvas.setHeight(newHeight.doubleValue());
                if(!running && simpleAtraction != null){
//                    simpleAtraction.draw(gameLayoutCanvas.getGraphicsContext2D());
                    simpleAtraction.redraw(gameLayoutCanvas.getGraphicsContext2D());
                }

            }
        });


        guiLayoutPane.setPrefWidth(100);
        return appHBox;
    }


    /**
     * Singleton method to get the instance of WindowFrame.
     *
     * @return The single instance of WindowFrame.
     */
    public static WindowFrame get(){
        if(instance == null){
            WindowFrame.instance = new WindowFrame();
        }
        return WindowFrame.instance;
    }
    /**
     * Runs the application window. Sets the scene and shows the stage.
     *
     * @param stage The primary stage for this application.
     */
    public void run(Stage stage){

        log("Running window: " + this.title + " with width: " + this.width + " and height: " + this.height, Level.INFO);
        init();

        stage.setScene(currentScene);
        stage.setTitle(title);
        stage.show();

    }

    /**
     * Initializes the window and sets up the scene.
     */
    private void init(){
//        System.out.println("Initializing window");
        log("Initializing window", Level.INFO);
        currentScene = new Scene(createContent(), width, height);

        //Init FX

    }





    /**
     * Starts the main loop of the application.
     *
     * @param engine The engine to be used in the simulation thread loop. {@link #createSimThread(Engine)}
     * @param simpleAtraction The SimpleAtraction instance to be used in JavaFx animation loop. {@link #createGameLoop(SimpleAtraction)}
     */
    public void loop(Engine engine, SimpleAtraction simpleAtraction){
        this.simpleAtraction = simpleAtraction;
        if ( gameLoopAnim == null){
            createSimThread(engine);
            createGameLoop( simpleAtraction);
        }



        gameLoopAnim.start();

        log("Looping window", Level.INFO);
    }
    /**
     * Stops the main loop of the application.
     */
    public void stopLoop(){
        gameLoopAnim.stop();
    }

    /**
     * Pauses the application.
     * @implNote Changes volatile running variable to false, which controls model update.
     * @implNote Pauses the animation loop .
     * @implNote Shows the GUI layout pane.
     */
    public void pause() {
        gameLoopAnim.stop();
        running = false;
        guiLayoutPane.setVisible(true);
        appAncorPane.setMinWidth(appHBox.getWidth() - 200);
        appAncorPane.setMaxWidth(appHBox.getWidth() - 200);
        guiLayoutPane.setPrefWidth(200);

    }


    /**
     * Resumes the application.
     * @implNote Changes volatile running variable to true, which controls model update.
     * @implNote Hides the GUI layout pane.
     * @implNote Starts javaFX animation loop .
     */
    public void resume() {

        running = true;
        guiLayoutPane.setVisible(false);
        appAncorPane.setMinWidth(appHBox.getWidth());
        appAncorPane.setMaxWidth(appHBox.getWidth());
        guiLayoutPane.setPrefWidth(0);
//        createSimThread(engine);
        gameLoopAnim.start();
    }

    /**
     * Checks if the application is volatile {@link #running}.
     *
     * @return True if the application is running, false otherwise.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Creates the JavaFx animation loop for the application.
     *
     * @param simpleAtraction The {@link SimpleAtraction} instance to be redrawn in the loop.
     */
    private void createGameLoop( SimpleAtraction simpleAtraction){


        gameLoopAnim = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                if (running && now - lastUpdate >= FRAME_DURATION) {
                    simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());
                    lastUpdate = now;
                }
            }
        };
    }


    /**
     * Adds a mouse click listener to the canvas.
     *
     * @param engine The engine to catch Entities, add and remove Entity from engine.
*      @implNote was needed to individual param setting for each mover
     */
    private void addCanvasListner(Engine engine){
        gameLayoutCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            log("Canvas width: " + gameLayoutCanvas.getWidth() + " height: " + gameLayoutCanvas.getHeight(), Level.INFO);
            log("Mouse Clicked at: " + event.getX() + ", " + event.getY(), Level.INFO);


            // Запрашиваем фокус для Canvas при клике
            gameLayoutCanvas.requestFocus();
            if(!running && simpleAtraction != null){
                // if click inside object pos + size find first one
                //without canvas.scale
//                Mover selectedMover = simpleAtraction.selectMover(new Point2D(event.getX() - windowFrame.gameLayoutCanvas.getWidth()/2, event.getY() - windowFrame.gameLayoutCanvas.getHeight() /2)) ;

                //with canvas.scale
                Mover selectedMover = simpleAtraction.selectMover(new Point2D((event.getX() - windowFrame.gameLayoutCanvas.getWidth()/2) / windowFrame.getCanvasScale(), (event.getY() - windowFrame.gameLayoutCanvas.getHeight() /2) / windowFrame.getCanvasScale())) ;
                if(selectedMover != null) {
                    log("Selected mover: " + selectedMover.getPosComp().position.toString(), Level.INFO);
//                    System.out.println("Selected mover: " + selectedMover.getPosComp().position.toString());
//                    selectedMover.setSize(100);
                    log("Selected mover size: " + selectedMover.getSize().size, Level.INFO);
//                    System.out.println("Selected mover size: " + selectedMover.getSize().size);

//                    selectedMover.setSize(selectedMover.getSize().size + 100);

//                    userControl.showCompControl(selectedMover.currentEntity);
//
                    //remove from entity
                    engine.removeEntity(selectedMover.currentEntity);

                    engine.update(0.0);

                    Mover newMover = new Mover(selectedMover.getPosComp().position.getX(),
                            selectedMover.getPosComp().position.getY(),
                            selectedMover.getVelComp().getVelocity().getX(),
                            selectedMover.getVelComp().getVelocity().getY(),
                    selectedMover.getMassComp().mass.getValue(), selectedMover.getSize().size + 100, selectedMover.getColor().color);
                    engine.addEntity(newMover.getCurrentEntity());

                    simpleAtraction.simulationState.getMovers().remove(selectedMover);
                    simpleAtraction.simulationState.getMovers().add(newMover);
//                    getAllEntities(engine).remove(selectedMover.currentEntity);
//                    getAllEntities(engine).add(selectedMover.currentEntity);

//                    System.out.println("New mover size: " + newMover.getSize().size);
                    log("New mover size: " + newMover.getSize().size, Level.INFO);
                    //draw двигает объекты
                    simpleAtraction.redraw(gameLayoutCanvas.getGraphicsContext2D());
                }
            }
        });
    }

    /**
     * Creates a simulation thread {@link #simulationExecutor} in purpose to update the engine.
     * @implNote adds a listener to the canvas {@link #addCanvasListner(Engine)}.
     *
     * @param engine The engine which will be updated in the thread.
 *    Uses {@link GLOBALS#SIMULATION_UPDATE_RATE} to set the rate of the simulation.
     *                   
     *    */
    private void createSimThread(Engine engine){


        this.engine = engine;
        addCanvasListner(engine);


        // Создаем и запускаем поток для обновления симуляции
        simulationExecutor = Executors.newSingleThreadScheduledExecutor();
        simulationExecutor.scheduleAtFixedRate(() -> {
            if(running) {
                engine.update(1.0 / SIMULATION_UPDATE_RATE);

            }
        }, 0, 1000 / SIMULATION_UPDATE_RATE, TimeUnit.MILLISECONDS);

    }

    /**
     * Shuts down the simulation thread.
     * @implNote {@link #simulationExecutor}
     */
    public void simShutdown(){
        if (simulationExecutor != null && !simulationExecutor.isShutdown()) {
            simulationExecutor.shutdown();
        }
    }





    //getters
    public double getCanvasScale() {
        return canvasScale;
    }
    public Scene getCurrentScene() {
        return currentScene;
    }

    public Canvas getGameLayoutCanvas() {
        return gameLayoutCanvas;
    }
    public Pane getGuiLayoutPane() {
        return guiLayoutPane;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public AnchorPane getAppPane() {
        return appAncorPane;
    }
    public SimpleAtraction getSim() {
        return simpleAtraction;
    }


    //setters
    public void setCanvasScale(double canvasScale) {
        this.canvasScale = canvasScale;
    }

    public Engine getEngine() {
        return engine;
    }
}
