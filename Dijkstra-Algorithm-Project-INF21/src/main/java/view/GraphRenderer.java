package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
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
    private final ObservableList<Node> elements;

    // display layers
    private final Group layersContainerGroup;
    private final ObservableList<Node> layerList;

    // display properties
    // for Germany: W: 5.866342; E: 15.041892; N: 55.058307; S: 47.270112;
    private GraphRendererBounds geoBounds;

    // graphs
    private HashMap<String, Graph> graphs = new HashMap<>();

    // styling
    private GraphRendererStyle style = new GraphRendererStyle(
            4, 8, new Font(24)
    );

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
        this.elements = group.getChildren();
        this.layersContainerGroup = new Group();
        this.elements.add(this.layersContainerGroup);
        this.layerList = this.layersContainerGroup.getChildren();
        this.setViewBounds(5.866342, 15.041892, 55.058307, 47.270112);
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

        double centerLat = 0.5 * (geoBounds.getNorth() + geoBounds.getSouth());
        double centerLon = 0.5 * (geoBounds.getWest() + geoBounds.getEast());
        MercatorProjector projector = new MercatorProjector(centerLat, centerLon);

        for (Edge e : graph.getEdgeList()) {
            Vertex start = e.getStartingVertex();
            CoordinatePair cStart = projector.project(start.getLat(), start.getLon());
            double xStart = this.getX(cStart.getLongitude());
            double yStart = this.getY(cStart.getLatitude());

            Vertex end = e.getEndingVertex();
            CoordinatePair cEnd = projector.project(end.getLat(), end.getLon());
            double xEnd = this.getX(cEnd.getLongitude());
            double yEnd = this.getY(cEnd.getLatitude());

            Line l = new Line(xStart, yStart, xEnd, yEnd);
            l.setStrokeWidth(style.lineWidth);
            l.setStroke(color);
            g.getChildren().add(l);
        }

        for (Vertex v : graph.getVertexList()) {
            CoordinatePair cp = projector.project(v.getLat(), v.getLon());
            double x = this.getX(cp.getLongitude());
            double y = this.getY(cp.getLatitude());

            if (v.getJunction() || v.getIdentifier() != null) {
                Circle c = new Circle(x, y, style.dotRadius);
                c.setFill(color);
                g.getChildren().add(c);
            }

            if (v.getIdentifier() != null) {
                Circle csm = new Circle(x, y, 0.5*style.dotRadius);
                csm.setFill(Color.BLACK);
                g.getChildren().add(csm);

                Text t = new Text(x + 1.5*style.dotRadius, y, v.getIdentifier());
                t.setFont(style.font);
                t.setStroke(Color.BLACK);
                g.getChildren().add(t);
            }
        }
        this.layerList.add(g);
        return true;
    }

//    private double getX(double longitude) {
//        return Math.abs(longitude - geoBounds.getWest()) / geoBounds.getWidth() * pane.getWidth();
//    }
//
//    private double getY(double latitude) {
//        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
//        return Math.abs(latitude - geoBounds.getNorth()) / geoBounds.getHeight() * pane.getHeight();
//    }

    private double getX(double longitude) {
        return Math.abs(longitude - geoBounds.getWest()) / geoBounds.getWidth() * pane.getWidth();
    }

    private double getY(double latitude) {
        latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
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

    public GraphRendererStyle getStyle() {
        return this.style;
    }
}
