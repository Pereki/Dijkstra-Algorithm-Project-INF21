package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Graph implements Serializable {
    private ArrayList<Edge> edgeList;
    private ArrayList<Vertex> vertexList;

    public Graph(){
        this.edgeList = new ArrayList<Edge>();
        this.vertexList = new ArrayList<Vertex>();
    }

    public Graph(ArrayList<Edge> edgesOfGraph){
        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        for (Edge edge: edgesOfGraph) {
            if (!vertexList.contains(edge.getStartingVertex())) {
                vertexList.add(edge.getStartingVertex());
            } else if (!vertexList.contains(edge.getEndingVertex())) {
                vertexList.add(edge.getEndingVertex());
            }
        }
        this.vertexList = vertexList;

        this.edgeList = edgesOfGraph;
    }

    public Graph(ArrayList<Edge> edgelist, ArrayList<Vertex> vertexList) throws Exception {

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

        this.vertexList = vertexList;
        this.edgeList = edgelist;
    }

    /**
     *
     * @param selectedVertex The Vertex to get the Options to go to
     * @return Returns a List of all the Edges connected to the Vertex
     */
    public ArrayList<Edge> getOptionsOfVertex(Vertex selectedVertex){
        ArrayList<Edge> optionsEdges = new ArrayList<Edge>();
        for (Edge edge: this.edgeList) {
            if(edge.getStartingVertex().equals(selectedVertex)||edge.getEndingVertex().equals(selectedVertex)){
                optionsEdges.add(edge);
            }
        }
        return optionsEdges;
    }

    public List<Vertex> getVertexList(){
        return vertexList;
    }

    public List<Edge> getEdgeList(){
        return edgeList;
    }

    public void addVertex(Vertex vertex){
        vertexList.add(vertex);
    }

    /**
     * Adds the Edge to the List and auto-adds the missing Vertexes
     * @param edge the Edge which will be added to the EdgeList
     */
    public void addEdge (Edge edge) {
        if(vertexList.contains(edge.getStartingVertex()) && vertexList.contains(edge.getEndingVertex())){
            edgeList.add(edge);
        }else if(!vertexList.contains(edge.getStartingVertex()) && vertexList.contains(edge.getEndingVertex())){
            vertexList.add(edge.getStartingVertex());
            edgeList.add(edge);
        }else if(vertexList.contains(edge.getStartingVertex()) && !vertexList.contains(edge.getEndingVertex())){
            vertexList.add(edge.getEndingVertex());
            edgeList.add(edge);
        }else if(!vertexList.contains(edge.getStartingVertex()) && !vertexList.contains(edge.getEndingVertex())) {
            vertexList.add(edge.getStartingVertex());
            vertexList.add(edge.getEndingVertex());
            edgeList.add(edge);
        }
    }

    public List<Edge> getEdges(Vertex vertex){
        List<Edge> edgesOfVertex = Collections.emptyList();
        for (Edge edge: edgeList) {
            if(edge.getStartingVertex()==vertex||edge.getEndingVertex()==vertex){
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }

    public Edge getEdge(Vertex v1, Vertex v2){
        for (Edge edge: edgeList) {
            if((v1.equals(edge.getStartingVertex())&&v2.equals(edge.getEndingVertex()))||
                    (v2.equals(edge.getStartingVertex())&&v1.equals(edge.getEndingVertex()))){
                return edge;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;
        Graph graph = (Graph) o;
        return Objects.equals(edgeList, graph.edgeList) && Objects.equals(getVertexList(), graph.getVertexList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(edgeList, getVertexList());
    }
}
