module me.fengming.maptranslate {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens me.fengming.maptranslate to javafx.fxml;
    opens me.fengming.maptranslate.nbt.tags to com.google.gson;
    exports me.fengming.maptranslate;
}