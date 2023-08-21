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
import me.fengming.maptranslate.models.nbt.tags.ListTag;
import me.fengming.maptranslate.models.nbt.tags.Tag;
import me.fengming.maptranslate.core.regoin.ChunkData;
import me.fengming.maptranslate.core.regoin.RegionFile;
import me.fengming.maptranslate.core.regoin.RegionReader;
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

        // Text
        VBox vb2 = new VBox();
        Label text3 = new Label("Map Translate Tool By FengMing3093");
        vb2.setAlignment(Pos.TOP_CENTER);
        vb2.getChildren().add(text3);

        // Json Setting
        VBox vb4 = new VBox(4);
        Label text1 = new Label("------Json Setting------");
        HBox hb1 = new HBox(8);
        CheckBox buttonCheckPretty = new CheckBox("Json Pretty Printing");
        CheckBox buttonCheckDisableHtmlEscaping = new CheckBox("Json Disable Html Escaping");
        hb1.setAlignment(Pos.CENTER);
        hb1.getChildren().addAll(buttonCheckPretty, buttonCheckDisableHtmlEscaping);
        vb4.setAlignment(Pos.CENTER);
        vb4.getChildren().addAll(text1, hb1);

        // DIM
        VBox vb5 = new VBox(4);
        Label text4 = new Label("------DIM Setting------");
        HBox hb2 = new HBox(8);
        CheckBox buttonCheckDIMRegion = new CheckBox("DIMRegion");
        CheckBox buttonCheckDIMEntities = new CheckBox("DIMEntities");
        hb2.setAlignment(Pos.CENTER);
        hb2.getChildren().addAll(buttonCheckDIMRegion, buttonCheckDIMEntities);
        vb5.setAlignment(Pos.CENTER);
        vb5.getChildren().addAll(text4, hb2);
        vb5.setVisible(false);

        // Main
        VBox vb3 = new VBox(4);
        Label text2 = new Label("------Action Setting------");
        HBox hb3 = new HBox(8);
        CheckBox buttonCheckDatapack = new CheckBox("Datapacks");
        CheckBox buttonCheckDIM = new CheckBox("DIM");
        CheckBox buttonCheckRegion = new CheckBox("Region");
        CheckBox buttonCheckEntities = new CheckBox("Entities");
        hb3.setAlignment(Pos.CENTER);
        hb3.getChildren().addAll(buttonCheckDatapack, buttonCheckRegion, buttonCheckEntities, buttonCheckDIM);
        vb3.setAlignment(Pos.CENTER);
        vb3.getChildren().addAll(text2, hb3);
        buttonCheckDIM.setOnAction(event -> vb5.setVisible(buttonCheckDIM.isSelected()));

        // Action
        VBox vb6 = new VBox(4);
        Label text5 = new Label("------Action------");
        HBox hb4 = new HBox(8);
        Button buttonReadMap = new Button("Extract");
        Button buttonWriteMap = new Button("Restore");
        buttonReadMap.setOnAction(event -> readButton(stage, buttonCheckPretty.isSelected(), buttonCheckDisableHtmlEscaping.isSelected(), buttonCheckRegion.isSelected(), buttonCheckEntities.isSelected(), buttonCheckDatapack.isSelected(), buttonCheckDIMRegion.isSelected(), buttonCheckDIMEntities.isSelected()));
        buttonWriteMap.setOnAction(event -> writeButton(stage));
        hb4.getChildren().addAll(buttonReadMap, buttonWriteMap);
        hb4.setAlignment(Pos.CENTER);
        vb6.setAlignment(Pos.BOTTOM_CENTER);
        vb6.getChildren().addAll(text5, hb4);

        // Add Components
        VBox vb1 = new VBox(20);
        vb1.getChildren().addAll(vb2, vb4, vb3, vb5, vb6);

        root.setTop(vb1);

        stage.setScene(main);
        stage.show();
    }

    private static void readButton(Stage stage, boolean pretty, boolean disableEscaping, boolean region, boolean entities, boolean datapacks, boolean dimregion, boolean dimentities) {
        DirectoryChooser dc1 = new DirectoryChooser();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Check the information!");
        alert.setHeaderText(" ");
        File saves = dc1.showDialog(stage);
        if (saves == null) {
            Utils.alertMessage(alert, "Save isn't selected!");
            return;
        }
        if (!Files.exists(saves.toPath().resolve("level.dat"))) {
            Utils.alertMessage(alert, "That isn't a save!");
            return;
        }

        Utils.alertMessage(alert, "Select a folder for outputting!");
        DirectoryChooser dc2 = new DirectoryChooser();
        File target = dc2.showDialog(stage);
        if (target == null) {
            Utils.alertMessage(alert, "Target isn't selected!");
            return;
        }

        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Tag.class, new TagJsonSerializer());
        if (disableEscaping) builder.disableHtmlEscaping();
        if (pretty) builder.setPrettyPrinting();
        Gson gson = builder.create();

        // region
        if (region) {
            RegionReader rr = new RegionReader(saves.toPath().resolve("region").toFile(), alert);
            rr.readAll();
            rr.writeFiles(target.toPath().resolve("region"), gson);
        }
        // entities
        if (entities) {
            RegionReader er = new RegionReader(saves.toPath().resolve("entities").toFile(), alert);
            er.readAll();
            er.writeFiles(target.toPath().resolve("entities"), gson);
        }

        for (File file : saves.listFiles()) {
            String fileName = file.getName();
            if (file.isDirectory() && fileName.startsWith("DIM")) {
                // DIM region
                if (dimregion) {
                    if (Files.notExists(file.toPath().resolve("region"))) continue;
                    RegionReader rr = new RegionReader(saves.toPath().resolve(fileName).resolve("region").toFile(), alert);
                    rr.readAll();
                    rr.writeFiles(target.toPath().resolve(fileName).resolve("region"), gson);
                }
                // DIM entities
                if (dimentities) {
                    if (Files.notExists(file.toPath().resolve("entities"))) continue;
                    RegionReader er = new RegionReader(saves.toPath().resolve("entities").toFile(), alert);
                    er.readAll();
                    er.writeFiles(target.toPath().resolve(fileName).resolve("entities"), gson);
                }
            }
        }

        // datapack
        // TODO

        Utils.alertMessage(alert, "Completed, please check the folder " + target + " !");
    }
    private static void writeButton(Stage stage) {

    }

    public static void main(String[] args) {
        launch();
    }
}