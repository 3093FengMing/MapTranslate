package me.fengming.maptranslate.core;

import com.google.gson.Gson;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class DataReader <T> {
    protected final Alert alert;
    protected final File file;

    protected DataReader(File f, Alert alert) {
        this.file = f;
        this.alert = alert;
    }
    public abstract void readAll();
    public abstract T read(File f) throws IOException;

    public void outputMessage(String m) {
        TextArea details = new TextArea(m);
        details.editableProperty().set(false);
        this.alert.setGraphic(details);
        this.alert.getDialogPane().setHeaderText("Done!\nCheck the information!");
        this.alert.showAndWait();
    }
    public abstract void writeFiles(Path folder, Gson gson);
}
