package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class ByteTag implements Tag {
    private byte data;
    public static TagType<ByteTag> TYPE = new TagType<ByteTag>() {
        @Override
        public ByteTag load(DataInput data) throws IOException {
            return ByteTag.valueOf(data.readByte());
        }
    };

    public ByteTag(byte b) {
        this.data = b;
    }

    public static ByteTag valueOf(byte b) {
        return Cache.cache[128 + b];
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    static class Cache {
        static final ByteTag[] cache = new ByteTag[256];

        static {
            for(int i = 0; i < cache.length; ++i) {
                cache[i] = new ByteTag((byte)(i - 128));
            }

        }
    }
}
