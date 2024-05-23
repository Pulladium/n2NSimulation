package cz.cvut.fel.pjv;

import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.controllers.KeyBoardHandl;
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

        KeyBoardHandl keyBoardHandl = new KeyBoardHandl();
        keyBoardHandl.AddHandler(simpleAtraction);


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