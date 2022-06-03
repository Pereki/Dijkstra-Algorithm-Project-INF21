package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class HelloController implements Initializable {

    private final int ACCURACY = 1;

    @FXML
    private StackPane pane;
    @FXML
    private Group group;
    private ObservableList<Node> elements;
    private Group layersGroup;
    private ObservableList<Node> layers;

    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ProgressBar progressbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.elements = group.getChildren();
        this.layersGroup = new Group();
        this.layers = this.layersGroup.getChildren();
        this.elements.add(this.layersGroup);
    }

    @FXML
    protected void onDrawButtonClick() {
        progressbar.setProgress(0);

        Color c = colorPicker.getValue();

        double w = pane.getWidth();
        double h = pane.getHeight();

        double amplitude = Double.parseDouble(field1.getText());
        double frequency = Double.parseDouble(field2.getText());
        double yDisplacement = 0.5 * h;

        if (amplitude > 2*h) return;

        Function<Double, Double> f = (Double x) -> {
            return amplitude * Math.sin( x / frequency ) + yDisplacement;
        };

        this.layers.removeAll();
        for (int i = 0; i < w; i+=ACCURACY) {
            Line l = new Line(i, -f.apply((double) i), i+ACCURACY, -f.apply((double) i+ACCURACY));
            l.setStroke(c);
            this.layers.add(l);
            progressbar.setProgress(i / w);
        }
        progressbar.setProgress(1);
    }

    @FXML
    protected void onClearButtonClick() {
        this.layers.removeAll();
        this.layers.clear();
        progressbar.setProgress(0);
    }
}