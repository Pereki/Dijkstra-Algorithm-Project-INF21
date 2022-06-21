package view;

import model.Graph;
import java.util.ArrayList;

public class ViewModel {

    private Graph map;
    private Graph route;

    private ArrayList<Object> listeners;

    public Graph getMap() {
        return map;
    }

    public void setMap(Graph map) {
        this.map = map;
    }

    public Graph getRoute() {
        return route;
    }

    public void setRoute(Graph route) {
        this.route = route;
    }
}
