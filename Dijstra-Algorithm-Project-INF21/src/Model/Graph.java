package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph implements Serializable {
    private List<Edge> Edgelist;
    private List<Vertex> VertexList;

    public Graph(){
        this.Edgelist= Collections.emptyList();
        this.VertexList=Collections.emptyList();
    }

    public Graph(List<Edge> edgesOfGraph){
        List<Vertex> vertexList = Collections.emptyList();
        for (Edge edge: edgesOfGraph) {
            if (!vertexList.contains(edge.getStartingVertex())) {
                vertexList.add(edge.getStartingVertex());
            } else if (!vertexList.contains(edge.getEndingVertex())) {
                vertexList.add(edge.getEndingVertex());
            }
        }
        this.VertexList = vertexList;

        this.Edgelist = edgesOfGraph;
    }

    public Graph(List<Edge> edgelist, List<Vertex> vertexList) throws Exception {

        for (Edge edge: edgelist) {
            if (!vertexList.contains(edge.getStartingVertex())) {
                if (!vertexList.contains(edge.getEndingVertex())) {
                    throw new Exception("Vertex not included in Edgelist");
                }
            }
        }

        for (Vertex vertex: vertexList){
            List<Edge> edgesOfVertex = getEdges(vertex);
            for (Edge edge:edgesOfVertex) {
                if(!edgelist.contains(edge)){
                    throw new Exception("Edge not includded in Vertexlist");
                }
            }
        }

        this.VertexList = vertexList;
        this.Edgelist = edgelist;
    }

    public List<Edge> getOptionsOfVertex(Vertex selectedVertex){
        List<Edge> optionsEdges = Collections.emptyList();
        for (Edge edge: this.Edgelist) {
            if(edge.getStartingVertex().getId() == selectedVertex.getId()){
                optionsEdges.add(edge);
            }
        }
        return optionsEdges;
    }

    public List<Vertex> getVertexes(){
        return VertexList;
    }

    public List<Edge> getEdges(){
        return Edgelist;
    }

    public void addVertex(Vertex vertex){
        VertexList.add(vertex);
    }

    public void addEdge (Edge edge) {
        if(VertexList.contains(edge.getStartingVertex()) && VertexList.contains(edge.getEndingVertex())){
            Edgelist.add(edge);
        }else if(!VertexList.contains(edge.getStartingVertex()) && VertexList.contains(edge.getEndingVertex())){
            VertexList.add(edge.getStartingVertex());
            Edgelist.add(edge);
        }else if(VertexList.contains(edge.getStartingVertex()) && !VertexList.contains(edge.getEndingVertex())){
            VertexList.add(edge.getEndingVertex());
            Edgelist.add(edge);
        }else if(!VertexList.contains(edge.getStartingVertex()) && !VertexList.contains(edge.getEndingVertex())) {
            VertexList.add(edge.getStartingVertex());
            VertexList.add(edge.getEndingVertex());
            Edgelist.add(edge);
        }
    }

    public List<Edge> getEdges(Vertex vertex){
        List<Edge> edgesOfVertex = Collections.emptyList();
        for (Edge edge:Edgelist) {
            if(edge.getStartingVertex()==vertex||edge.getEndingVertex()==vertex){
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }

    public Edge getEdge(Vertex v1, Vertex v2){
        for (Edge edge: Edgelist) {
            if((v1.equals(edge.getStartingVertex())&&v2.equals(edge.getEndingVertex()))||
                    (v2.equals(edge.getStartingVertex())&&v1.equals(edge.getEndingVertex()))){
                return edge;
            }
        }
        return null;
    }


}
