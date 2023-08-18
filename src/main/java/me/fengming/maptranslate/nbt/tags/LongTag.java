package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class LongTag implements Tag {
    private long data;

    public static TagType<LongTag> TYPE = new TagType<LongTag>() {
        @Override
        public LongTag load(DataInput data) throws IOException {
            return LongTag.valueOf(data.readLong());
        }
    };

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public static LongTag valueOf(long data) {
        return data >= -128L && data <= 1024L ? LongTag.Cache.cache[(int)data - -128] : new LongTag(data);
    }

    public LongTag(long data) {
        this.data = data;
    }

    static class Cache {
        private static final int HIGH = 1024;
        private static final int LOW = -128;
        static final LongTag[] cache = new LongTag[1153];

        private Cache() {
        }

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new LongTag((long)(-128 + i));
            }

        }
    }
}
