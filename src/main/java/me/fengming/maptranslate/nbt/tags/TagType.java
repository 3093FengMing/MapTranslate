package me.fengming.maptranslate.nbt.tags;

import java.io.DataInput;
import java.io.IOException;

public interface TagType<T extends Tag> {
    T load(DataInput data) throws IOException;
}
