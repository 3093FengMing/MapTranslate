package me.fengming.maptranslate.models.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class IntArrayTag implements ArrayTag {
    
    private int[] data;
    public static TagType<IntArrayTag> TYPE = new TagType<IntArrayTag>() {
        @Override
        public IntArrayTag load(DataInput data) throws IOException {
            int length = data.readInt();
            int[] ints = new int[length];
            for (int i = 0; i < length; i++) {
                ints[i] = data.readInt();
            }
            return new IntArrayTag(ints);
        }
    };

    public Integer[] getData() {
        Integer[] ints = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            ints[i] = data[i];
        }
        return ints;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public IntArrayTag(int[] b) {
        this.data = b;
    }
}
