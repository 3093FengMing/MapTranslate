package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class LongArrayTag implements Tag {
    private long[] data;
    public static TagType<LongArrayTag> TYPE = new TagType<LongArrayTag>() {
        @Override
        public LongArrayTag load(DataInput data) throws IOException {
            int length = data.readInt();
            long[] longs = new long[length];
            for (int i = 0; i < length; i++) {
                longs[i] = data.readLong();
            }
            return new LongArrayTag(longs);
        }
    };

    public long[] getData() {
        return data;
    }

    public void setData(long[] data) {
        this.data = data;
    }

    public LongArrayTag(long[] b) {
        this.data = b;
    }
}
