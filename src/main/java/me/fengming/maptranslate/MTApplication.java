package me.fengming.maptranslate;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.fengming.maptranslate.regoin.RegionFile;
import me.fengming.maptranslate.regoin.RegionReader;

import java.io.IOException;

public class MTApplication extends Application {
    @Override
    public void start(Stage stage) {
        Scene main = new Scene(new BorderPane(), 400, 300);
        BorderPane root = (BorderPane) main.getRoot();
        stage.setTitle("Map Translate V0.1.0");
        VBox vb = new VBox();
        Button buttonOpenMapFolder = new Button("Open Map Folder");
        buttonOpenMapFolder.setOnAction(event -> {
            DirectoryChooser dc = new DirectoryChooser();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Done!");
            RegionReader rr = new RegionReader(dc.showDialog(stage).toPath().resolve("region").toFile(), alert);
            try {
                rr.readAll();
            } catch (IOException e) {
                alert.setContentText(e.getMessage());
            }
            alert.show();
        });
        vb.setAlignment(Pos.BOTTOM_CENTER);
        vb.getChildren().add(buttonOpenMapFolder);

        root.setCenter(vb);
        stage.setScene(main);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}