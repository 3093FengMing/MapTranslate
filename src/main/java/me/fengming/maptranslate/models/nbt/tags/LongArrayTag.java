package me.fengming.maptranslate.models.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class LongArrayTag implements ArrayTag {
    
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

    public Long[] getData() {
        Long[] longs = new Long[data.length];
        for (int i = 0; i < data.length; i++) {
            longs[i] = data[i];
        }
        return longs;
    }

    public void setData(long[] data) {
        this.data = data;
    }

    public LongArrayTag(long[] b) {
        this.data = b;
    }
}
