package me.fengming.maptranslate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.fengming.maptranslate.models.nbt.tags.CompoundTag;
import me.fengming.maptranslate.models.nbt.tags.Tag;
import me.fengming.maptranslate.regoin.ChunkData;
import me.fengming.maptranslate.regoin.RegionFile;
import me.fengming.maptranslate.regoin.RegionReader;
import me.fengming.maptranslate.models.json.TagJsonSerializer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MTApplication extends Application {
    @Override
    public void start(Stage stage) {
        Scene main = new Scene(new BorderPane(), 400, 300);
        BorderPane root = (BorderPane) main.getRoot();
        root.setPadding(new Insets(10, 0, 60, 0));
        stage.setTitle("Map Translate V0.1.0");

        // Json Setting
        HBox hb1 = new HBox(8);
        CheckBox buttonCheckPretty = new CheckBox("Json Pretty");
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().add(buttonCheckPretty);

        // DIM
        HBox hb2 = new HBox(8);
        CheckBox buttonCheckDIMRegion = new CheckBox("DIMRegion");
        CheckBox buttonCheckDIMEntities = new CheckBox("DIMEntities");
        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().addAll(buttonCheckDIMRegion, buttonCheckDIMEntities);
        hb2.setVisible(false);

        // Main
        HBox hb3 = new HBox(8);
        CheckBox buttonCheckDatapack = new CheckBox("Datapacks");
        CheckBox buttonCheckDIM = new CheckBox("DIM");
        CheckBox buttonCheckRegion = new CheckBox("Region");
        CheckBox buttonCheckEntities = new CheckBox("Entities");
        hb3.setAlignment(Pos.CENTER);
        hb3.getChildren().addAll(buttonCheckDatapack, buttonCheckRegion, buttonCheckEntities, buttonCheckDIM);
        buttonCheckDIM.setOnAction(event -> hb2.setVisible(buttonCheckDIM.isSelected()));

        // Text
        Label text = new Label("Map Translate Tool By FengMing3093");
        VBox vb2 = new VBox(text);
        vb2.setAlignment(Pos.CENTER);

        // Vbox
        VBox vb = new VBox();
        Button buttonOpenMapFolder = new Button("Open Map Folder");
        buttonOpenMapFolder.setOnAction(event -> button(stage, buttonCheckPretty.isSelected()));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(buttonOpenMapFolder);
        VBox vb1 = new VBox(20);

        vb1.getChildren().addAll(vb2, hb1, hb3, hb2);

        root.setTop(vb1);
        root.setCenter(vb);

        stage.setScene(main);
        stage.show();
    }

    private static void button(Stage stage, boolean pretty) {
        DirectoryChooser dc1 = new DirectoryChooser();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Done! Check the following!");
        RegionReader rr = new RegionReader(dc1.showDialog(stage).toPath().resolve("region").toFile(), alert);
        try {
            rr.readAll();
            alert.getDialogPane().setExpandableContent(new TextArea("Nothing, you can take the next step now!"));
        } catch (Exception e) {
            alert.getDialogPane().setExpandableContent(new TextArea(e.getMessage()));
        }
        alert.showAndWait();
        DirectoryChooser dc2 = new DirectoryChooser();
        File target = dc2.showDialog(stage);

        for (RegionFile region : rr.getRegions()) {
            CompoundTag regionData = new CompoundTag();
            for (ChunkData chunk : region.getChunks()) {
                CompoundTag data = new CompoundTag();
                if (chunk.isLevel()) {
                    data.put("tilesEntities", chunk.isNewVersion() ? chunk.getData().get("block_entities") : chunk.getData().get("TileEntities"));
                } else {
                    data.put("entities", chunk.getData());
                }
                regionData.put(chunk.getX() + "#" + chunk.getZ(), data);
            }

            GsonBuilder gson = new GsonBuilder()
                    .registerTypeAdapter(Tag.class, new TagJsonSerializer());
            if (pretty) gson.setPrettyPrinting();

            String json = gson.create().toJson(regionData);

            Path chunkPath = target.toPath().resolve(region.getFileName() + ".json");
            try {
                Path parent = chunkPath.getParent();
                if (!Files.exists(parent)) {
                    Files.createDirectory(parent);
                }
                if (!Files.exists(chunkPath)) {
                    Files.createFile(chunkPath);
                }
                Files.writeString(chunkPath, json, StandardOpenOption.WRITE, StandardOpenOption.SYNC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}