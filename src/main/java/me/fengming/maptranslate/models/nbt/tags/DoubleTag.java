package me.fengming.maptranslate.models.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public class DoubleTag implements NumberTag {
    
    private double data;
    public static final DoubleTag ZERO = new DoubleTag(0.0F);

    public static TagType<DoubleTag> TYPE = new TagType<DoubleTag>() {
        @Override
        public DoubleTag load(DataInput data) throws IOException {
            return DoubleTag.valueOf(data.readDouble());
        }
    };

    public Double getData() {
        return data;
    }

    public DoubleTag(double data) {
        this.data = data;
    }

    public static DoubleTag valueOf(double data) {
        return data == 0 ? ZERO : new DoubleTag(data);
    }
}
