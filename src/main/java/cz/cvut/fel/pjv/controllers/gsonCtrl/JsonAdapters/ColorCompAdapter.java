package cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;
/**
 * Custom GSON adapter for serializing and deserializing {@link CompColor} objects.
 * <p>
 * Implements {@link JsonSerializer} and {@link JsonDeserializer} to handle the conversion between {@link CompColor} and JSON.
 */
public class ColorCompAdapter implements JsonSerializer<CompColor>, JsonDeserializer<CompColor> {
    /**
     * Deserializes a JSON element into a {@link CompColor} object.
     * <p>
     * Converts the JSON string representation of a color into a {@link CompColor} using {@link Color#web(String)}.
     *
     * @param jsonElement the JSON element to deserialize.
     * @param type the type of the object to deserialize.
     * @param jsonDeserializationContext the context of the deserialization process.
     * @return the deserialized {@link CompColor} object.
     * @throws JsonParseException if the JSON element cannot be parsed.
     */
    @Override
    public CompColor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CompColor(Color.web(jsonElement.getAsString()));
    }
    /**
     * Serializes a {@link CompColor} object into a JSON element.
     * <p>
     * Converts the {@link CompColor} into its string representation using {@link Color#toString()}.
     *
     * @param compColor the {@link CompColor} object to serialize.
     * @param type the type of the object to serialize.
     * @param jsonSerializationContext the context of the serialization process.
     * @return the serialized JSON element.
     */
    @Override
    public JsonElement serialize(CompColor compColor, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(compColor.color.toString());
    }
}
