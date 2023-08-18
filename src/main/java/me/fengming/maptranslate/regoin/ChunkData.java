package me.fengming.maptranslate.regoin;

import me.fengming.maptranslate.nbt.tags.CompoundTag;

public class ChunkData {
    private final CompoundTag data;
    private final int x;
    private final int z;
    public ChunkData(CompoundTag levelTag, int x, int z) {
        this.data = levelTag;
        this.x = x;
        this.z = z;
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
