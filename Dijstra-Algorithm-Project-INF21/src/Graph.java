import java.util.ArrayList;

public class Graph {
    private ArrayList<Edge> Edgelist;

    public Graph(ArrayList<Edge> edgesOfGraph){
        this.Edgelist = edgesOfGraph;
    }

    public  ArrayList<Edge> getOptionsOfVertex(Vertex selectedVertex){

        ArrayList<Edge> optionsEdges = new ArrayList<Edge>();
        for (Edge edge: this.Edgelist) {
            if(edge.getStartingVertex().getId() == selectedVertex.getId()){
                optionsEdges.add(edge);
            }
        }
        return optionsEdges;
    }

}
