package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;
/**
 * Represents a color {@link Component} in the ECS (Entity Component System) framework.
 * <p>
 * Contains a color property.
 */
public class CompColor extends Component   {
    @Expose
    public Color color;

    public CompColor(Color color) {
        this.color = color;
    }
    public CompColor(String color) {
        this.color = Color.web(color);
    }


}
