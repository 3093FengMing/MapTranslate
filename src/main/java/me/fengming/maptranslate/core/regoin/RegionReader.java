package me.fengming.maptranslate.core.regoin;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import me.fengming.maptranslate.core.DataReader;
import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.ListTag;
import me.fengming.maptranslate.models.nbt.tags.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class RegionReader extends DataReader<RegionFile> {
    private final List<RegionFile> regions = new ArrayList<>();
    public RegionReader(File f, Alert alert) {
        super(f, alert);
        if (!f.isDirectory()) {
            outputMessage("[ERROR] Target isn't a file!");
        }
    }

    public void readAll() {
        File[] files = this.file.listFiles();
        if (files == null) {
            this.alert.setContentText("[ERROR] Target hasn't any files!");
            return;
        }
        for (File file : files) {
            if (file.getName().endsWith(".mca")) {
                try {
                    regions.add(read(file));
                } catch (IOException e) {
                    outputMessage(e.getMessage());
                }
            } else {
                outputMessage( "[WARN] " + file + " isn't a mca file!");
                return;
            }
        }
    }
    public RegionFile read(File f) throws IOException {
        return new RegionFile(f);
    }

    public List<RegionFile> getRegions() {
        return regions;
    }

    public void writeFiles(Path f, Gson gson) {
        for (RegionFile region_ : this.getRegions()) {
            CompoundTag regionData = new CompoundTag();
            for (ChunkData chunk : region_.getChunks()) {
                CompoundTag data = new CompoundTag();
                if (chunk.isLevel()) {
                    Tag tiles = chunk.isNewVersion() ? chunk.getData().get("block_entities") : chunk.getData().get("TileEntities");
                    if (tiles instanceof ListTag lt) {
                        if (lt.getData().isEmpty()) continue;
                        data.put("tilesEntities", lt);
                    } else continue;
                } else {
                    data.put("entities", chunk.getData());
                }
                regionData.put(chunk.getX() + "#" + chunk.getZ(), data);
            }
            String json = gson.toJson(regionData);
            Path regionFile = f.resolve(region_.getFileName() + ".json");
            try {
                Path parent = regionFile.getParent();
                Path parent1 = parent.getParent();
                if (!Files.exists(parent1)) {
                    Files.createDirectory(parent1);
                }
                if (!Files.exists(parent)) {
                    Files.createDirectory(parent);
                }
                Files.deleteIfExists(regionFile);
                Files.writeString(regionFile, json, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            } catch (Exception e) {
                outputMessage(e.getMessage());
            }
        }
    }
}
