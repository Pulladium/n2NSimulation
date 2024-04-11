package cz.cvut.fel.pjv.view.ecsViewGUI;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Dictionary;

public abstract class  EntityCompUI {
    //velocity

    String compName;
    Label compLabel;

    Pane layout;
    VBox CompVbox = new VBox();

    Dictionary<String, Object> CompProperties;

    public EntityCompUI(String compName){


        this.compName = compName;
        this.compLabel = new Label(compName);
    }

    // Метод для добавления кастомной логики и элементов UI для конкретного компонента
    public abstract void buildUI();


}
