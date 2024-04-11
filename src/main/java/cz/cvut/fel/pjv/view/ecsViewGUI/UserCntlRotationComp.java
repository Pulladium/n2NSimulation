package cz.cvut.fel.pjv.view.ecsViewGUI;

import cz.cvut.fel.pjv.model.ecsComponents.RotationComponent;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

public class UserCntlRotationComp extends EntityCompUI{

    private final RotationComponent thisComp;
    public UserCntlRotationComp(String compName, RotationComponent thisComp, Pane layout) {
        super(compName);
        this.thisComp = thisComp;
        this.layout = layout;
    }

    @Override
    public void buildUI() {

        HBox rotPropChangeBox = new HBox();
        rotPropChangeBox.setSpacing(0.2);


        TextField doubleInput = new TextField();

// Create TextFormatter for double values
        TextFormatter<Double> doubleFormatter = new TextFormatter<>(
                new DoubleStringConverter(), // Converts between String and Double
                0.0 // Default value
        );

// Set the TextFormatter on the TextField
        doubleInput.setTextFormatter(doubleFormatter);

        Slider angleSlider = new Slider(0, 360, thisComp.angle.get() );
        angleSlider.setMajorTickUnit(90);
        angleSlider.setBlockIncrement(0.5);

        angleSlider.setViewOrder(0.0);
        compLabel.setViewOrder(0.0);
        angleSlider.setPrefWidth(100);


//        angleSlider.
        // Привязка значения слайдера к свойству угла компонента

        angleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            thisComp.angle.set(newValue.doubleValue());
            doubleInput.setText(String.valueOf(newValue.doubleValue()));
        });
        doubleInput.textProperty().addListener((observable, oldValue, newValue) -> {
            thisComp.angle.set(Double.parseDouble(newValue));
            angleSlider.setValue(Double.parseDouble(newValue));
        });




//        layout.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        rotPropChangeBox.getChildren().addAll(this.compLabel, angleSlider, doubleInput);
        CompVbox.getChildren().addAll(this.compLabel,rotPropChangeBox);

        CompVbox.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        layout.getChildren().add(CompVbox);

//        layout.getChildren().addAll(this.compLabel, rotPropChangeBox);
    }
}
