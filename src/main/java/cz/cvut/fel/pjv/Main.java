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