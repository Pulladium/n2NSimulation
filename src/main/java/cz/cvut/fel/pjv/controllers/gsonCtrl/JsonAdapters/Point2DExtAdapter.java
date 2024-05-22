package cz.cvut.fel.pjv.controllers.gsonCtrl.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.utils.Point2DExt;

import java.lang.reflect.Type;


/**
 * Custom GSON adapter for serializing and deserializing {@link Point2DExt} objects.
 * <p>
 * Implements {@link JsonSerializer} and {@link JsonDeserializer} to handle the conversion between {@link Point2DExt} and JSON.
 */
public class Point2DExtAdapter  implements JsonSerializer<Point2DExt>, JsonDeserializer<Point2DExt> {
    /**
     * Serializes a {@link Point2DExt} object into a JSON element.
     * <p>
     * Converts the {@link Point2DExt} into a JSON object with "x" and "y" properties.
     *
     * @param src the {@link Point2DExt} object to serialize.
     * @param typeOfSrc the type of the object to serialize.
     * @param context the context of the serialization process.
     * @return the serialized JSON element.
     */
    @Override
    public JsonElement serialize(Point2DExt src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.getX());
        jsonObject.addProperty("y", src.getY());
        return jsonObject;
    }
    /**
     * Deserializes a JSON element into a {@link Point2DExt} object.
     * <p>
     * Converts the JSON object with "x" and "y" properties into a {@link Point2DExt}.
     *
     * @param json the JSON element to deserialize.
     * @param typeOfT the type of the object to deserialize.
     * @param context the context of the deserialization process.
     * @return the deserialized {@link Point2DExt} object.
     * @throws JsonParseException if the JSON element cannot be parsed.
     */
    @Override
    public Point2DExt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        return new Point2DExt(x, y);
    }
}
