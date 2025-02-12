package cz.cvut.fel.pjv.view.ecsViewGUI;

import at.fhooe.mtd.ecs.Entity;
import cz.cvut.fel.pjv.controllers.SimpleAtraction;
import cz.cvut.fel.pjv.model.GLOBALS;
import cz.cvut.fel.pjv.model.ecsComponents.CompPosition;
import cz.cvut.fel.pjv.model.utils.Point2DExt;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @deprecated
  @hidden
 **/
public class UserCtrlPositionComp extends EntityCompUI{


    private final CompPosition thisComp;
    private SimpleAtraction simpleAtraction;

    private Entity currEnt;
//    private final ImageComponent imageComponent;

//    public UserCtrlPositionComp(String compName, PositionComponent thisComp, Pane layout, ImageComponent imageComponent, ImageComponent imageComponent1) {
//
//        super(compName);
//        this.thisComp = thisComp;
//        this.imageComponent = imageComponent;
//        this.layout = layout;
//
//
//    }

    public UserCtrlPositionComp(String compName, Entity currEnt, Pane layout) {

        super(compName);
        this.currEnt = currEnt;
        this.thisComp = currEnt.getComponent(CompPosition.class);
        this.layout = layout;
        this.simpleAtraction = GLOBALS.windowFrame.getSim();
    }

    //TODO UNBIND???

    @Override
    public void buildUI() {

        HBox positionPropChangeBox1 = new HBox();
        positionPropChangeBox1.setSpacing(0.2);


        TextField doubleInputX = new TextField();
        TextField doubleInputY = new TextField();


        Label positionXLabel = new Label("X:");

//        TextFormatter<Double> doubleFormaterX = new TextFormatter<>(
//                new DoubleStringConverter(),
//                0.0
//        );


        HBox positionPropChangeBox2 = new HBox();
        positionPropChangeBox2.setSpacing(0.2);

        Label positionYLabel = new Label("Y:");
//
//        TextFormatter<Double> doubleFormaterY = new TextFormatter<>(
//                new DoubleStringConverter(),
//                0.0
//        );


//        doubleInputX.setTextFormatter(doubleFormaterX);
//        doubleInputY.setTextFormatter(doubleFormaterY);

        positionPropChangeBox1.getChildren().addAll(positionXLabel, doubleInputX);

        positionPropChangeBox2.getChildren().addAll(positionYLabel, doubleInputY);

        doubleInputX.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("X: " + newValue);
            if(newValue.isEmpty()){
                return;
            }
            if(thisComp == null){
                System.out.println("thisComp is null");
                return;
            }
//            thisComp.vector2D.xProperty().set(Double.parseDouble(newValue));

//            thisComp.position = new Point2DExt(Double.parseDouble(newValue), thisComp.position.getY());



            currEnt.getComponent(CompPosition.class).setPosition( new Point2DExt(Double.parseDouble(newValue), thisComp.position.getY()));
            thisComp.setPosition( new Point2DExt(Double.parseDouble(newValue), thisComp.position.getY()));
//            Platform.runLater(() -> thisComp.position2D.xProperty().set(Double.parseDouble(newValue)));

            simpleAtraction.redraw(GLOBALS.windowFrame.getGameLayoutCanvas().getGraphicsContext2D());


//            thisComp.position2D.xProperty().set(50.0);
//            thisComp.position2D.xProperty().set(50);
//            doubleInputX.setText(String.valueOf(thisComp.position2D.xProperty().get()));

        });

        doubleInputY.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("Y: " + newValue);
            if(newValue.isEmpty()){
                return;
            }
            if(thisComp == null){
                System.out.println("thisComp is null");
                return;
            }
//            Platform.runLater(() -> thisComp.position2D.yProperty().set(Double.parseDouble(newValue)));
//               thisComp.vector2D.yProperty().set(Double.parseDouble(newValue));
            thisComp.setPosition( new Point2DExt(thisComp.position.getX(), Double.parseDouble(newValue)));
//            thisComp.position2D.yProperty().set(50.0);
//            doubleInputY.setText(String.valueOf(thisComp.position2D.yProperty().get()));
            simpleAtraction.redraw(GLOBALS.windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
        });

        CompVbox.getChildren().addAll(positionPropChangeBox1, positionPropChangeBox2);

        CompVbox.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));


        layout.getChildren().add(CompVbox);



    }
}
