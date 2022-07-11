package view;

import com.almasb.fxgl.entity.level.tiled.Layer;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Edge;
import model.GeoBounds;
import model.Graph;
import model.Vertex;
import service.MercatorProjector;

import java.util.HashMap;
import java.util.List;

public class GraphRenderer {

    // display properties
    private final GeoBounds geoBounds;

    /**
     * Creates a new {@code GraphRenderer}
     * @param geoBounds A {@code GeoBounds} object which specifies the geographical viewport.
     */
    public GraphRenderer(GeoBounds geoBounds) {
        this.geoBounds = geoBounds;
    }

    public void render(GraphLayer layer, Canvas canvas) {
        Graph graph = layer.getGraph();
        GraphRendererOptions options = layer.getOptions();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setStroke(Color.GRAY);
        gc.strokeRect(0, 0, w, h);

        // draw edges
        if (options.isFillShape()) {
            gc.beginPath();
        }

        for (Edge e : graph.getEdgeList()) {
            Vertex start = e.getStartingVertex();
            double xStart = this.getX(start.getLon(), w);
            double yStart = this.getY(start.getLat(), h);

            Vertex end = e.getEndingVertex();
            double xEnd = this.getX(end.getLon(), w);
            double yEnd = this.getY(end.getLat(), h);

            if (options.isFillShape()) {
                gc.setFill(options.getRouteColor());
                gc.moveTo(xStart, yStart);
                gc.lineTo(xEnd, yEnd);
            } else {
                gc.setStroke(options.getRouteColor());
                gc.setLineWidth(options.getLineWidth());
                gc.strokeLine(xStart, yStart, xEnd, yEnd);
            }
        }

        if (options.isFillShape()) {
            gc.fill();
            gc.closePath();
        }

        // draw vertices
        for (Vertex v : graph.getVertexList()) {
            double x = this.getX(v.getLon(), w);
            double y = this.getY(v.getLat(), h);

            // draw dot on junctions and cities
            if (v.getJunction() && options.isDotJunctions()) {
                gc.setFill(options.getRouteColor());
                gc.fillOval(
                        x - 0.5 * options.getDotRadius(),
                        y - 0.5 * options.getDotRadius(),
                        options.getDotRadius(),
                        options.getDotRadius()
                );
            }

            // draw labels
            if (v.getIdentifier() != null && options.isShowLabels()) {
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
    }

    /**
     * Renders the given {@code GraphLayer} onto the given canvas.
     * @param layer The {@code GraphLayer} to be rendered.
     * @param canvas A {@code Canvas} on which the layer should be rendered.
     * @param geoBounds The geographical viewport.
     */
    public static void render(GraphLayer layer, Canvas canvas, GeoBounds geoBounds) {
        new GraphRenderer(geoBounds).render(layer, canvas);
    }

    private double getX(double longitude, double width) {
        return Math.abs(longitude / geoBounds.getEast()) * width;
    }

    private double getY(double latitude, double height) {
        //latitude = Math.toDegrees(Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2)));
        return Math.abs(latitude - geoBounds.getNorth()) / geoBounds.getHeight() * height;
    }
}
