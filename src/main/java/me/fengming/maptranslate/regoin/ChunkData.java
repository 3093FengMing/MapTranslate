package me.fengming.maptranslate.regoin;

import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.EndTag;

public class ChunkData {
    private final CompoundTag data;
    private final int x;
    private final int z;
    public ChunkData(CompoundTag levelTag, int x, int z) {
        this.data = levelTag;
        this.x = x;
        this.z = z;
    }

    public boolean isLevel() {
        return (data.get("Entities") instanceof EndTag);
    }

    public CompoundTag getData() {
        return data;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "ChunkData{" +
                "data=" + data +
                ", x=" + x +
                ", z=" + z +
                '}';
    }
}
