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

    private final MercatorProjector projector;

    /**
     * Creates a new {@code GraphRenderer}
     * @param geoBounds A {@code GeoBounds} object which specifies the geographical viewport.
     */
    public GraphRenderer(GeoBounds geoBounds) {
        this.geoBounds = geoBounds;
        this.projector = new MercatorProjector(geoBounds.getSouth(), geoBounds.getWest());
    }

    public Canvas render(GraphLayer layer) {
        Canvas canvas = new Canvas(
                projector.getX(geoBounds.getEast()) * 3000,
                projector.getY(geoBounds.getNorth()) * 500
        );
        Graph graph = layer.getGraph();
        GraphRendererOptions options = layer.getOptions();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(20);
        gc.strokeRect(0, 0, w, h);

        // draw edges
        for (Edge e : graph.getEdgeList()) {
            Vertex start = e.getStartingVertex();
            double xStart = this.getX(start.getLon(), w);
            double yStart = this.getY(start.getLat(), h);

            Vertex end = e.getEndingVertex();
            double xEnd = this.getX(end.getLon(), w);
            double yEnd = this.getY(end.getLat(), h);

            gc.setStroke(options.getRouteColor());
            gc.setLineWidth(options.getLineWidth());
            gc.strokeLine(xStart, yStart, xEnd, yEnd);
        }

        // draw vertices
        for (Vertex v : graph.getVertexList()) {
            double x = this.getX(v.getLon(), w);
            double y = this.getY(v.getLat(), h);

            boolean dotJunction = options.isDotJunctions()
                    || options.getShowLabelsEquals().contains(v.getIdentifier());

            // draw dot on junctions and cities
            if (v.getJunction() && dotJunction) {
                gc.setFill(options.getRouteColor());
                gc.fillOval(
                        x - 0.5 * options.getDotRadius(),
                        y - 0.5 * options.getDotRadius(),
                        options.getDotRadius(),
                        options.getDotRadius()
                );
            }

            boolean drawLabel = options.isShowLabels()
                    || options.getShowLabelsEquals().contains(v.getIdentifier());

            // draw labels
            if (v.getIdentifier() != null && drawLabel) {
                gc.setFill(options.getLabelColor());
                gc.fillOval(
                        x - 0.25 * options.getDotRadius(),
                        y - 0.25 * options.getDotRadius(),
                        0.5 * options.getDotRadius(),
                        0.5 * options.getDotRadius()
                );

                gc.setFont(options.getFont());
                Color color = options.getLabelColor();
                gc.setFill(new Color(1-color.getRed(), 1-color.getGreen(), 1-color.getBlue(), 1));
                gc.fillText(v.getIdentifier(), x + 1.5 * options.getDotRadius()+1, y+1);
                gc.setFill(color);
                gc.fillText(v.getIdentifier(), x + 1.5 * options.getDotRadius(), y);
            }
        }
        return canvas;
    }

    /**
     * Renders the given {@code GraphLayer} onto the given canvas.
     * @param layer The {@code GraphLayer} to be rendered.
     * @param geoBounds The geographical viewport.
     */
    public static Canvas render(GraphLayer layer, GeoBounds geoBounds) {
        return new GraphRenderer(geoBounds).render(layer);
    }

    private double getX(double longitude, double width) {
        return projector.getX(longitude) / projector.getX(geoBounds.getEast()) * width;
    }

    private double getY(double latitude, double height) {
        double geoHeight = projector.getY(geoBounds.getNorth()) - projector.getY(geoBounds.getSouth());
        double yFromBottom = projector.getY(latitude) - projector.getY(geoBounds.getSouth());
        double yFromTop = geoHeight - yFromBottom;
        return yFromTop / geoHeight * height;
    }
}
