package cz.cvut.fel.pjv.view.frames;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.controller.canvasRender.CanvasRenderer;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class WindowFrame {
    private int width, height;
    private String title;

    Canvas gameLayoutCanvas;
    AnchorPane appAncorPane;
    Scene currentScene;


    private static AnimationTimer gameLoopAnim;

    private static WindowFrame instance;

    private WindowFrame() {
        this.width = 1200;
        this.height = 1000;
        this.title = "EngineFX";
    }

    private Parent createContent() {



        gameLayoutCanvas = new CanvasFrame(width, height);

//        gameLayoutPane.autosize();
        GraphicsContext gc = gameLayoutCanvas.getGraphicsContext2D();

//        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameLayoutCanvas.getWidth(), gameLayoutCanvas.getHeight()); // Заливаем весь Canvas черным цветом


//        gameLayoutPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        gameLayoutCanvas.setViewOrder(1.0);

        appAncorPane = new AnchorPane();
        appAncorPane.autosize();
        appAncorPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, null)));
        appAncorPane.getChildren().add(gameLayoutCanvas);





//        //doesnt work with canvas
        AnchorPane.setTopAnchor(gameLayoutCanvas, 0.0);
        AnchorPane.setLeftAnchor(gameLayoutCanvas, 0.0);
        AnchorPane.setRightAnchor(gameLayoutCanvas, 0.0);
        AnchorPane.setBottomAnchor(gameLayoutCanvas, 0.0);


        // Add width and height listeners to the anchor pane
        appAncorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                gameLayoutCanvas.setWidth(newWidth.doubleValue());
                // Optionally, redraw your canvas here if needed
            }
        });

        appAncorPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldHeight, Number newHeight) {
                gameLayoutCanvas.setHeight(newHeight.doubleValue());
                // Optionaly, redraw canvas here
            }
        });


        return appAncorPane;
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

    public void loop(Engine engine, SimpleAtraction simpleAtraction){
//        System.out.println("Canvas renderer created");
//        CanvasRenderer canvasRenderer = new CanvasRenderer((CanvasFrame) gameLayoutCanvas, engine);


        if ( gameLoopAnim == null){
            createGameLoop(engine, simpleAtraction);
        }



        gameLoopAnim.start();
        // Главный цикл анимации
        System.out.println("Looping window");

    }
    public void stopLoop(){
        gameLoopAnim.stop();
    }

    private void createGameLoop(Engine engine, SimpleAtraction simpleAtraction){
        gameLoopAnim = new AnimationTimer() {
            private long lastUpdate = 0; // Время последнего обновления

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_00.0; // Время в секундах между текущим и последним кадрами

                //ОБРАЩЕНИЕ К MODEL
//                engine.update(deltaTime); // Обновление с учетом реального времени между кадрами
                lastUpdate = now; // Сохранение времени этого обновления для следующего кадра

                simpleAtraction.draw((GraphicsContext) gameLayoutCanvas.getGraphicsContext2D());

//                canvasRenderer.render();
            }
        };
    }


}
