package cz.cvut.fel.pjv.model.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorCompAdapter implements JsonSerializer<CompColor>, JsonDeserializer<CompColor> {

    @Override
    public CompColor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompColor(Color.web(jsonElement.getAsString()));
    }

    @Override
    public JsonElement serialize(CompColor compColor, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compColor.color.toString());
    }
}
