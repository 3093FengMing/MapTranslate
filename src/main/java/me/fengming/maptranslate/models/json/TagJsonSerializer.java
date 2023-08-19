package me.fengming.maptranslate.models.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.fengming.maptranslate.Utils;
import me.fengming.maptranslate.models.nbt.tags.Tag;

import java.lang.reflect.Type;

public class TagJsonSerializer implements JsonSerializer<Tag> {
    @Override
    public JsonElement serialize(Tag tag, Type type, JsonSerializationContext jsonSerializationContext) {
        return Utils.tag2Element(tag);
    }
}
