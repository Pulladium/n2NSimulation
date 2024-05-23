package cz.cvut.fel.pjv;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.controllers.SimpleAtraction;
//import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.log;



public class Main extends Application{

    private Engine engine = new Engine();

    private final WindowFrame windowFrame = WindowFrame.get();

//    private final UserControl ui = UserControl.get();

    @Override
    //Nodes(obj, shapes)
    public void start(Stage primaryStage) throws Exception{
        // Создание окна
        windowFrame.run(primaryStage);




        //start simulationNplanets


        SimpleAtraction simpleAtraction = new SimpleAtraction( windowFrame, engine);


        windowFrame.loop(engine,simpleAtraction);


        windowFrame.getCurrentScene().setOnKeyPressed(keyEvent ->
        {
            log("Key pressed: " + keyEvent.getCode(), Level.INFO);

            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.P) {
                if (windowFrame.isRunning())
                    windowFrame.pause();
                else
                    windowFrame.resume();
            }
            else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                if(!windowFrame.isRunning())
                    simpleAtraction.saveSimState();
            }
            else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.L) {
                if(!windowFrame.isRunning())
                    simpleAtraction.loadSimState();
            }
            else if ( keyEvent.isControlDown()) {
                if ( keyEvent.getCode() == KeyCode.PLUS ||  keyEvent.getCode() == KeyCode.EQUALS) {
                    windowFrame.setCanvasScale(windowFrame.getCanvasScale()* 1.1);
                    simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                } else if ( keyEvent.getCode() == KeyCode.MINUS) {
//                    scale /= 1.1;
                    windowFrame.setCanvasScale(windowFrame.getCanvasScale() / 1.1);
                    simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                }
            }
            else {
                switch (keyEvent.getCode()) {
                    case W:
                        windowFrame.offsetY -= 10;
                        simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case S:
                        windowFrame.offsetY += 10;
                        simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case A:
                        windowFrame.offsetX -= 10;
                        simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case D:
                        windowFrame.offsetX += 10;
                        simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;

                }

            }
        });



    }

    @Override
    public void stop() throws Exception {

        super.stop();

        windowFrame.simShutdown();
        windowFrame.stopLoop();
    }




    public static void main(String[] args) {
        launch(args);
    }
}