package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class IntTag implements Tag {

    private int data;

    public static TagType<IntTag> TYPE = new TagType<IntTag>() {
        @Override
        public IntTag load(DataInput data) throws IOException {
            return IntTag.valueOf(data.readInt());
        }
    };

    public IntTag(int data) {
        this.data = data;
    }

    public static IntTag valueOf(int data) {
        return data >= -128 && data <= 1024 ? IntTag.Cache.cache[data - -128] : new IntTag(data);
    }

    static class Cache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final IntTag[] cache = new IntTag[1153];

        private Cache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new IntTag(-128 + i);
            }

        }
    }
}
