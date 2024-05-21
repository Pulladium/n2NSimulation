package cz.cvut.fel.pjv.view.ecsViewGUI;

import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.model.ecsSystems.N2mAtraction;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.log;

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


    public VBox showSunProp() {
        // Создание контейнера для свойств солнца
        VBox sunPropsContainer = new VBox(10); // Пространство между элементами
        sunPropsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #ddd; -fx-border-width: 1px;");

        // Позиция
        Label positionLabel = new Label("Position: ");
        TextField posXTextField = new TextField("0"); // Координата X
        TextField posYTextField = new TextField("0"); // Координата Y
        posXTextField.setPromptText("Enter X coordinate");
        posYTextField.setPromptText("Enter Y coordinate");

        // Скорость
        Label velocityLabel = new Label("Velocity: ");
        TextField veloXTextField = new TextField("0"); // Скорость X
        TextField veloYTextField = new TextField("0"); // Скорость Y
        veloXTextField.setPromptText("Enter X velocity");
        veloYTextField.setPromptText("Enter Y velocity");

        // Масса
        Label massLabel = new Label("Mass: ");
        TextField massTextField = new TextField("100");
        massTextField.setPromptText("Enter mass value");

        // Размер
        Label sizeLabel = new Label("Size: ");
        TextField sizeTextField = new TextField("100");
        sizeTextField.setPromptText("Enter size value");

        // Добавление всех компонентов в контейнер
        sunPropsContainer.getChildren().addAll(positionLabel, posXTextField, posYTextField,
                velocityLabel, veloXTextField, veloYTextField,
                massLabel, massTextField,
                sizeLabel, sizeTextField);

        // Добавление контейнера в основной пейс
        sliderPane.getChildren().add(sunPropsContainer);

//        System.out.println("Sun properties input fields have been displayed.");
        log("Sun properties input fields have been displayed.", Level.INFO);
        return sunPropsContainer;
    }

    private Mover createSunAndValidateProp(VBox sunPropsContainer){
        if(sunPropsContainer == null){
            System.out.println("Sun properties container is null");
            return null;
        }
        TextField posXTextField = (TextField) sunPropsContainer.getChildren().get(1);
        TextField posYTextField = (TextField) sunPropsContainer.getChildren().get(2);
        TextField veloXTextField = (TextField) sunPropsContainer.getChildren().get(4);
        TextField veloYTextField = (TextField) sunPropsContainer.getChildren().get(5);
        TextField massTextField = (TextField) sunPropsContainer.getChildren().get(7);
        TextField sizeTextField = (TextField) sunPropsContainer.getChildren().get(9);

        try {
            double posX = Double.parseDouble(posXTextField.getText());
            double posY = Double.parseDouble(posYTextField.getText());
            double veloX = Double.parseDouble(veloXTextField.getText());
            double veloY = Double.parseDouble(veloYTextField.getText());
            double mass = Double.parseDouble(massTextField.getText());
            double size = Double.parseDouble(sizeTextField.getText());
            if(mass <= 0 || size <= 0){
                System.out.println("Invalid mass or size value");
                return null;
            }
            System.out.println("Sun properties have been validated");
            Color color = Color.YELLOW;
            return new Mover(posX, posY, veloX, veloY, mass, size, color);
        } catch (NumberFormatException e){
            System.out.println("Invalid number format in sun properties");
            return null;
        }
    }
    private VBox sunPropContainer = null;
    public void showSimStartProperties(){
        // Create text field for entering the number of movers
        TextField moverCountTextField = new TextField("1");
        moverCountTextField.setPromptText("Mover Count");

        // Create checkbox for determining if the sun is present
        CheckBox hasSunCheckBox = new CheckBox("Has Sun");
        hasSunCheckBox.setSelected(false); // Assume the sun is present by default

        Button startSimButton = new Button("Start Simulation");
        // Add text field and checkbox to the panel
        sliderPane.getChildren().addAll(moverCountTextField, hasSunCheckBox, startSimButton);

        // Set up a listener for the checkbox
        hasSunCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            log("Sun checkbox changed to " + newValue, Level.INFO);
//            System.out.println("Sun checkbox changed to " + newValue);
            if(newValue) { // Update the variable when the checkbox state changes
                // Optionally, call showSunProp() here to immediately reflect the change
                sunPropContainer = showSunProp();
            }
        });





        startSimButton.setOnAction(event -> {
//            System.out.println("Start simulation button clicked");
            log("Start simulation button clicked", Level.INFO);
            // Get the number of movers from the text field
            try {
                int moverCount = Integer.parseInt(moverCountTextField.getText());
                if(moverCount < 0) {
//                    System.out.println("Invalid mover count: " + moverCount);
                    log("Invalid mover count: " + moverCount, Level.WARNING);
                    return;
                }
                windowFrame.getSim().simulationState = new SimulationState();
                if(hasSunCheckBox.isSelected()){
                    if(sunPropContainer == null){
//                        System.out.println("Sun properties container is null");
                        log("Sun properties container is null", Level.WARNING);
                        return;
                    }
                    Mover sun = createSunAndValidateProp(sunPropContainer);
                    if(sun == null){
//                        System.out.println("Sun is null");
                        log("Sun is null", Level.WARNING);
                        return;
                    }
                    windowFrame.getSim().simulationState.createNwithAtractor(moverCount, sun);
                }
                else {
                    windowFrame.getSim().simulationState.createNwithoutAtractor(moverCount);
                }
                windowFrame.getEngine().removeAll();//only entities
                for (int i = 0; i < windowFrame.getEngine().getNumOfSystems(); i++) {
                    windowFrame.getEngine().removeSystem(windowFrame.getEngine().getSystem(0));
                }
                N2mAtraction n2mAtraction = new N2mAtraction(windowFrame.getSim().simulationState.getMovers(), windowFrame.getSim().simulationState.getSun());
                windowFrame.getEngine().addSystem(n2mAtraction);
                windowFrame.getSim().redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());

//                System.out.println("Mover count: " + moverCount);
                log("Mover count: " + moverCount, Level.INFO);
            }catch (NumberFormatException e){
                log("Invalid mover count: " + moverCountTextField.getText(), Level.WARNING);
//                System.out.println("Invalid mover count: " + moverCountTextField.getText());
                return;
            }


        });


    }
}
