package cz.cvut.fel.pjv.view.ecsViewGUI;

import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UiNewSim {

    private Pane parentPane;
    private VBox sliderPane;

    private WindowFrame windowFrame;

    private static UiNewSim instance;
    private UiNewSim() {
        sliderPane = new VBox();

//        sliderPane.setAlignment(javafx.geometry.Pos.CENTER);

        sliderPane.setViewOrder(0.1);

        sliderPane.setStyle("-fx-background-color: #ffff; -fx-border-color: #16be78; -fx-border-width: 1px;");

    }

    public static UiNewSim get(){
        if(UiNewSim.instance == null){
            UiNewSim.instance = new UiNewSim();
        }
        return UiNewSim.instance;
    }

    public void setWindow(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        this.parentPane = windowFrame.getGuiLayoutPane();
        this.parentPane.getChildren().add(sliderPane);

//
//        controlWidth = windowFrame.getWidth(;
//        controlHeight = windowFrame.getHeight();
//        sliderPane.setMaxWidth(controlWidth);
//        sliderPane.setMinWidth(controlWidth);


        sliderPane.setPrefHeight(parentPane.getPrefHeight());
        sliderPane.setPrefWidth(parentPane.getPrefWidth());

        showSimStartProperties();
//        windowFrame.stopLoop();
//        showCompControl

    }


    public void showSunProp() {
        // Создание контейнера для свойств солнца
        VBox sunPropsContainer = new VBox(10); // Пространство между элементами
        sunPropsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #ddd; -fx-border-width: 1px;");

        // Позиция
        Label positionLabel = new Label("Position: ");
        TextField posXTextField = new TextField(); // Координата X
        TextField posYTextField = new TextField(); // Координата Y
        posXTextField.setPromptText("Enter X coordinate");
        posYTextField.setPromptText("Enter Y coordinate");

        // Скорость
        Label velocityLabel = new Label("Velocity: ");
        TextField veloXTextField = new TextField(); // Скорость X
        TextField veloYTextField = new TextField(); // Скорость Y
        veloXTextField.setPromptText("Enter X velocity");
        veloYTextField.setPromptText("Enter Y velocity");

        // Масса
        Label massLabel = new Label("Mass: ");
        TextField massTextField = new TextField();
        massTextField.setPromptText("Enter mass value");

        // Размер
        Label sizeLabel = new Label("Size: ");
        TextField sizeTextField = new TextField();
        sizeTextField.setPromptText("Enter size value");

        // Добавление всех компонентов в контейнер
        sunPropsContainer.getChildren().addAll(positionLabel, posXTextField, posYTextField,
                velocityLabel, veloXTextField, veloYTextField,
                massLabel, massTextField,
                sizeLabel, sizeTextField);

        // Добавление контейнера в основной пейс
        sliderPane.getChildren().add(sunPropsContainer);

        System.out.println("Sun properties input fields have been displayed.");
    }
    public void showSimStartProperties(){
        // Create text field for entering the number of movers
        TextField moverCountTextField = new TextField("");
        moverCountTextField.setPromptText("Mover Count");

        // Create checkbox for determining if the sun is present
        CheckBox hasSunCheckBox = new CheckBox("Has Sun");
        hasSunCheckBox.setSelected(false); // Assume the sun is present by default

        Button startSimButton = new Button("Start Simulation");
        // Add text field and checkbox to the panel
        sliderPane.getChildren().addAll(moverCountTextField, hasSunCheckBox, startSimButton);

        // Set up a listener for the checkbox
        hasSunCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Sun checkbox changed to " + newValue);
            if(newValue) { // Update the variable when the checkbox state changes
                // Optionally, call showSunProp() here to immediately reflect the change
                showSunProp();
            }
        });



        startSimButton.setOnAction(event -> {
            System.out.println("Start simulation button clicked");
            // Get the number of movers from the text field
            try {
                int moverCount = Integer.parseInt(moverCountTextField.getText());
                if(moverCount < 0) {
                    System.out.println("Invalid mover count: " + moverCount);
                    return;
                }
                windowFrame.getSim().simulationState = new SimulationState();
                if(hasSunCheckBox.isSelected()){
                    windowFrame.getSim().simulationState.createDefaultState();
                } else {

                    windowFrame.getSim().simulationState.createNwithoutAtractor(moverCount);
                }
                windowFrame.getSim().redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());

                System.out.println("Mover count: " + moverCount);
            }catch (NumberFormatException e){
                System.out.println("Invalid mover count: " + moverCountTextField.getText());
                return;
            }


        });


    }
}
