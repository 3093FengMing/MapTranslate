package me.fengming.maptranslate.core.datapack;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import me.fengming.maptranslate.core.DataReader;

import java.io.File;
import java.nio.file.Path;

public class DatapackReader extends DataReader<McfunctionFile> {
    protected DatapackReader(File f, Alert alert) {
        super(f, alert);
    }

    public void readAll() {

    }

    public McfunctionFile read(File f) {
        return null;
    }

    public void writeFiles(Path folder, Gson gson) {

    }
}
