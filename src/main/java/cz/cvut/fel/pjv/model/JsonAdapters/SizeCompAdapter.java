package cz.cvut.fel.pjv.model.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.CompSize;

import java.lang.reflect.Type;

public class SizeCompAdapter implements JsonSerializer<CompSize>, JsonDeserializer<CompSize> {
    @Override
    public CompSize deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompSize(jsonElement.getAsDouble());
    }

    @Override
    public JsonElement serialize(CompSize compSize, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compSize.size);
    }
}
