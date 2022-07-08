package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import model.*;
import service.SVGParser;
import view.GraphRenderer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final double MAX_ZOOM_LEVEL = 2.5;
    private static final double MIN_ZOOM_LEVEL = 0.25;
    private static final double ZOOM_IN_MULTIPLIER = 1.25;
    private static final double ZOOM_OUT_MULTIPLIER = 0.8;
    private static final String ROUTES_KEY = "ROUTES";
    private static final Color ROUTES_COLOR = Color.valueOf("#154889");
    private static final String BORDERS_KEY = "BORDERS";
    private static final Color BORDERS_COLOR = Color.valueOf("#bfbfbf");

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Group groupGraphs;

    private GraphRenderer renderer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // for Germany: W: 5.866342; E: 15.041892; N: 55.058307; S: 47.270112;
        this.renderer = new GraphRenderer(groupGraphs, new GeoBounds(
                5.866342, 15.041892, 55.058307, 47.270112
        ));

//        File f = new File("C:\\Users\\David\\OneDrive\\Dokumente\\Beruflich\\Duales Studium\\DH\\Vorlesungen\\2. Semester\\Programmieren\\Programmierprojekt\\Dijstra-Algorithm-Project-INF21\\Dijkstra-Algorithm-Project-INF21\\src\\main\\resources\\de.svg");
//        List<List<SVGPath>> svgs;
//        try {
//            svgs = SVGParser.parse(f);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        for (SVGPath p : svgs.get(0)) {
//            p.setFill(Paint.valueOf("#bfbfbf"));
//            this.groupBackground.getChildren().add(p);
//        }

        //setZoomFactor(0.25);
    }

    @FXML
    protected void onDrawButtonClick() {
        Graph g = new Graph();

        Vertex stuttgart = new Vertex(0, 48.800676, 9.143225, null, "Stuttgart", true);
        Vertex frankfurt = new Vertex(1, 50.102346, 8.703868, null, "Frankfurt", true);
        Vertex berlin = new Vertex(2, 52.503680, 13.480916, null, "Berlin", false);

        g.addVertex(stuttgart);
        g.addVertex(frankfurt);
        g.addVertex(berlin);

        g.addEdge(new Edge(stuttgart, frankfurt, 1));
        g.addEdge(new Edge(frankfurt, berlin,  2));

        Platform.runLater(() -> renderer.addGraphLayer("Urlaubsroute", g, Color.RED));

        Graph g2 = new Graph();

        Vertex nuremberg = new Vertex(3, 49.424261, 11.124826, null, "Nürnberg", true);
        Vertex hamburg = new Vertex(4, 53.484564, 10.249799, null, "Hamburg", true);

        g2.addVertex(frankfurt);
        g2.addVertex(nuremberg);
        g2.addVertex(hamburg);

        g2.addEdge(new Edge(frankfurt, nuremberg, 3));
        g2.addEdge(new Edge(nuremberg, hamburg, 3));

        Platform.runLater(() -> renderer.addGraphLayer("Pendelroute", g2, Color.CADETBLUE));
    }

    @FXML
    protected void onButtonZoomOutClick() {
        double scale = scrollpane.getContent().getScaleX();
        scale *= ZOOM_OUT_MULTIPLIER;
        if (scale < MIN_ZOOM_LEVEL) return;
        setZoomFactor(scale);
    }

    @FXML
    protected void onButtonZoomInClick() {
        double scale = scrollpane.getContent().getScaleX();
        scale *= ZOOM_IN_MULTIPLIER;
        if (scale > MAX_ZOOM_LEVEL) return;
        setZoomFactor(scale);
    }

    protected void setZoomFactor(double factor) {
        scrollpane.getContent().setScaleX(factor);
        scrollpane.getContent().setScaleY(factor);
        scrollpane.getContent().setScaleY(factor);
    }

    @FXML
    protected void onMenuButtonContributorsClick() {
        String text = "Lukas Burkhardt (Algorithmus) # Pascal Fuchs (Datenstruktur) # Ruben Kraft (API) # Paul Lehmann (SVG Parser) # David Maier (GUI)";
        Alert alert = new Alert(Alert.AlertType.NONE, text.replaceAll(" # ", "\n"), ButtonType.CLOSE);
        alert.setTitle("Mitwirkende");
        alert.showAndWait();
    }

    @FXML
    protected void onMenuButtonMapExportClick() {
        Graph graph = getBordersGraph();
        if (graph == null) {
            String text = "Es ist keine Karte geladen.";
            Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.CLOSE);
            alert.setTitle("Fehler");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Karte speichern unter");
        File file = fileChooser.showSaveDialog(scrollpane.getScene().getWindow());

        Platform.runLater(() -> {
            try {
                SerializeService.saveGraph(graph, file.getAbsolutePath());
                setBordersGraph(graph);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    protected void onMenuButtonMapImportClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Karte öffnen");
        File file = fileChooser.showOpenDialog(scrollpane.getScene().getWindow());

        Platform.runLater(() -> {
            try {
                Graph graph = SerializeService.loadGraph(file.getAbsolutePath());
                setBordersGraph(graph);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    protected void onMenuButtonRouteExportClick() {

    }

    @FXML
    protected void onMenuButtonRouteImportClick() {

    }

    @FXML
    protected void onMenuButtonOsmImportClick() {

    }

    protected void setBordersGraph(Graph graph) {
        Platform.runLater(() -> {
            renderer.removeGraphLayer(BORDERS_KEY);
            renderer.addGraphLayer(BORDERS_KEY, graph, BORDERS_COLOR);
        });
    }

    protected Graph getBordersGraph() {
        return renderer.getGraphLayer(BORDERS_KEY);
    }

    protected void setRoadsGraph(Graph graph) {
        Platform.runLater(() -> {
            renderer.removeGraphLayer(ROUTES_KEY);
            renderer.addGraphLayer(ROUTES_KEY, graph, ROUTES_COLOR);
        });
    }

    protected Graph getRoadsGraph() {
        return renderer.getGraphLayer(ROUTES_KEY);
    }
}