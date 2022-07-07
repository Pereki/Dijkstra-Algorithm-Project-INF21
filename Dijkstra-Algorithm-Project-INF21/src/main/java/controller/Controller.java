package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import model.Edge;
import model.GeoBounds;
import model.Graph;
import model.Vertex;
import service.SVGParser;
import view.GraphRenderer;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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

        setZoomFactor(0.25);
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

        g.addEdge(new Edge(stuttgart, frankfurt, 0, 1));
        g.addEdge(new Edge(frankfurt, berlin, 1, 2));

        renderer.addGraphLayer("Urlaubsroute", g, Color.RED);

        Graph g2 = new Graph();

        Vertex nuremberg = new Vertex(3, 49.424261, 11.124826, null, "Nürnberg", true);
        Vertex hamburg = new Vertex(4, 53.484564, 10.249799, null, "Hamburg", true);

        g2.addVertex(nuremberg);
        g2.addVertex(hamburg);

        g2.addEdge(new Edge(nuremberg, hamburg, 0, 3));

        renderer.addGraphLayer("Pendelroute", g2, Color.YELLOW);
    }

    @FXML
    protected void onButtonZoomOutClick() {
        double scale = scrollpane.getContent().getScaleX();
        scale *= 0.8;
        if (scale < 0.25) return;
        setZoomFactor(scale);
    }

    @FXML
    protected void onButtonZoomInClick() {
        double scale = scrollpane.getContent().getScaleX();
        scale *= 1.25;
        if (scale > 1) return;
        setZoomFactor(scale);
    }

    protected void setZoomFactor(double factor) {
        scrollpane.getContent().setScaleX(factor);
        scrollpane.getContent().setScaleY(factor);
        scrollpane.getContent().setScaleY(factor);
    }
}