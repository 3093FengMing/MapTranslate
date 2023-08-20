package me.fengming.maptranslate.core.regoin;

public class ChunkPos {
    private int x;
    private int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getLocalRegionX() {
        return x & 31;
    }

    public int getLocalRegionZ() {
        return z & 31;
    }
}
