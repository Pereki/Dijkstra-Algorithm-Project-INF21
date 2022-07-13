package view;

import model.Graph;

public class GraphLayer {
    private Graph graph;
    private GraphRendererOptions options;

    public GraphLayer(Graph graph, GraphRendererOptions options) {
        this.graph = graph;
        this.options = options;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public GraphRendererOptions getOptions() {
        return options;
    }

    public void setOptions(GraphRendererOptions options) {
        this.options = options;
    }
}
