package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.HashMap;

public class GraphRenderer {

    public class GraphRendererBounds {
        double north;
        double east;
        double south;
        double west;

        GraphRendererBounds(double westLon, double eastLon, double northLat, double southLat) {
            this.north = northLat;
            this.east = eastLon;
            this.south = southLat;
            this.west = westLon;
        }

        public double getWidth() {
            return Math.abs(this.west - this.east);
        }

        public double getHeight() {
            return Math.abs(this.north - this.south);
        }

        public double getNorth() {
            return north;
        }

        public void setNorth(double north) {
            this.north = north;
        }

        public double getEast() {
            return east;
        }

        public void setEast(double east) {
            this.east = east;
        }

        public double getSouth() {
            return south;
        }

        public void setSouth(double south) {
            this.south = south;
        }

        public double getWest() {
            return west;
        }

        public void setWest(double west) {
            this.west = west;
        }
    }

    // background & size
    private final StackPane pane;
    private final double height;
    private final double width;

    // all display elements
    private final Group group;
    private final ObservableList<Node> elements;

    // display layers
    private final Group layersContainerGroup;
    private final ObservableList<Node> layerList;

    // display properties
    // for Germany: W: 5.590663; E: 15.587623; N: 55.157607; S: 47.242657;
    private GraphRendererBounds geoBounds;

    // graphs
    private HashMap<String, Graph> graphs = new HashMap<>();

    /**
     * Initializes a new {@code GraphRenderer}
     * @param group A JavaFX {@code Group} where the layers can be placed in
     * @param pane A JavaFX {@code StackPane} on which the layers will be stacked
     */
    GraphRenderer(Group group, StackPane pane) {
        this.pane = pane;
        this.height = pane.getHeight();
        this.width = pane.getWidth();
        this.group = group;
        this.elements = group.getChildren();
        this.layersContainerGroup = new Group();
        this.elements.add(this.layersContainerGroup);
        this.layerList = this.layersContainerGroup.getChildren();
        this.setViewBounds(5.590663, 15.587623, 55.157607, 47.242657);
    }

    /**
     * Displays the given {@code Graph} by adding it as a new layer on top of the existing ones.
     * @param key A unique identifying key for the layer.
     * @param graph The {@code Graph} object.
     * @param color The color in which it should be displayed.
     * @return {@code true} if successful, {@code false} if the given key already exists.
     */
    public boolean addGraphLayer(String key, Graph graph, Color color) {
        if (this.graphs.containsKey(key))
            return false;
        this.graphs.put(key, graph);
        Group g = new Group();
        g.setId(key);

        double viewHeight = this.pane.getHeight();
        double viewWidth = this.pane.getWidth();

        double geoNorth = geoBounds.getNorth();
        double geoWest = geoBounds.getWest();
        double geoHeight = geoBounds.getHeight();
        double geoWidth = geoBounds.getWidth();

        for (Edge e : graph.getEdgeList()) {

            Vertex start = e.getStartingVertex();
            double xStart = Math.abs(start.getLat() - geoNorth) / geoHeight * viewHeight;
            double yStart = Math.abs(start.getLon() - geoWest) / geoWidth * viewWidth;

            Vertex end = e.getEndingVertex();
            double xEnd = Math.abs(end.getLat() - geoNorth) / geoHeight * viewHeight;
            double yEnd = Math.abs(end.getLon() - geoWest) / geoWidth * viewWidth;

            Line l = new Line(xStart, yStart, xEnd, yEnd);
            l.setStroke(color);
            g.getChildren().add(l);
        }

        this.layerList.add(g);
        return true;
    }

    /**
     * Removes a layer from the display.
     * @param key The unique identifying key of the layer.
     * @return The {@code Graph} of the layer if found and removed, {@code null} if there is no layer with that key.
     */
    public Graph removeGraphLayer(String key) {
        if (!this.graphs.containsKey(key))
            return null;
        for (Node n : this.layerList) {
            if (n.getId().equals(key)) {
                this.layerList.remove(n);
                return this.graphs.remove(key);
            }
        }
        return null;
    }

    public HashMap<String, Graph> getGraphLayers() {
        return this.graphs;
    }

    public int getGraphLayerCount() {
        return this.graphs.size();
    }

    // TODO: Add methods to control the layers' order

    public void setViewBounds(double westLon, double eastLon, double northLat, double southLat) {
        this.geoBounds = new GraphRendererBounds(westLon, eastLon, northLat, southLat);
    }

    public GraphRendererBounds getGeoBounds() {
        return this.geoBounds;
    }

}
