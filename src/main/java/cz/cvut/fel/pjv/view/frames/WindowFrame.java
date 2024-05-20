package cz.cvut.fel.pjv.view.frames;

import at.fhooe.mtd.ecs.Engine;
//import cz.cvut.fel.pjv.controller.canvasRender.CanvasRenderer;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import cz.cvut.fel.pjv.view.ecsViewGUI.UiNewSim;
import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
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

import static cz.cvut.fel.pjv.model.GLOBALS.*;

public class WindowFrame {

    private int width, height;

    private String title;

    public int offsetX, offsetY = 0;


    //currentScene --> appAncorPane
    //appAncorPane --> gameLayoutCanvas
    //appAncorPane --> GuiLayoutPane
    Scene currentScene;

    HBox appHBox;

    AnchorPane appAncorPane;
    Canvas gameLayoutCanvas;
    Pane guiLayoutPane;
    private double canvasScale = 0.5;


    private Engine engine;

    UiNewSim uiNewSim = UiNewSim.get();
//    UserControl userControl = UserControl.get();


    private ScheduledExecutorService simulationExecutor;

    private volatile boolean running = true;

    private static AnimationTimer gameLoopAnim;


    private SimpleAtraction simpleAtraction;



    private static WindowFrame instance;

    private WindowFrame() {
        this.width = 1200;
        this.height = 1000;
        this.title = "EngineFX";

    }

    private Parent createGUI(){
        guiLayoutPane = new Pane();

        guiLayoutPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
        guiLayoutPane.setViewOrder(0.0);

//        AnchorPane.setTopAnchor(guiLayoutPane, 0.0);
////        AnchorPane.setLeftAnchor(guiLayoutPane, 0.0);
//        AnchorPane.setBottomAnchor(guiLayoutPane, 0.0);
//        AnchorPane.setRightAnchor(guiLayoutPane, 0.0);
//        guiLayoutPane.setPrefWidth(100);
//        guiLayoutPane.setPrefWidth(100);


        System.out.println("GuiLayoutPane create width: " + guiLayoutPane.getWidth());


//        userControl.setWindow(this);
        appHBox.getChildren().add(guiLayoutPane);
        guiLayoutPane.setVisible(false);
        return null;
    }

    private Parent createContent() {



        gameLayoutCanvas = new CanvasFrame(width, height);


//        gameLayoutPane.autosize();
        GraphicsContext gc = gameLayoutCanvas.getGraphicsContext2D();

//        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameLayoutCanvas.getWidth(), gameLayoutCanvas.getHeight()); // Заливаем весь Canvas черным цветом

        gc.save();
        gc.scale(canvasScale, canvasScale); // Масштабирование Canvas
        gc.restore();
//        gameLayoutPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        gameLayoutCanvas.setViewOrder(2.0);

        // Make the Canvas focusable
        gameLayoutCanvas.setFocusTraversable(true);


        appAncorPane = new AnchorPane();
//        appAncorPane.autosize();
        appAncorPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, null)));
        appAncorPane.getChildren().add(gameLayoutCanvas);
//







        appHBox = new HBox();
//        appHBox.autosize();

        appHBox.getChildren().add(appAncorPane);

        createGUI();
        uiNewSim.setWindow(this);

//        appAncorPane.setMinWidth(appHBox.getWidth()- 200);
//        appAncorPane.setMaxWidth(appHBox.getWidth() - 200);
//        gameLayoutCanvas.setWidth(appHBox.getWidth() - 200);
        System.out.println("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth());



        //сначла нео

        // Add width and height listeners to the Hbox
        appHBox.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {

                System.out.println("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth());

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


                System.out.println("GuiLayoutPane height: " + guiLayoutPane.getHeight() + " width: " + guiLayoutPane.getWidth());
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


    //need 1 win
    public static WindowFrame get(){
        if(instance == null){
            WindowFrame.instance = new WindowFrame();
        }
        return WindowFrame.instance;
    }
    public void run(Stage stage){
        System.out.println("Running window: " + this.title + " with width: " + this.width + " and height: " + this.height);

        init();

        stage.setScene(currentScene);
        stage.setTitle(title);
        stage.show();

//        loop(engine);
    }

    private void init(){
        System.out.println("Initializing window");
        currentScene = new Scene(createContent(), width, height);

        //Init FX

    }






    public void loop(Engine engine, SimpleAtraction simpleAtraction){
//        System.out.println("Canvas renderer created");
//        CanvasRenderer canvasRenderer = new CanvasRenderer((CanvasFrame) gameLayoutCanvas, engine);

        this.simpleAtraction = simpleAtraction;

        if ( gameLoopAnim == null){
            createSimThread(engine);
            createGameLoop( simpleAtraction);
        }



        gameLoopAnim.start();
        // Главный цикл анимации
        System.out.println("Looping window");

    }
    public void stopLoop(){
        gameLoopAnim.stop();
    }

    public void pause() {
        gameLoopAnim.stop();
        running = false;


        //wait until Sim thread fully
//        simShutdown();
        guiLayoutPane.setVisible(true);

        appAncorPane.setMinWidth(appHBox.getWidth() - 200);
        appAncorPane.setMaxWidth(appHBox.getWidth() - 200);
        guiLayoutPane.setPrefWidth(200);

        System.out.println("GuiLayoutPane width: " + guiLayoutPane.getWidth());


//                guiLayoutPane.setVisible(true);
    }

    public void resume() {
        running = true;
        guiLayoutPane.setVisible(false);
        appAncorPane.setMinWidth(appHBox.getWidth());
        appAncorPane.setMaxWidth(appHBox.getWidth());
        guiLayoutPane.setPrefWidth(0);
//        createSimThread(engine);
        gameLoopAnim.start();
    }

    public boolean isRunning() {
        return running;
    }

    // Частота redraw
    private void createGameLoop( SimpleAtraction simpleAtraction){


        gameLoopAnim = new AnimationTimer() {
            private long lastUpdate = 0; // Время последнего обновления

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
//                double deltaTime = (now - lastUpdate) / 1_000_000_00.0; // Время в секундах между текущим и последним кадрами

                //ОБРАЩЕНИЕ К MODEL
//                engine.update(deltaTime); // Обновление с учетом реального времени между кадрами
//                lastUpdate = now; // Сохранение времени этого обновления для следующего кадра


//                canvasRenderer.render();
            }
        };
    }


    private void addCanvasListner(Engine engine){
        gameLayoutCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            System.out.println("Sun pos: " + simpleAtraction.simulationState.getSun().getPosComp().position.toString());
//            System.out.println("Mouse Clicked at: " + event.getX() + ", " + event.getY());
            System.out.println("Canvas width: " + gameLayoutCanvas.getWidth() + " height: " + gameLayoutCanvas.getHeight());
            System.out.println("Mouse Clicked at: " + (event.getX() - windowFrame.gameLayoutCanvas.getWidth()/2) + ", " + (event.getY() - windowFrame.gameLayoutCanvas.getHeight() /2));
            // Запрашиваем фокус для Canvas при клике
            gameLayoutCanvas.requestFocus();
            if(!running && simpleAtraction != null){
                // if click inside object pos + size find first one
                //without canvas.scale
//                Mover selectedMover = simpleAtraction.selectMover(new Point2D(event.getX() - windowFrame.gameLayoutCanvas.getWidth()/2, event.getY() - windowFrame.gameLayoutCanvas.getHeight() /2)) ;

                //with canvas.scale
                Mover selectedMover = simpleAtraction.selectMover(new Point2D((event.getX() - windowFrame.gameLayoutCanvas.getWidth()/2) / windowFrame.getCanvasScale(), (event.getY() - windowFrame.gameLayoutCanvas.getHeight() /2) / windowFrame.getCanvasScale())) ;
                if(selectedMover != null) {
                    System.out.println("Selected mover: " + selectedMover.getPosComp().position.toString());
//                    selectedMover.setSize(100);
                    System.out.println("Selected mover size: " + selectedMover.getSize().size);

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

                    System.out.println("New mover size: " + newMover.getSize().size);
                    //draw двигает объекты
                    simpleAtraction.redraw(gameLayoutCanvas.getGraphicsContext2D());
                }
            }
        });
    }

    /// Частота обновления симуляции
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
