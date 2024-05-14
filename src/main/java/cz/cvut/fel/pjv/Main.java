package cz.cvut.fel.pjv;

import at.fhooe.mtd.ecs.Engine;

//import cz.cvut.fel.pjv.controller.Simulation2planet;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
//import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.application.Application;
import javafx.stage.Stage;

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

//        PositionComponent pos = earth.getComponent(PositionComponent.class);

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




    public static void main(String[] args) {
        launch(args);
    }
}