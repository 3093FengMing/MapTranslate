package me.fengming.maptranslate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import me.fengming.maptranslate.models.nbt.tags.*;

import java.util.List;

public class Utils {
    public static void alertMessage(Alert alert, String m) {
        TextArea area = new TextArea(m);
        area.setEditable(false);
        alert.setGraphic(area);
        alert.showAndWait();
    }
    public static JsonElement tag2Element(Tag tag) {
        if (tag instanceof ArrayTag) { // ByteArray, IntArray, LongArray
            Object[] data = ((ArrayTag) tag).getData();
            JsonArray array = new JsonArray(data.length);
            for (Object o : data) {
                array.add((Number) o);
            }
            return array;
        } else if (tag instanceof NumberTag) { // ByteTag, DoubleTag, FloatTag, IntTag, LongTag, ShortTag
            return new JsonPrimitive(((NumberTag) tag).getData());
        } else if (tag instanceof StringTag) {
            return new JsonPrimitive(((StringTag) tag).getData());
        } else if (tag instanceof ListTag) {
            List<? extends Tag> data = ((ListTag) tag).getData();
            JsonArray array = new JsonArray(data.size());
            data.forEach(tag1 -> array.add(tag2Element(tag1)));
            return array;
        } else if (tag instanceof CompoundTag) {
            JsonObject object = new JsonObject();
            ((CompoundTag) tag).getData().forEach((key, value) -> object.add(key, tag2Element(value)));
            return object;
        } else return new JsonObject();
    }
    public static Tag Element2tag(JsonElement element) {
        return null;
    }
}
