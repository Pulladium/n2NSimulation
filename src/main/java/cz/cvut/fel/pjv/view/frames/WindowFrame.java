package cz.cvut.fel.pjv.view.frames;

import at.fhooe.mtd.ecs.Engine;
//import cz.cvut.fel.pjv.controller.canvasRender.CanvasRenderer;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    public int offsetX, offsetY = 500;


    //currentScene --> appAncorPane
    //appAncorPane --> gameLayoutCanvas
    //appAncorPane --> GuiLayoutPane
    Scene currentScene;

    HBox appHBox;

    AnchorPane appAncorPane;
    Canvas gameLayoutCanvas;
//    Pane guiLayoutPane;
    private double canvasScale = 0.5;




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

//    private Parent createGUI(){
//        guiLayoutPane = new Pane();
//
//        guiLayoutPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));
//        guiLayoutPane.setViewOrder(0.0);
//
//        AnchorPane.setTopAnchor(guiLayoutPane, 0.0);
////        AnchorPane.setLeftAnchor(guiLayoutPane, 0.0);
//        AnchorPane.setBottomAnchor(guiLayoutPane, 0.0);
//        AnchorPane.setRightAnchor(guiLayoutPane, 0.0);
////        guiLayoutPane.setPrefWidth(100);
//        guiLayoutPane.setMaxWidth(100);
//
//        guiLayoutPane.setVisible(false);
//        appAncorPane.getChildren().add(guiLayoutPane);
//        return null;
//    }

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

        appAncorPane = new AnchorPane();
        appAncorPane.autosize();
        appAncorPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, null)));
        appAncorPane.getChildren().add(gameLayoutCanvas);
//        createGUI();





//        //doesnt work with canvas
//        AnchorPane.setTopAnchor(gameLayoutCanvas, 0.0);
//        AnchorPane.setLeftAnchor(gameLayoutCanvas, 0.0);
//        AnchorPane.setRightAnchor(gameLayoutCanvas, 0.0);
//        AnchorPane.setBottomAnchor(gameLayoutCanvas, 0.0);


        // Add width and height listeners to the anchor pane
        appAncorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                gameLayoutCanvas.setWidth(newWidth.doubleValue());
//                if(simpleAtraction != null){
//                    simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());
//                }

            }
        });

        appAncorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
                gameLayoutCanvas.setHeight(newHeight.doubleValue());
//                if(simpleAtraction != null){
//                    simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());
//                }
            }
        });


        appHBox = new HBox();
        appHBox.autosize();
        appHBox.getChildren().add(appAncorPane);

        // Add width and height listeners to the Hbox
        appHBox.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {

//                appAncorPane.setPrefWidth(newWidth.doubleValue());
                appAncorPane.setMinWidth(newWidth.doubleValue());
                appAncorPane.setMaxWidth(newWidth.doubleValue());
//                if (simpleAtraction != null) {
//                    simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());
//                }
            }
        });

        appHBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
//                appAncorPane.setPrefHeight(newHeight.doubleValue());
                appAncorPane.setMinHeight(newHeight.doubleValue());
                appAncorPane.setMaxHeight(newHeight.doubleValue());
//                if(simpleAtraction != null){
//                    simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());
//                }
            }
        });


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
//        guiLayoutPane.setVisible(true);
    }

    public void resume() {
        running = true;
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



    /// Частота обновления симуляции
    private void createSimThread(Engine engine){

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

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public AnchorPane getAppPane() {
        return appAncorPane;
    }


    //setters
    public void setCanvasScale(double canvasScale) {
        this.canvasScale = canvasScale;
    }
}
