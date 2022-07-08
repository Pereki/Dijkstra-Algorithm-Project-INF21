package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import model.Edge;
import model.Graph;
import model.Vertex;
import service.Dijkstra;
import service.SVGParser;
import service.XmlParser;
import view.GraphRenderer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private StackPane pane;
    @FXML
    private Group groupGraphs;
    @FXML
    private Group groupBackground;

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
        this.renderer = new GraphRenderer(groupGraphs, pane);

        File f = new File("src/main/resources/de.svg");
        List<List<SVGPath>> svgs;
        try {
            svgs = SVGParser.parse(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (SVGPath p : svgs.get(0)) {
            p.setFill(Paint.valueOf("#bfbfbf"));
            this.groupBackground.getChildren().add(p);
        }

        setZoomFactor(0.25);
    }

    @FXML
    protected void onDrawButtonClick() throws Exception {
        XmlParser parser = new XmlParser("test");
        parser.parseXmlToGraph();
        Graph finalGraph = parser.getGraph();
        ArrayList<Vertex> v = finalGraph.getVertexList();
        Vertex startingVertex = null;
        Vertex endingVertex = null;


        for(int i = 0; i < v.size(); i++){
            if(v.get(i).getIdentifier() == field1.getText()){
                startingVertex = v.get(i);
            }
            else if(v.get(i).getIdentifier() == field2.getText()){
                endingVertex = v.get(i);
            }
        }


        Graph shortestWay = Dijkstra.getShortWay(finalGraph, startingVertex, endingVertex);
        renderer.addGraphLayer("Urlaubsroute", shortestWay, Color.RED);
    }

    @FXML
    protected void onButtonZoomOutClick() {
        double scale = pane.getScaleX();
        scale *= 0.8;
        if (scale < 0.25) return;
        setZoomFactor(scale);
    }

    @FXML
    protected void onButtonZoomInClick() {
        double scale = pane.getScaleX();
        scale *= 1.25;
        if (scale > 1) return;
        setZoomFactor(scale);
    }

    protected void setZoomFactor(double factor) {
        pane.setScaleX(factor);
        pane.setScaleY(factor);
        pane.setScaleY(factor);
    }
}