module me.fengming.maptranslate {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.fengming.maptranslate to javafx.fxml;
    exports me.fengming.maptranslate;
}