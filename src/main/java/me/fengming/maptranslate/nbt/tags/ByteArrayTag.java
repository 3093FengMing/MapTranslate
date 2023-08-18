package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class ByteArrayTag implements Tag {
    private final byte[] data;
    public static TagType<ByteArrayTag> TYPE = new TagType<ByteArrayTag>() {
        @Override
        public ByteArrayTag load(DataInput data) throws IOException {
            int length = data.readInt();
            byte[] bytes = new byte[length];
            data.readFully(bytes);
            return new ByteArrayTag(bytes);
        }
    };

    public byte[] getData() {
        return data;
    }

    public ByteArrayTag(byte[] b) {
        this.data = b;
    }

}
