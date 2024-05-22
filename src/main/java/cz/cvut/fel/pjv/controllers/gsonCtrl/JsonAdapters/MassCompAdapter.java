package cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;

import java.lang.reflect.Type;
/**
 * Custom GSON adapter for serializing and deserializing {@link MassComponent} objects.
 * <p>
 * Implements {@link JsonSerializer} and {@link JsonDeserializer} to handle the conversion between {@link MassComponent} and JSON.
 */
public class MassCompAdapter implements JsonSerializer<MassComponent>, JsonDeserializer<MassComponent> {
    /**
     * Deserializes a JSON element into a {@link MassComponent} object.
     * <p>
     * Converts the JSON representation of mass into a {@link MassComponent}.
     *
     * @param jsonElement the JSON element to deserialize.
     * @param type the type of the object to deserialize.
     * @param jsonDeserializationContext the context of the deserialization process.
     * @return the deserialized {@link MassComponent} object.
     * @throws JsonParseException if the JSON element cannot be parsed.
     */
    @Override
    public MassComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new MassComponent(jsonElement.getAsDouble());
    }
    /**
     * Serializes a {@link MassComponent} object into a JSON element.
     * <p>
     * Converts the {@link MassComponent} into its JSON representation.
     *
     * @param massComponent the {@link MassComponent} object to serialize.
     * @param type the type of the object to serialize.
     * @param jsonSerializationContext the context of the serialization process.
     * @return the serialized JSON element.
     */
    @Override
    public JsonElement serialize(MassComponent massComponent, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(massComponent.mass.get());
    }
}
