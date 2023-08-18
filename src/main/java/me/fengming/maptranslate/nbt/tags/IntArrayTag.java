package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class IntArrayTag implements Tag {
    private final int[] data;
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

    public IntArrayTag(int[] b) {
        this.data = b;
    }
}
