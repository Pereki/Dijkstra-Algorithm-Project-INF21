package view;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GraphRendererOptions {
    private Color routeColor = Color.GRAY;
    private Color labelColor = Color.BLACK;

    private double lineWidth = 2;
    private double dotRadius = 8;

    private boolean showLabels = true;
    private boolean dotJunctions = true;
    private boolean fillShape = false;

    private Font font = new Font(12);

    private HashSet<String> showLabelsEquals = new HashSet<>();



    // alternate setting methods

    public GraphRendererOptions routeColor(Color color) {
        this.routeColor = color;
        return this;
    }

    public GraphRendererOptions labelColor(Color color) {
        this.labelColor = color;
        return this;
    }

    public GraphRendererOptions lineWidth(double width) {
        this.lineWidth = width;
        return this;
    }

    public GraphRendererOptions dotRadius(double radius) {
        this.dotRadius = radius;
        return this;
    }

    public GraphRendererOptions showLabels(boolean show) {
        this.showLabels = show;
        return this;
    }

    public GraphRendererOptions dotJunctions(boolean junctions) {
        this.dotJunctions = junctions;
        return this;
    }

    public GraphRendererOptions fillShape(boolean fill) {
        this.fillShape = fill;
        return this;
    }

    public GraphRendererOptions font(Font font) {
        this.font = font;
        return this;
    }

    public GraphRendererOptions showLabelsEquals(String... strings) {
        this.showLabelsEquals.addAll(Arrays.asList(strings));
        return this;
    }



    // getters & setters

    public Color getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(Color routeColor) {
        this.routeColor = routeColor;
    }

    public Color getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public double getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(double dotRadius) {
        this.dotRadius = dotRadius;
    }

    public boolean isShowLabels() {
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    public boolean isDotJunctions() {
        return dotJunctions;
    }

    public void setDotJunctions(boolean dotJunctions) {
        this.dotJunctions = dotJunctions;
    }

    public boolean isFillShape() {
        return fillShape;
    }

    public void setFillShape(boolean fillShape) {
        this.fillShape = fillShape;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public HashSet<String> getShowLabelsEquals() {
        return showLabelsEquals;
    }

    public void setShowLabelsEquals(HashSet<String> showLabelsEquals) {
        this.showLabelsEquals = showLabelsEquals;
    }
}
