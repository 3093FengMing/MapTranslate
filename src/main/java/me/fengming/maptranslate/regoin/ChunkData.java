package me.fengming.maptranslate.regoin;

import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.EndTag;

public class ChunkData {
    private final CompoundTag data;
    private final int x;
    private final int z;
    private final boolean isNew;
    private boolean isLevel;
    public ChunkData(CompoundTag levelTag, int x, int z, boolean isNew) {
        this.data = levelTag;
        this.x = x;
        this.z = z;
        this.isNew = isNew;
    }

    public boolean isNewVersion() {
        return this.isNew;
    }
    public void setLevel(boolean isLevel) {
        this.isLevel = isLevel;
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
