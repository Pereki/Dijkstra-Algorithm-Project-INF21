package view;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

public class GraphRenderer {

    // background & size
    private final Rectangle background;
    private final double height;
    private final double width;

    // all display elements
    private final Group group;
    private final ObservableList<Node> elements;

    // display layers
    private final Group layersContainerGroup;
    private final ObservableList<Node> layerList;

    // graphs
    private HashMap<String, Object> graphs;

    GraphRenderer(Group group, Rectangle background) {
        this.background = background;
        this.height = background.getHeight();
        this.width = background.getWidth();
        this.group = group;
        this.elements = group.getChildren();
        this.layersContainerGroup = new Group();
        this.elements.add(this.layersContainerGroup);
        this.layerList = this.layersContainerGroup.getChildren();
    }

    public boolean addGraphLayer(String key, Object graph, Color color) {
        if (this.graphs.containsKey(key))
            return false;
        this.graphs.put(key, graph);
        Group g = new Group();
        g.setId(key);

        // TODO: Implement the following code
        /*
        for (Edge e in graph):
            double xStart = e.start.latitude / maxLatitude * this.height
            double yStart = e.start.longitude / maxLongitude * this.width
            double xEnd = e.end.latitude / maxLatitude * this.height
            double yEnd = e.end.longitude / maxLongitude * this.width

            Line l = new Line(xStart, yStart, xEnd, yEnd);
            l.setStroke(color);

            // Possibly also add a circle for each vertex?

            g.add(l)
         */

        this.layerList.add(g);
        return true;
    }

    public Object removeGraphLayer(String key) {
        if (!this.graphs.containsKey(key))
            return null;
        this.graphs.remove(key);
        for (Node n : this.layerList) {
            if (n.getId().equals(key)) {
                this.layerList.remove(n);
                return n;
            }
        }
        return null;
    }

    public HashMap<String, Object> getGraphLayers() {
        return this.graphs;
    }

    public int getGraphLayerCount() {
        return this.graphs.size();
    }

    // TODO: Add methods to control the layers' order

}
