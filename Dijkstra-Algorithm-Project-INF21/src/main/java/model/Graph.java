package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graph implements Serializable {
    private final ArrayList<Edge> edgeList;
    private final ArrayList<Vertex> vertexList;

    public Graph(){
        this.edgeList = new ArrayList<>();
        this.vertexList = new ArrayList<>();
    }
    
    public Graph(ArrayList<Edge> edgesOfGraph){
        ArrayList<Vertex> vertexList = new ArrayList<>();
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

        this.vertexList = vertexList;
        this.edgeList = edgelist;

        for (Edge edge: edgelist) {
            if (!vertexList.contains(edge.getStartingVertex())) {
                if (!vertexList.contains(edge.getEndingVertex())) {
                    throw new Exception("Vertex not included in Edgelist");
                }
            }
        }

        for (Vertex vertex: vertexList){
            ArrayList<Edge> edgesOfVertex = getEdges(vertex);
            for (Edge edge:edgesOfVertex) {
                if(!edgelist.contains(edge)){
                    throw new Exception("Edge not includded in Vertexlist");
                }
            }
        }


    }

    /**
     *
     * @param selectedVertex The Vertex to get the Options to go to
     * @return Returns a List of all the Edges connected to the Vertex
     */
    public ArrayList<Edge> getOptionsOfVertex(Vertex selectedVertex){
        ArrayList<Edge> optionsEdges = new ArrayList<>();
        for (Edge edge: this.edgeList) {
            if(edge.getStartingVertex().equals(selectedVertex)||edge.getEndingVertex().equals(selectedVertex)){
                optionsEdges.add(edge);
            }
        }
        return optionsEdges;
    }

    public ArrayList<Vertex> getVertexList(){
        return vertexList;
    }

    public ArrayList<Edge> getEdgeList(){
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

    public ArrayList<Edge> getEdges(Vertex vertex){
        ArrayList<Edge> edgesOfVertex = new ArrayList<>();
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

    public Vertex getNearestVertex(Vertex vertexInput){
        Vertex found = vertexList.get(0);
        for (Vertex vertex:vertexList) {
            double diffLon;
            double diffLat;
            if(vertexInput.getLon() > vertex.getLon()) {
                diffLon = vertexInput.getLon() - vertex.getLon();
            }else {
                 diffLon = vertex.getLon() - vertexInput.getLon();
            }
            if(vertexInput.getLat() > vertex.getLat()) {
                diffLat = vertexInput.getLat() - vertex.getLat();
            } else {
                diffLat = vertex.getLat() - vertexInput.getLat();
            }
            double diffLonFound;
            double diffLatFound;
            if(vertexInput.getLon() > found.getLon()) {
                diffLonFound = vertexInput.getLon() - found.getLon();
            }else {
                diffLonFound = found.getLon() - vertexInput.getLon();
            }
            if(vertexInput.getLat() > found.getLat()) {
                diffLatFound = vertexInput.getLat() - found.getLat();
            } else {
                diffLatFound = found.getLat() - vertexInput.getLat();
            }

            if((diffLat+diffLon)<(diffLatFound+diffLonFound)){
                found = vertex;
            }
        }
        return found;
    }

    public void createCrossingIfNeeded(Edge e){
        for(int i=0;i< edgeList.size();i++){
            Edge compare = edgeList.get(i);

            Vertex e1 = e.getStartingVertex();
            Vertex e2 = e.getEndingVertex();

            Vertex c1 = compare.getStartingVertex();
            Vertex c2 = compare.getEndingVertex();

            boolean e1First;
            boolean c1First;

            double eSteigung;
            double cSteigung;
            double eXAchsenKomponente;
            double cXAchsenKomponente;

            if(e1.getLat()<e2.getLat()){
                e1First = true;
            }else{
                e1First = false;
            }

            if(c1.getLat()<c2.getLat()){
                c1First = true;
            }else{
                c1First = false;
            }

            //Steigung von e berechnen
            if(e1First){
                eSteigung = (e2.getLat()-e1.getLat())/ (e2.getLon()-e1.getLon());
            }else{
                eSteigung = (e1.getLat()-e2.getLat())/ (e1.getLon()-e2.getLon());
            }

            //Steigung von c berechnen
            if(c1First){
                cSteigung = (c2.getLat()-c1.getLat())/ (c2.getLon()-c1.getLon());
            }else{
                cSteigung = (c1.getLat()-c2.getLat())/ (c1.getLon()-c2.getLon());
            }

            //x-Achsenabschnitt von e berechnen
            eXAchsenKomponente = -1*eSteigung*e1.getLon()+e1.getLat();

            //x-Achsenabschnitt von c berechnen
            cXAchsenKomponente = -1*cSteigung*c1.getLon()+c1.getLat();
        }
    }

    public boolean hasVertex(Vertex v){
        for (Vertex vertex:vertexList) {
            if (vertex.equals(v))return true;
        }
        return false;
    }

    public boolean hasEdge(Edge e){
        for (Edge edge:edgeList) {
            if (edge.equals(e))return true;
        }
        return false;
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
