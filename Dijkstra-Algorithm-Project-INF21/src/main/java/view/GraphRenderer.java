package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.CoordinatePair;
import model.Edge;
import model.Graph;
import model.Vertex;
import service.MercatorProjector;

import java.util.HashMap;
import java.util.List;

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

    public class GraphRendererStyle {
        // styling
        private double lineWidth;
        private double dotRadius;
        private Font font;

        public GraphRendererStyle(double lineWidth, double dotRadius, Font font) {
            this.lineWidth = lineWidth;
            this.dotRadius = dotRadius;
            this.font = font;
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

        public Font getFontSize() {
            return font;
        }

        public void setFontSize(Font font) {
            this.font = font;
        }
    }

    // background & size
    private final StackPane pane;
    private final double height;
    private final double width;

    // all display elements
    private final Group group;

    // display properties
    // for Germany: W: 5.866342; E: 15.041892; N: 55.058307; S: 47.270112;
    private GraphRendererBounds geoBounds;

    // graphs
    private HashMap<String, Graph> graphs = new HashMap<>();

    // styling
    private GraphRendererStyle style = new GraphRendererStyle(
            4, 8, new Font(24)
    );
    
    //projection
    private MercatorProjector projector;

    /**
     * Initializes a new {@code GraphRenderer}
     * @param group A JavaFX {@code Group} where the layers can be placed in
     * @param pane A JavaFX {@code StackPane} on which the layers will be stacked
     */
    public GraphRenderer(Group group, StackPane pane) {
        this.pane = pane;
        this.height = pane.getHeight();
        this.width = pane.getWidth();
        this.group = group;
        
        this.setViewBounds(5.866342, 15.041892, 55.058307, 47.270112);

//        this.projector = new MercatorProjector(
//                0.5 * (geoBounds.getNorth() + geoBounds.getSouth()),
//                0.5 * (geoBounds.getWest() + geoBounds.getEast())
//        );
        this.projector = new MercatorProjector(
                geoBounds.getSouth(),
                geoBounds.getWest()
        );
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
        Canvas c = new Canvas(this.width, this.height);
        GraphicsContext gc = c.getGraphicsContext2D();
        c.setId(key);

        double centerLat = 0.5 * (geoBounds.getNorth() + geoBounds.getSouth());
        double centerLon = 0.5 * (geoBounds.getWest() + geoBounds.getEast());
        MercatorProjector projector = new MercatorProjector(centerLat, centerLon);

        for (Edge e : graph.getEdgeList()) {
            Vertex start = e.getStartingVertex();
            double xStart = this.getX(start.getLon());
            double yStart = this.getY(start.getLat());

            Vertex end = e.getEndingVertex();
            double xEnd = this.getX(end.getLon());
            double yEnd = this.getY(end.getLat());

            gc.setStroke(color);
            gc.setLineWidth(style.lineWidth);
            gc.strokeLine(xStart, yStart, xEnd, yEnd);
        }

        for (Vertex v : graph.getVertexList()) {
            double x = this.getX(v.getLon());
            double y = this.getY(v.getLat());

            if (v.getJunction() || v.getIdentifier() != null) {
                gc.setFill(color);
                gc.fillOval(x, y, style.dotRadius, style.dotRadius);
            }

            if (v.getIdentifier() != null) {
                gc.setFill(Color.BLACK);
                gc.fillOval(x, y, 0.5*style.dotRadius, 0.5*style.dotRadius);

                gc.setFont(style.font);
                gc.setStroke(Color.BLACK);
                gc.fillText(v.getIdentifier(), x + 1.5*style.dotRadius, y);
            }
        }
        this.group.getChildren().add(c);
        return true;
    }

    private double getX(double longitude) {
        return projector.getX(longitude) / projector.getX(geoBounds.getEast()) * pane.getWidth();
    }

//    private double getY(double latitude) {
//        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
//        return pane.getHeight() - ( projector.getY(latitude) / projector.getY(geoBounds.getNorth()) * pane.getHeight() );
//    }

//    private double getX(double longitude) {
//        return Math.abs(longitude - geoBounds.getWest()) / geoBounds.getWidth() * pane.getWidth();
//    }
//
    private double getY(double latitude) {
        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
        return Math.abs(latitude - geoBounds.getNorth()) / geoBounds.getHeight() * pane.getHeight();
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

    public GraphRendererStyle getStyle() {
        return this.style;
    }
}
