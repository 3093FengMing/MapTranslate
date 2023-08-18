package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;

public class EndTag implements Tag {
    public static TagType<EndTag> TYPE = new TagType<EndTag>() {
        @Override
        public EndTag load(DataInput data) {
            return EndTag.INSTANCE;
        }
    };
    public static EndTag INSTANCE = new EndTag();
}
