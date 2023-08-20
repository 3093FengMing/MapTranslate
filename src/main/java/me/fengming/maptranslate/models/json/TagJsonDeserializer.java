package me.fengming.maptranslate.models.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import me.fengming.maptranslate.Utils;
import me.fengming.maptranslate.models.nbt.tags.Tag;

import java.lang.reflect.Type;

public class TagJsonDeserializer implements JsonDeserializer<Tag> {
    @Override
    public Tag deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Utils.Element2tag(jsonElement);
    }
}
