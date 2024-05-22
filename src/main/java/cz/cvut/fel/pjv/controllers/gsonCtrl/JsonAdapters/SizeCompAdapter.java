package cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.CompSize;

import java.lang.reflect.Type;
/**
 * Custom GSON adapter for serializing and deserializing {@link CompSize} objects.
 * <p>
 * Implements {@link JsonSerializer} and {@link JsonDeserializer} to handle the conversion between {@link CompSize} and JSON.
 */
public class SizeCompAdapter implements JsonSerializer<CompSize>, JsonDeserializer<CompSize> {
    /**
     * Deserializes a JSON element into a {@link CompSize} object.
     * <p>
     * Converts the JSON representation of size into a {@link CompSize}.
     *
     * @param jsonElement the JSON element to deserialize.
     * @param type the type of the object to deserialize.
     * @param jsonDeserializationContext the context of the deserialization process.
     * @return the deserialized {@link CompSize} object.
     * @throws JsonParseException if the JSON element cannot be parsed.
     */
    @Override
    public CompSize deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompSize(jsonElement.getAsDouble());
    }
    /**
     * Serializes a {@link CompSize} object into a JSON element.
     * <p>
     * Converts the {@link CompSize} into its JSON representation.
     *
     * @param compSize the {@link CompSize} object to serialize.
     * @param type the type of the object to serialize.
     * @param jsonSerializationContext the context of the serialization process.
     * @return the serialized JSON element.
     */
    @Override
    public JsonElement serialize(CompSize compSize, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compSize.size);
    }
}
