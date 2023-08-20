package me.fengming.maptranslate.core.regoin;

import me.fengming.maptranslate.models.nbt.tags.CompoundTag;

public class ChunkData {
    private final CompoundTag data;
    private final int x;
    private final int z;
    private final boolean isNew;
    private boolean isLevel;
    public ChunkData(CompoundTag levelTag, int x, int z, boolean isNew, boolean isLevel) {
        this.data = levelTag;
        this.x = x;
        this.z = z;
        this.isNew = isNew;
        this.isLevel = isLevel;
    }

    public boolean isNewVersion() {
        return this.isNew;
    }

    public boolean isLevel() {
        return isLevel;
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
