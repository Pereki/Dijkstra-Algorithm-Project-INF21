import java.util.ArrayList;
import java.util.List;

public class Graph {
    private ArrayList<Edge> Edgelist;
    private List<Vertex> VertexList;

    public Graph(){
        this.Edgelist=null;
        this.VertexList=null;
    }

    public Graph(ArrayList<Edge> edgesOfGraph, List<Vertex> vertexList){
        this.VertexList = vertexList;
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

    public List<Vertex> getVertex(){
        return VertexList;
    }

    public List<Edge> getEdges(){
        return Edgelist;
    }

    public void addVertex(Vertex vertex){
        VertexList.add(vertex);
    }

    public void addEdge (Edge edge){
        if(VertexList.contains(edge.getStartingVertex()) && VertexList.contains(edge.getEndingVertex())){
            Edgelist.add(edge);
        }
    }

    public List<Edge> getEdges(Vertex vertex){
        List<Edge> edgesOfVertex = new ArrayList<Edge>();
        for (Edge edge:Edgelist) {
            if(edge.getStartingVertex()==vertex||edge.getEndingVertex()==vertex){
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }


}
