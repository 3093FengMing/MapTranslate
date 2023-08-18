package me.fengming.maptranslate;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.fengming.maptranslate.regoin.ChunkData;
import me.fengming.maptranslate.regoin.RegionFile;
import me.fengming.maptranslate.regoin.RegionReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        // Vbox
        VBox vb = new VBox();
        Button buttonOpenMapFolder = new Button("Open Map Folder");
        buttonOpenMapFolder.setOnAction(event -> button(stage));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(buttonOpenMapFolder);
        VBox vb1 = new VBox(20);

        // DIM
        HBox hb3 = new HBox(8);
        CheckBox buttonCheckDIMRegion = new CheckBox("DIMRegion");
        CheckBox buttonCheckDIMEntities = new CheckBox("DIMEntities");
        hb3.setAlignment(Pos.CENTER);
        hb3.getChildren().addAll(buttonCheckDIMRegion, buttonCheckDIMEntities);
        hb3.setVisible(false);

        // Main
        HBox hb1 = new HBox(8);
        CheckBox buttonCheckDatapack = new CheckBox("Datapacks");
        CheckBox buttonCheckDIM = new CheckBox("DIM");
        CheckBox buttonCheckRegion = new CheckBox("Region");
        CheckBox buttonCheckEntities = new CheckBox("Entities");
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().addAll(buttonCheckDatapack, buttonCheckRegion, buttonCheckEntities, buttonCheckDIM);
        buttonCheckDIM.setOnAction(event -> hb3.setVisible(buttonCheckDIM.isSelected()));

        // Text
        Label text = new Label("Map Translate Tool By FengMing3093");
        VBox vb2 = new VBox(text);
        vb2.setAlignment(Pos.CENTER);

        vb1.getChildren().addAll(vb2, hb1, hb3);

        root.setTop(vb1);
        root.setCenter(vb);

        stage.setScene(main);
        stage.show();

    }

    private static void button(Stage stage) {
        DirectoryChooser dc1 = new DirectoryChooser();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Done! Please choose folder for the output!");
        RegionReader rr = new RegionReader(dc1.showDialog(stage).toPath().resolve("region").toFile(), alert);
        try {
            rr.readAll();
        } catch (Exception e) {
            alert.setContentText(e.getMessage());
        }
        alert.show();
        DirectoryChooser dc2 = new DirectoryChooser();
        File target = dc2.showDialog(stage);

        for (RegionFile region : rr.getRegions()) {
            for (ChunkData chunk : region.getChunks()) {
                String name;
                if (chunk.isLevel()) {
                    name = "level." + chunk.getX() + "." + chunk.getZ() + ".json";
                } else {
                    name = "entities." + chunk.getX() + "." + chunk.getZ() + ".json";
                }
                String json = new Gson().toJson(chunk.getData());
                System.out.println("json = " + json);
                Path chunkPath = target.toPath().resolve(region.getFileName()).resolve(name);
                try {
                    if (!Files.exists(chunkPath)) {
                        Files.createDirectory(chunkPath.getParent());
                        Files.createFile(chunkPath);
                    }
                    Files.writeString(chunkPath, json, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW, StandardOpenOption.SYNC);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}