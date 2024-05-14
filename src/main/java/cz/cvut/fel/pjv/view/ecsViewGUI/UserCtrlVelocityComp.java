//package cz.cvut.fel.pjv.view.ecsViewGUI;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextFormatter;
//import javafx.scene.layout.*;
//import javafx.util.converter.DoubleStringConverter;
//
//
//
//public class UserCtrlVelocityComp extends EntityCompUI{
//    //velocity
//    private final VelocityComponent thisComp;
//
//
//
//    public UserCtrlVelocityComp(String compName, VelocityComponent thisComp, Pane layout) {
//        super(compName);
//        this.thisComp = thisComp;
//        this.layout = layout;
//    }
//
//    @Override
//    public void buildUI() {
//
//        HBox velocityPropChangeBox1 = new HBox();
//        velocityPropChangeBox1.setSpacing(0.2);
//
//        Label velocityXLabel = new Label("X:");
//        TextField doubleInputX = new TextField();
//        TextField doubleInputY = new TextField();
//
//        TextFormatter<Double> doubleFormatterX = new TextFormatter<>(
//                new DoubleStringConverter(), // Converts between String and Double
//                0.0 // Default value
//        );
//        doubleInputX.setTextFormatter(doubleFormatterX);
//
//        velocityPropChangeBox1.getChildren().addAll(velocityXLabel, doubleInputX);
//
//        HBox velocityPropChangeBox2 = new HBox();
//        velocityPropChangeBox2.setSpacing(0.2);
//
//        Label velocityYLabel = new Label("Y:");
//        TextFormatter<Double> doubleFormatterY = new TextFormatter<>(
//                new DoubleStringConverter(), // Converts between String and Double
//                0.0 // Default value
//        );
//        doubleInputY.setTextFormatter(doubleFormatterY);
//
//        velocityPropChangeBox2.getChildren().addAll(velocityYLabel, doubleInputY);
//
//        doubleInputX.textProperty().addListener((observable, oldValue, newValue) -> {
//            thisComp.getVelocity().xProperty().set(Double.parseDouble(newValue));
//        });
//        doubleInputY.textProperty().addListener((observable, oldValue, newValue) -> {
//            thisComp.getVelocity().yProperty().set(Double.parseDouble(newValue));
//        });
//
//
//        CompVbox.getChildren().addAll(velocityPropChangeBox1, velocityPropChangeBox2);
//
//        CompVbox.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTBLUE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
//
//        layout.getChildren().add(CompVbox);
//
//    }
//}
