package me.fengming.maptranslate;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

public class WindowUtils {
    public static double getMidX(Region region, Node node) {
        return (region.getWidth() - node.getLayoutBounds().getWidth()) / 2;
    }
    public static double getMidY(Region region, Node node) {
        return (region.getHeight() - node.getLayoutBounds().getHeight()) / 2;
    }
}
