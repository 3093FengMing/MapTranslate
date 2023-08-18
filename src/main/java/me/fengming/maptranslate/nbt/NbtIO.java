package me.fengming.maptranslate.nbt;

import me.fengming.maptranslate.nbt.tags.CompoundTag;
import me.fengming.maptranslate.nbt.tags.EndTag;
import me.fengming.maptranslate.nbt.tags.Tag;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NbtIO {
    public static Tag read(DataInputStream data) throws IOException {
        byte type = data.readByte(); // useless type = 10
        data.readUTF(); // Tag name (WTF)
        if (type == 0) return EndTag.INSTANCE;
        return CompoundTag.readNamedTagData(TagTypes.getType(type), data);
    }


}
