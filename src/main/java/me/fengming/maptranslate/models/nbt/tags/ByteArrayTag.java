package me.fengming.maptranslate.models.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class ByteArrayTag implements ArrayTag {

    private byte[] data;
    public static TagType<ByteArrayTag> TYPE = new TagType<ByteArrayTag>() {
        @Override
        public ByteArrayTag load(DataInput data) throws IOException {
            int length = data.readInt();
            byte[] bytes = new byte[length];
            data.readFully(bytes);
            return new ByteArrayTag(bytes);
        }
    };

    public Byte[] getData() {
        Byte[] bytes = new Byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bytes[i] = data[i];
        }
        return bytes;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ByteArrayTag(byte[] b) {
        this.data = b;
    }

}
