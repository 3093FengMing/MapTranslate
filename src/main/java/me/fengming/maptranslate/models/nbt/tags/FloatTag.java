package me.fengming.maptranslate.models.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class FloatTag implements NumberTag {
    
    private float data;
    public static final FloatTag ZERO = new FloatTag(0.0F);

    public static TagType<FloatTag> TYPE = new TagType<FloatTag>() {
        @Override
        public FloatTag load(DataInput data) throws IOException {
            return FloatTag.valueOf(data.readFloat());
        }
    };

    public Float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public FloatTag(float data) {
        this.data = data;
    }

    public static FloatTag valueOf(float data) {
        return data == 0 ? ZERO : new FloatTag(data);
    }
}
