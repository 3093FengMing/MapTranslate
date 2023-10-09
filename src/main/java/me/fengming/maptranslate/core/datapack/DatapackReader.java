package me.fengming.maptranslate.core.datapack;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import me.fengming.maptranslate.core.DataReader;
import me.fengming.maptranslate.models.nbt.tags.CompoundTag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DatapackReader extends DataReader<McfunctionFile> {
    private List<McfunctionFile> functions = new ArrayList<>();
    public DatapackReader(File f, Alert alert) {
        super(f, alert);
    }

    public void readAll() {
        File[] datapacks = this.file.listFiles();
        for (File d : datapacks) {
            Path pd = d.toPath();
            if (Files.exists(pd.resolve("data")) && Files.exists(pd.resolve("pack.mcmeta"))) {
                try {
                    Files.list(pd.resolve("data")).forEach(path -> {
                        if (Files.isDirectory(path))
                            try {
                                Files.list(path.resolve("functions")).forEach(funcFile -> {
                                    try {
                                        listFiles(funcFile.toFile());
                                    } catch (Exception e) {
                                        outputMessage(e.getMessage());
                                    }
                                });
                            } catch (IOException e) {
                                outputMessage(e.getMessage());
                            }
                    });
                } catch (Exception e) {
                    outputMessage(e.getMessage());
                }
            }
        }
    }

    public McfunctionFile read(File f) throws IOException {
        return new McfunctionFile(f);
    }

    public void writeFiles(Path folder, Gson gson) {
        CompoundTag outputData = new CompoundTag();
        for (McfunctionFile function : functions) {
            McFunctionData functionData = function.getData();
            outputData.put(functionData.getName(), functionData.getData());
        }
        String json = gson.toJson(outputData);
        Path outputFile = folder.resolve("functions.json");
        try {
            Path parent = outputFile.getParent();
            if (!Files.exists(parent)) {
                Files.createDirectory(parent);
            }
            Files.deleteIfExists(outputFile);
            Files.write(outputFile, json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        } catch (Exception e) {
            outputMessage(e.getMessage());
        }
    }

    private void listFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File value : files) {
                if (value.isDirectory()) {
                    listFiles(value);
                } else {
                    read(value);
                }

            }
        } else {
            read(file);
        }
    }
}
