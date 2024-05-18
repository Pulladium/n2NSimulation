package cz.cvut.fel.pjv.model.JsonAdapters;

import com.google.gson.*;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt;

import java.lang.reflect.Type;

public class Point2DExtAdapter  implements JsonSerializer<Point2DExt>, JsonDeserializer<Point2DExt> {
    @Override
    public JsonElement serialize(Point2DExt src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.getX());
        jsonObject.addProperty("y", src.getY());
        return jsonObject;
    }

    @Override
    public Point2DExt deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        return new Point2DExt(x, y);
    }
}
