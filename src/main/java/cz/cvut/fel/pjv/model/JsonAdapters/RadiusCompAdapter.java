package cz.cvut.fel.pjv.model.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.CompRadius;

import java.lang.reflect.Type;

public class RadiusCompAdapter implements JsonSerializer<CompRadius>, JsonDeserializer<CompRadius> {
    @Override
    public CompRadius deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompRadius(jsonElement.getAsDouble());
    }

    @Override
    public JsonElement serialize(CompRadius compRadius, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compRadius.radius.get());
    }
}
