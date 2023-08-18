package me.fengming.maptranslate.regoin;

import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegionReader {
    private final Alert alert;
    private final File folder;
    private final List<RegionFile> regions = new ArrayList<>();
    public RegionReader(File f, Alert alert) {
        if (!f.isDirectory()) {
            alert.setContentText("Target isn't Folder!");
        }
        this.folder = f;
        this.alert = alert;
    }

    public void readAll() throws IOException {
        File[] files = this.folder.listFiles();
        if (files == null) {
            alert.setContentText("Target isn't any files!");
            return;
        }
        for (File file : files) {
            if (file.getName().endsWith(".mca")) {
                regions.add(read(file));
            } else {
                alert.setContentText(file + " isn't a mca file!");
                return;
            }
        }
    }
    public RegionFile read(File f) throws IOException {
        return new RegionFile(f, alert);
    }
}
