package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;
import service.*;
import view.GraphDisplay;
import view.GraphLayer;
import view.GraphRendererOptions;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public class Controller implements Initializable {

    private static final double MAX_ZOOM_LEVEL = 1.5;
    private static final double MIN_ZOOM_LEVEL = 0.25;
    private static final double ZOOM_IN_MULTIPLIER = 1.25;
    private static final double ZOOM_OUT_MULTIPLIER = 0.8;

    private static final int BORDERS_POS = 0;
    private static final GraphRendererOptions BORDERS_OPTIONS = new GraphRendererOptions()
            .routeColor(Color.valueOf("#bfbfbf"))
            .dotJunctions(false)
            .showLabels(false);

    private static final int ROADS_POS = 1;
    private static final GraphRendererOptions ROADS_OPTIONS = new GraphRendererOptions()
            .routeColor(Color.valueOf("#154889"))
            .dotJunctions(false)
            .showLabels(false);

    private static final int ROUTE_POS = 2;
    private static final GraphRendererOptions ROUTE_OPTIONS = new GraphRendererOptions()
            .routeColor(Color.RED)
            .dotJunctions(false)
            .showLabels(false);

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Group groupGraphs;
    @FXML
    private ComboBox<String> inputStart;
    @FXML
    private ComboBox<String> inputDestination;

    private HashMap<String, Vertex> junctions = new HashMap<>();

    private GraphDisplay display;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // for Germany: W: 5.866342; E: 15.041892; N: 55.058307; S: 47.270112;
        this.display = new GraphDisplay(groupGraphs, new GeoBounds(
                5.866342, 15.041892, 55.058307, 47.270112
        ));
    }

    // FIND ROUTE

    @FXML
    protected void onCalculateButtonClick() {
        if (junctions.isEmpty()) {
            showError("Es muss zuerst ein Straßennetz geladen werden.");
            return;
        }

        Vertex start = junctions.get(inputStart.getValue());
        Vertex dest = junctions.get(inputDestination.getValue());

        if (start == null) {
            showError("Der angegebene Startort existiert im geladenen Straßennetz nicht.");
            return;
        }
        if (dest == null) {
            showError("Der angegebene Zielort existiert im geladenen Straßennetz nicht.");
            return;
        }

        Platform.runLater(() -> {
            GraphWay route;
            double apiLength;
            try {
                route = Dijkstra.getShortWay(getRoadsGraph(), start, dest);
                if (route == null) {
                    showError(String.format("Es konnte keine Route von %s nach %s berechnet werden.", start.getIdentifier(), dest.getIdentifier()));
                }
                apiLength = new OpenMapRequester().getDistance(start, dest) / 1000;
            } catch (Exception e) {
                e.printStackTrace();
                showError(String.format("Beim Berechnen der Route ist ein Fehler aufgetreten: %s", e.getLocalizedMessage()));
                return;
            }
            setRouteGraph(route);
            String message = "Es wurde eine Route gefunden!\n" +
                    String.format("Länge: %.2f km\n", route.getLength()) +
                    String.format("Länge der Route von Openrouteservice: %.2f km", apiLength);
            Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
            alert.setTitle("Route gefunden");
            alert.show();
        });
    }

    // TEXT FIELDS

    @FXML
    protected void onInputStartKeyEvent(KeyEvent e) {
        displaySuggestions(inputStart);
    }

    @FXML
    protected void onInputDestinationKeyEvent(KeyEvent e) {
        displaySuggestions(inputDestination);
    }

    private void displaySuggestions(ComboBox input) {
        String text = input.getEditor().getText();
        ObservableList<String> matches = FXCollections.observableList(findMatches(text));
        input.setItems(matches);
        if (matches.size() > 0) {
            input.show();
        } else {
            input.hide();
        }
    }

    private List<String> findMatches(String text) {
        List<String> matches = new ArrayList<>();
        for (String key : junctions.keySet()) {
            if (key.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                matches.add(key);
            }
        }
        return matches;
    }

    // ZOOMING

    @FXML
    protected void onButtonZoomOutClick() {
        zoomOut();
    }

    private void zoomOut() {
        double scale = scrollpane.getContent().getScaleX();
        scale = scale * ZOOM_OUT_MULTIPLIER;
        if (scale < MIN_ZOOM_LEVEL) return;
        setZoomFactor(scale);
    }

    @FXML
    protected void onButtonZoomInClick() {
        zoomIn();
    }

    private void zoomIn() {
        double scale = scrollpane.getContent().getScaleX();
        scale = scale * ZOOM_IN_MULTIPLIER;
        if (scale > MAX_ZOOM_LEVEL) return;
        setZoomFactor(scale);
    }

    protected void setZoomFactor(double factor) {
//        Scale scale = new Scale(factor, factor, 0, 0);
//        scrollpane.getContent().getTransforms().add(scale);
        double width = display.getCanvas().getWidth() * factor;
        double height = display.getCanvas().getHeight() * factor;
        scrollpane.setVmax(height);
        scrollpane.setHmax(width);
        System.out.println(factor);
//        scrollpane.setHmax(factor);
//        scrollpane.setVmax(factor);
        scrollpane.getContent().setScaleX(factor);
        scrollpane.getContent().setScaleY(factor);
        scrollpane.getContent().setScaleY(factor);
    }

    // MENUBAR BUTTONS

    // about

    @FXML
    protected void onMenuButtonContributorsClick() {
        StringBuilder text = new StringBuilder();
        InputStream stream = ResourceLoader.get("credits.txt");
        if (stream == null) return;
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            text.append("\n").append(scanner.nextLine());
        }
        Alert alert = new Alert(Alert.AlertType.NONE, text.toString(), ButtonType.CLOSE);
        alert.setTitle("Credits");
        alert.showAndWait();
    }

    // file

    /**
     * Loads a {@code Graph} object from a graph file selected by the user.
     * @param callback A function to be called with the loaded {@code Graph}.
     * @param title Title of the window.
     * @param loadingError Text to be displayed when the file couldn't be loaded.
     */
    private void importGraph(
            Consumer<Graph> callback,
            String title,
            String loadingError
    ) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showOpenDialog(scrollpane.getScene().getWindow());
        if (file == null) {
            return;
        }
        Platform.runLater(() -> {
            try {
                Graph graph = SerializeService.loadGraph(file.getAbsolutePath());
                callback.accept(graph);
            } catch (IOException | ClassNotFoundException e) {
                showError(loadingError);
                e.printStackTrace();
            }
        });
    }

    /**
     * Saves a {@code Graph} object to a graph file selected by the user.
     * @param graph The {@code Graph} object to be saved.
     * @param title Title of the window.
     * @param noneLoaded Text to be displayed if graph is {@code null}.
     * @param savingError Text to be displayed when the file couldn't be saved.
     */
    private void exportGraph(
            Graph graph,
            String title,
            String noneLoaded,
            String savingError
    ) {
        if (graph == null) {
            showError(noneLoaded);
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showSaveDialog(scrollpane.getScene().getWindow());
        if (file == null) {
            return;
        }
        Platform.runLater(() -> {
            try {
                SerializeService.saveGraph(graph, file.getAbsolutePath());
            } catch (IOException e) {
                showError(savingError);
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates a {@code Graph} object from an OSM-file selected by the user.
     * @param callback A function to be called with the imported {@code Graph}.
     * @param title Title of the window.
     */
    private void importOSM(
            Consumer<Graph> callback,
            String title
    ) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        File file = fileChooser.showOpenDialog(scrollpane.getScene().getWindow());
        if (file == null) {
            return;
        }
        if (!file.getName().toLowerCase().endsWith(".osm")) {
            String text = "Die ausgewählte Datei scheint keine OSM-Datei zu sein. Sicher, dass sie geladen werden soll?";
            Alert alert = new Alert(Alert.AlertType.WARNING, text, ButtonType.NO, ButtonType.YES);
            alert.setTitle("Warnung");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get() == ButtonType.NO) {
                return;
            }
        }
        Platform.runLater(() -> {
            XmlParser p = new XmlParser(file.getAbsolutePath());
            Graph g = p.getGraph();
            callback.accept(g);
        });
    }

    // file: borders

    @FXML
    protected void onMenuButtonBordersExportClick() {
        exportGraph(
                getBordersGraph(),
                "Ländergrenzen speichern unter",
                "Es sind keine Ländergrenzen geladen.",
                "Die Ländergrenzen konnten nicht exportiert werden."
        );
    }

    @FXML
    protected void onMenuButtonBordersImportClick() {
        importGraph(
                this::setBordersGraph,
                "Ländergrenzen öffnen",
                "Die angegebene Datei konnte nicht als Ländergrenzen geladen werden."
        );
    }

    // file: roads

    @FXML
    protected void onMenuButtonRoadsExportClick() {
        exportGraph(
                getRoadsGraph(),
                "Straßennetz speichern unter",
                "Es ist kein Straßennetz geladen.",
                "Das Straßennetz konnte nicht exportiert werden."
        );
    }

    @FXML
    protected void onMenuButtonRoadsImportClick() {
        importGraph(
                this::setRoadsGraph,
                "Straßennetz öffnen",
                "Die angegebene Datei konnte nicht als Straßennetz geladen werden."
        );
    }

    // file: OSM

    @FXML
    protected void onMenuButtonOsmBordersImportClick() {
        importOSM(
                this::setBordersGraph,
                "OSM-Datei mit Ländergrenzen öffnen"
        );
    }

    @FXML
    protected void onMenuButtonOsmRoadsImportClick() {
        importOSM(
                this::setRoadsGraph,
                "OSM-Datei mit Straßennetz öffnen"
        );
    }

    // LAYER GETTERS/SETTERS

    // borders

    protected void setBordersGraph(Graph graph) {
        Platform.runLater(() -> {
            display.setGraphLayer(BORDERS_POS, new GraphLayer(graph, BORDERS_OPTIONS));
        });
    }

    protected Graph getBordersGraph() {
        return display.getGraphLayer(BORDERS_POS).getGraph();
    }

    // roads

    public void setRoadsGraph(Graph graph) {
        Platform.runLater(() -> {
            setJunctions(graph.getVertexList());
            display.setGraphLayer(ROADS_POS, new GraphLayer(graph, ROADS_OPTIONS));
        });
    }

    protected Graph getRoadsGraph() {
        return display.getGraphLayer(ROADS_POS).getGraph();
    }

    // route

    protected void setRouteGraph(GraphWay graph) {
        Platform.runLater(() -> {
            display.setGraphLayer(ROUTE_POS, new GraphLayer(graph.getGraph(), ROUTE_OPTIONS
                    .showLabelsEquals(
                            graph.getStartVertex().getIdentifier(),
                            graph.getEndVertex().getIdentifier()
                    )));
            ROUTE_OPTIONS.setShowLabelsEquals(new HashSet<>());
        });
    }

    protected Graph getRouteGraph() {
        return display.getGraphLayer(ROUTE_POS).getGraph();
    }

    // MISCELLANEOUS

    private void setJunctions(List<Vertex> vertices) {
        this.junctions = new HashMap<>();
        GeoBounds bounds = new GeoBounds(Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MAX_VALUE);
        for (Vertex v : vertices) {

            // expand viewport if necessary
            if (v.getLat() < bounds.getNorth()) bounds.setNorth(v.getLat());
            if (v.getLat() > bounds.getSouth()) bounds.setSouth(v.getLat());
            if (v.getLon() < bounds.getWest()) bounds.setWest(v.getLon());
            if (v.getLon() > bounds.getEast()) bounds.setEast(v.getLon());

            // sorry :P
            List<String> forbidden = List.of(
                    "",
                    "kreuz",
                    "rasthof",
                    "dreieck",
                    "kreuzung",
                    "rastplatz",
                    "darmstädter",
                    "autobahnkreuz",
                    "autobahndreieck"
            );

            if (v.getJunction() && v.getIdentifier() != null && !forbidden.contains(v.getIdentifier())) {
                junctions.put(v.getIdentifier(), v);
                inputStart.getItems().add(v.getIdentifier());
                inputDestination.getItems().add(v.getIdentifier());
            }
        }
        //display.setGeoBounds(bounds);
    }

    private void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.CLOSE);
        alert.setTitle("Fehler");
        alert.showAndWait();
    }
}