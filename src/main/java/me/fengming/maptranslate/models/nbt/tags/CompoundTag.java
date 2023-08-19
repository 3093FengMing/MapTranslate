package me.fengming.maptranslate.models.nbt.tags;

import me.fengming.maptranslate.models.nbt.TagTypes;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompoundTag implements Tag {
    
    private Map<String, Tag> data;

    public static TagType<CompoundTag> TYPE = new TagType<CompoundTag>() {
        @Override
        public CompoundTag load(DataInput data) throws IOException {
            byte type;
            Map<String, Tag> map = new HashMap<>();

            while ((type = CompoundTag.readNamedTagType(data)) != 0) {
                String name = CompoundTag.readNamedTagName(data);
                Tag tag = CompoundTag.readNamedTagData(TagTypes.getType(type), data);
                map.put(name, tag);
            }

            return new CompoundTag(map);
        }
    };
    public Tag get(String name) {
        return data.getOrDefault(name, EndTag.INSTANCE);
    }

    public Map<String, Tag> getData() {
        return data;
    }

    public void setData(Map<String, Tag> data) {
        this.data = data;
    }

    public Tag put(String name, Tag tag) {
        return data.put(name, tag);
    }
    protected CompoundTag(Map<String, Tag> data) {
        this.data = data;
    }
    public CompoundTag() {
        this.data = new HashMap<>();
    }
    public static byte readNamedTagType(DataInput data) throws IOException {
        return data.readByte();
    }
    public static String readNamedTagName(DataInput data) throws IOException {
        return data.readUTF();
    }

    public static Tag readNamedTagData(TagType<?> pType, DataInput pInput) {
        try {
            return pType.load(pInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EndTag.INSTANCE;
    }

    @Override
    public String toString() {
        return "CompoundTag{" +
                "data=" + data.toString() +
                '}';
    }
}
