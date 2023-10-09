package me.fengming.maptranslate.core.datapack;

import me.fengming.maptranslate.models.nbt.tags.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class McFunctionData {
    private CompoundTag data = new CompoundTag();
    private File file;
    public McFunctionData(File f) {
        this.file = f;
    }
    public void addLine(int index1, int index2, String data) {
        Tag tag = this.data.get("lines");
        if (tag instanceof EndTag) tag = new ListTag((byte) 10);
        if (tag instanceof ListTag) {
            ListTag lt = (ListTag) tag;
            CompoundTag arr = new CompoundTag();
            arr.put(index1 + "#" + index2, new StringTag(data));
            lt.add(arr);
        }
    }

    public String getName() {
        return file.getName();
    }

    public CompoundTag getData() {
        return data;
    }
}
