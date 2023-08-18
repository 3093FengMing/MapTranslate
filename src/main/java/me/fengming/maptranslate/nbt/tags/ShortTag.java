package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class ShortTag implements Tag {
    private final short data;
    public static TagType<ShortTag> TYPE = new TagType<ShortTag>() {
        @Override
        public ShortTag load(DataInput data) throws IOException {
            return ShortTag.valueOf(data.readShort());
        }
    };

    public ShortTag(short data) {
        this.data = data;
    }

    public static ShortTag valueOf(short data) {
        return data >= -128 && data <= 1024 ? ShortTag.Cache.cache[data - -128] : new ShortTag(data);
    }

    static class Cache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final ShortTag[] cache = new ShortTag[1153];

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new ShortTag((short)(-128 + i));
            }

        }
    }
}
