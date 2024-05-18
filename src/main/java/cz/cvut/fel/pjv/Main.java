package cz.cvut.fel.pjv;

import at.fhooe.mtd.ecs.Engine;

//import cz.cvut.fel.pjv.controller.Simulation2planet;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
//import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Objects;

//podskazky


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application{

    private Engine engine = new Engine();

//    static
    private final WindowFrame windowFrame = WindowFrame.get();

//    static
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
            System.out.println("Key pressed: " + keyEvent.getCode());
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
//        //TUTA PRACUJE
//        pos.vector2D.xProperty().set(200);
//        pos.vector2D.yProperty().set(200);
////
//        ImageComponent img = earth.getComponent(ImageComponent.class);
//        img.bindPosition(pos);
////
////
////        //где андинд то
//
//        ui.setWindow(window);
//        ui.showCompControl(earth);




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