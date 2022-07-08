package view;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.*;
import service.MercatorProjector;

import java.util.HashMap;
import java.util.List;

public class GraphRenderer {

    // background & size
    private double height;
    private double width;

    // all display elements
    private final Group group;

    // display properties
    private GeoBounds geoBounds;

    // graphs
    private final HashMap<String, Graph> graphs = new HashMap<>();
    
    //projection
    private MercatorProjector projector;

    /**
     * Initializes a new {@code GraphRenderer}
     * @param group A JavaFX {@code Group} where the layers can be placed in
     */
    public GraphRenderer(Group group, GeoBounds geoBounds) {
        this.group = group;
        this.geoBounds = geoBounds;
        calculateDimensions();

        this.projector = new MercatorProjector(
                geoBounds.getSouth(),
                geoBounds.getWest()
        );
    }

    /**
     * Displays the given {@code Graph} by adding it as a new layer on top of the existing ones.
     * @param key A unique identifying key for the layer.
     * @param graph The {@code Graph} object.
     * @param options An {@code Options} object specifying how the graph should be displayed.
     * @return {@code true} if successful, {@code false} if the given key already exists.
     */
    synchronized public boolean addGraphLayer(String key, Graph graph, GraphRendererOptions options) {
        if (this.graphs.containsKey(key))
            return false;
        this.graphs.put(key, graph);
        Canvas c = new Canvas(this.width, this.height);
        //Canvas c = this.canvas;
        GraphicsContext gc = c.getGraphicsContext2D();
        c.setId(key);

        // draw edges
        for (Edge e : graph.getEdgeList()) {
            Vertex start = e.getStartingVertex();
            double xStart = this.getX(start.getLon());
            double yStart = this.getY(start.getLat());

            Vertex end = e.getEndingVertex();
            double xEnd = this.getX(end.getLon());
            double yEnd = this.getY(end.getLat());

            gc.setStroke(options.getRouteColor());
            gc.setLineWidth(options.getLineWidth());
            gc.strokeLine(xStart, yStart, xEnd, yEnd);
        }

        // draw vertices
        for (Vertex v : graph.getVertexList()) {
            double x = this.getX(v.getLon());
            double y = this.getY(v.getLat());

            // draw dot on junctions and cities
            if (v.getJunction() || v.getIdentifier() != null) {
                gc.setFill(options.getRouteColor());
                gc.fillOval(
                        x - 0.5 * options.getDotRadius(),
                        y - 0.5 * options.getDotRadius(),
                        options.getDotRadius(),
                        options.getDotRadius()
                );
            }

            // draw labels
            if (v.getIdentifier() != null) {
                gc.setFill(options.getLabelColor());
                gc.fillOval(
                        x - 0.25 * options.getDotRadius(),
                        y - 0.25 * options.getDotRadius(),
                        0.5 * options.getDotRadius(),
                        0.5 * options.getDotRadius()
                );

                gc.setFont(options.getFont());
                gc.setStroke(options.getLabelColor());
                gc.fillText(v.getIdentifier(), x + 1.5 * options.getDotRadius(), y);
            }
        }
        this.group.getChildren().add(c);
        return true;
    }

    private double getX(double longitude) {
        return projector.getX(longitude) / projector.getX(geoBounds.getEast()) * width;
    }

//    private double getY(double latitude) {
//        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
//        return pane.getHeight() - ( projector.getY(latitude) / projector.getY(geoBounds.getNorth()) * pane.getHeight() );
//    }

//    private double getX(double longitude) {
//        return Math.abs(longitude - geoBounds.getWest()) / geoBounds.getWidth() * pane.getWidth();
//    }

    private double getY(double latitude) {
        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
        return Math.abs(latitude - geoBounds.getNorth()) / geoBounds.getHeight() * height;
    }

    /**
     * Removes a layer from the display.
     * @param key The unique identifying key of the layer.
     * @return The {@code Graph} of the layer if found and removed, {@code null} if there is no layer with that key.
     */
    public Graph removeGraphLayer(String key) {
        if (!this.graphs.containsKey(key))
            return null;
        List<Node> nodes = this.group.getChildren();
        for (Node n : nodes) {
            if (n.getId().equals(key)) {
                nodes.remove(n);
                return this.graphs.remove(key);
            }
        }
        return null;
    }

    public Graph getGraphLayer(String key) {
        return this.graphs.get(key);
    }

    public HashMap<String, Graph> getGraphLayers() {
        return this.graphs;
    }

    public int getGraphLayerCount() {
        return this.graphs.size();
    }

    // TODO: Add methods to control the layers' order

    public void setGeoBounds(GeoBounds geoBounds) {
        this.geoBounds = geoBounds;
        calculateDimensions();
    }

    private void calculateDimensions() {
        this.height = geoBounds.getWidth() * 100;
        this.width = geoBounds.getHeight() * 100;
    }

    public GeoBounds getGeoBounds() {
        return this.geoBounds;
    }
}
