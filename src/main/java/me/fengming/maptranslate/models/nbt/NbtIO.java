package me.fengming.maptranslate.models.nbt;

import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.EndTag;
import me.fengming.maptranslate.models.nbt.tags.Tag;

import java.io.DataInputStream;
import java.io.IOException;

public class NbtIO {
    public static Tag read(DataInputStream data) throws IOException {
        byte type = data.readByte(); // useless type = 10
        data.readUTF(); // Tag name (WTF)
        if (type == 0) return EndTag.INSTANCE;
        return CompoundTag.readNamedTagData(TagTypes.getType(type), data);
    }


}
