package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.Edge;
import model.Graph;
import model.Vertex;
import view.GraphRenderer;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private StackPane pane;
    @FXML
    private Group group;

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ProgressBar progressbar;

    private GraphRenderer renderer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.renderer = new GraphRenderer(group, pane);
    }

    @FXML
    protected void onDrawButtonClick() {
        Graph g = new Graph();

        Vertex stuttgart = new Vertex(0, 48.800676, 9.143225);
        Vertex frankfurt = new Vertex(1, 50.102346, 8.703868);
        Vertex berlin = new Vertex(2, 52.503680, 13.480916);

        g.addVertex(stuttgart);
        g.addVertex(frankfurt);
        g.addVertex(berlin);

        g.addEdge(new Edge(stuttgart, frankfurt, 1));
        g.addEdge(new Edge(frankfurt, berlin,  2));

        renderer.addGraphLayer("Urlaubsroute", g, Color.RED);

        Graph g2 = new Graph();

        Vertex nuremberg = new Vertex(3, 49.424261, 11.124826);
        Vertex hamburg = new Vertex(4, 53.484564, 10.249799);

        g2.addVertex(nuremberg);
        g2.addVertex(hamburg);

        g2.addEdge(new Edge(nuremberg, hamburg,  3));

        renderer.addGraphLayer("Pendelroute", g2, Color.BLUE);
    }

    @FXML
    protected void onClearButtonClick() {

    }
}