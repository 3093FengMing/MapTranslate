package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class StringTag implements Tag {
    private String data;
    public static final StringTag EMPTY = new StringTag("");

    public static TagType<StringTag> TYPE = new TagType<StringTag>() {
        @Override
        public StringTag load(DataInput data) throws IOException {
            return StringTag.valueOf(data.readUTF());
        }
    };

    public StringTag(String data) {
        this.data = data;
    }

    public static StringTag valueOf(String data) {
        return data.isEmpty() ? EMPTY : new StringTag(data);
    }
}
