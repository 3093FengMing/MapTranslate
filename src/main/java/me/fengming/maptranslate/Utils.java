package me.fengming.maptranslate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import me.fengming.maptranslate.models.nbt.tags.*;

import java.util.List;

public class Utils {
    public static double getMidX(Region region, Node node) {
        return (region.getWidth() - node.getLayoutBounds().getWidth()) / 2;
    }
    public static double getMidY(Region region, Node node) {
        return (region.getHeight() - node.getLayoutBounds().getHeight()) / 2;
    }
    public static JsonElement tag2Element(Tag tag) {
        if (tag instanceof ArrayTag at) { // ByteArray, IntArray, LongArray
            Object[] data = at.getData();
            JsonArray array = new JsonArray(data.length);
            for (Object o : data) {
                array.add((Number) o);
            }
            return array;
        } else if (tag instanceof NumberTag nt) { // ByteTag, DoubleTag, FloatTag, IntTag, LongTag, ShortTag
            return new JsonPrimitive(nt.getData());
        } else if (tag instanceof StringTag st) {
            return new JsonPrimitive(st.getData());
        } else if (tag instanceof ListTag lt) {
            List<Tag> data = lt.getData();
            JsonArray array = new JsonArray(data.size());
            data.forEach(tag1 -> array.add(tag2Element(tag1)));
            return array;
        } else if (tag instanceof CompoundTag ct) {
            JsonObject object = new JsonObject();
            ct.getData().forEach((key, value) -> object.add(key, tag2Element(value)));
            return object;
        } else return new JsonObject();
    }
}
