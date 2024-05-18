package cz.cvut.fel.pjv.model.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;

import java.lang.reflect.Type;

public class MassCompAdapter implements JsonSerializer<MassComponent>, JsonDeserializer<MassComponent> {
    @Override
    public MassComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new MassComponent(jsonElement.getAsDouble());
    }

    @Override
    public JsonElement serialize(MassComponent massComponent, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(massComponent.mass.get());
    }
}
