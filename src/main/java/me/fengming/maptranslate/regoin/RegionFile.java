package me.fengming.maptranslate.regoin;

import javafx.scene.control.Alert;
import me.fengming.maptranslate.models.nbt.NbtIO;
import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.EndTag;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile {
    private final int CHUNKS_SIZE = 1024;
    private final List<ChunkData> chunks = new ArrayList<>();
    private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
    private final IntBuffer offsets;
    private final IntBuffer timestamps;
    private FileChannel file;
    private String fileName;

    public RegionFile(File f, Alert alert) throws IOException {
        Path regionFile = f.toPath();
        this.fileName = f.getName();


        this.file = FileChannel.open(regionFile, StandardOpenOption.READ);

        // offset
        offsets = header.asIntBuffer();
        offsets.limit(CHUNKS_SIZE);

        // timestamps
        header.position(CHUNKS_SIZE * 4);
        timestamps = header.asIntBuffer();

        header.position(0);

        int len = this.file.read(this.header, 0L);
        if (len != -1) {
            long size = Files.size(regionFile);
            for (int i = 0; i < CHUNKS_SIZE; i++) {
                int offset = this.offsets.get(i);
                if (offset != 0) {
                    int sectorNumber = getSectorNumber(offset);
                    int numberSector = getNumberSector(offset);
                    if (sectorNumber < 2) {
                        this.offsets.put(i, 0);
                    } else if (numberSector == 0) {
                        this.offsets.put(i, 0);
                    } else if ((long) sectorNumber * CHUNKS_SIZE * 4 > size) {
                        this.offsets.put(i, 0);
                    }
                }
            }
        }

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                DataInputStream stream;
                ChunkPos pos = new ChunkPos(i, j);
                if (hasChunk(pos)) {
                    int offset = getOffset(pos);
                    if (i == 0) continue;

                    int sectorNumber = getSectorNumber(offset);
                    int numberSector = getNumberSector(offset);

                    int size = numberSector * CHUNKS_SIZE * 4;
                    ByteBuffer bytebuffer = ByteBuffer.allocate(size);
                    this.file.read(bytebuffer, sectorNumber * 4096L);
                    bytebuffer.flip();

                    if (bytebuffer.remaining() < 5) continue;

                    int i1 = bytebuffer.getInt();
                    byte version = bytebuffer.get();
                    if (i1 == 0) continue;
                    int l = i1 - 1;
                    if (isExternalStreamChunk(version)) {
                        String s = "c." + pos.getX() + "." + pos.getZ() + ".mcc";
                        Path path = regionFile.getParent().resolve(s);
                        if (Files.isRegularFile(path)) continue;
                        stream = new DataInputStream(new ByteArrayInputStream(decompress(Files.newInputStream(path).readAllBytes(), getExternalChunkVersion(version))));
                    } else if (l > bytebuffer.remaining()) {
                        continue;
                    } else if (l < 0) {
                        continue;
                    } else {
                        stream = new DataInputStream(new ByteArrayInputStream(decompress(new ByteArrayInputStream(bytebuffer.array(), bytebuffer.position(), l).readAllBytes(), version)));
                    }
                    CompoundTag dataTag = (CompoundTag) NbtIO.read(stream);
                    ChunkData dataChunk;

                    if (dataTag.get("Level") instanceof EndTag) { // new version (Anvil)
                        dataChunk = new ChunkData(dataTag, i, j, true);
                    } else { // old version (MCRegion)
                        dataTag = (CompoundTag) dataTag.get("Level");
                        dataChunk = new ChunkData(dataTag, i, j, false);
                    }

                    if (dataTag.get("Status") instanceof EndTag) { // entities chunk
                        dataChunk.setLevel(false);
                        chunks.add(dataChunk);
                    } else if (dataTag.get("Status").getData().equals("full")) { // region chunk
                        dataChunk.setLevel(true);
                        chunks.add(dataChunk);
                    }
                }
            }
        }

    }

    public List<ChunkData> getChunks() {
        return chunks;
    }

    public byte[] decompress(byte[] data, byte chunkVersion) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        InputStream inputStream = null;
        byte[] bytes = new byte[1024];
        int read;

        switch (chunkVersion) {
            case 1 -> inputStream = new GZIPInputStream(byteArrayInputStream);
            case 2 -> inputStream = new InflaterInputStream(byteArrayInputStream);
            case 3 -> {
                return bytes;
            }
        }

        while ((read = inputStream.read(bytes, 0, bytes.length)) != -1)
        {
            buffer.write(bytes, 0, read);
        }
        buffer.flush();

        return buffer.toByteArray();
    }

    private int getOffset(ChunkPos pos) {
        return this.offsets.get(getOffsetIndex(pos));
    }

    public boolean hasChunk(ChunkPos pos) {
        return this.getOffset(pos) != 0;
    }

    private static int getOffsetIndex(ChunkPos pos) {
        return pos.getLocalRegionX() + pos.getLocalRegionZ() * 32;
    }

    private static int getSectorNumber(int offset) {
        return offset >> 8 & 16777215;
    }
    private static int getNumberSector(int offset) {
        return offset & 255;
    }
    private static boolean isExternalStreamChunk(byte pVersionByte) {
        return (pVersionByte & 128) != 0;
    }
    private static byte getExternalChunkVersion(byte pVersionByte) {
        return (byte)(pVersionByte & -129);
    }

    public String getFileName() {
        String[] s = fileName.split("\\.");
        return s[0] + s[1] + "." + s[2];
    }

}
