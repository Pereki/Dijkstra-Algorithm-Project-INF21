package view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import model.*;
import service.MercatorProjector;

import java.util.HashMap;

public class GraphDisplay {

    // group for UI elements
    private final Group group;

    // viewport in coordinates
    private GeoBounds geoBounds;

    // graphs
    private final HashMap<Integer, GraphLayer> layers = new HashMap<>();

    /**
     * Initializes a new {@code GraphRenderer}
     * @param group A JavaFX {@code Group} where the layers can be placed in
     * @param geoBounds A {@code GeoBounds} object which defines the geographical viewport
     */
    public GraphDisplay(Group group, GeoBounds geoBounds) {
        this.group = group;
        this.geoBounds = geoBounds;
    }

    public Canvas getCanvas() {
        MercatorProjector p = new MercatorProjector(geoBounds.getNorth(), geoBounds.getWest());
        double height = p.getX(geoBounds.getEast()) * 100;
        double width = p.getY(geoBounds.getNorth()) - p.getY(geoBounds.getSouth()) * 100;
        return new Canvas(width, height);
    }

    /**
     * Displays the given {@code GraphLayer} by adding it as a new layer at {@code pos} in the layer stack.
     * @param pos A unique number specifying the position in the layer stack.
     * @param layer A {@code GraphLayer} to be displayed.
     */
    synchronized public void setGraphLayer(Integer pos, GraphLayer layer) {
        layers.put(pos, layer);
        renderLayer(pos);
    }

    synchronized private void renderLayer(int pos) {
        GraphLayer layer = layers.get(pos);
        if (layer == null) {
            return;
        }
        Canvas canvas = getCanvas();
        GraphRenderer.render(layer, canvas, geoBounds);
        canvas.setId(String.valueOf(pos));
        if (group.getChildren().size() <= pos) {
            for (int i = group.getChildren().size(); i <= pos; i++) {
                group.getChildren().add(getCanvas());
            }
        }
        group.getChildren().set(pos, canvas);
    }

    /**
     * Removes a layer from the display.
     * @param key The unique identifying key of the layer.
     * @return The {@code Graph} of the layer if found and removed, {@code null} if there is no layer with that key.
     */
    public GraphLayer removeGraphLayer(int key) {
        group.getChildren().remove(key);
        return this.layers.remove(key);
    }

    public GraphLayer getGraphLayer(int key) {
        return this.layers.get(key);
    }

    public HashMap<Integer, GraphLayer> getGraphLayers() {
        return this.layers;
    }

    public int getGraphLayerCount() {
        return this.layers.size();
    }

    public void setGeoBounds(GeoBounds geoBounds) {
        this.geoBounds = geoBounds;
        rerender();
    }

    public GeoBounds getGeoBounds() {
        return this.geoBounds;
    }

    public void rerender() {
        for (Integer i : layers.keySet()) {
            renderLayer(i);
        }
    }

}
