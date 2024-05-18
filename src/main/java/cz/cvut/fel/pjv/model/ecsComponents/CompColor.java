package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class CompColor extends Component  implements JsonSerializer<CompColor>, JsonDeserializer<CompColor> {
    @Expose
    public Color color;

    public CompColor(Color color) {
        this.color = color;
    }
    public CompColor(String color) {
        this.color = Color.web(color);
    }

    @Override
    public CompColor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompColor(Color.web(jsonElement.getAsString()));
    }

    @Override
    public JsonElement serialize(CompColor compColor, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compColor.color.toString());
    }
}
