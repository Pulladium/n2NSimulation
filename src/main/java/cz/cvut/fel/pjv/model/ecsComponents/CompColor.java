package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;

public class CompColor extends Component  im{
    @Expose
    public Color color;

    public CompColor(Color color) {
        this.color = color;
    }
    public CompColor(String color) {
        this.color = Color.web(color);
    }
}
