package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Returns the Edges of an Vertex
     * @param vertex the Vertex to get the Edges of
     * @return a Arraylist<Edge> containing the Edges
     */
    public ArrayList<Edge> getEdges(Vertex vertex){

        ArrayList<Edge> edgesOfVertex = new ArrayList<>();
        for (Edge edge: edgeList) {
            if(edge.getStartingVertex().equals(vertex)||edge.getEndingVertex().equals(vertex)){
                edgesOfVertex.add(edge);
            }
        }
        return edgesOfVertex;
    }

    /**
     * Returns the nearest Vertex, no matter of the Edges
     * @param vertexInput the Vertex you want to find the closest Vertex of
     * @return the nearest Vertex
     */
    public Vertex getNearestVertex(Vertex vertexInput){
        Vertex selectedVertex = null;
        for (Vertex vertex:vertexList) {
            double differenceLon;
            double differenceLat;
            double shortesdifference = 2000000;

            if(vertexInput.getLon() > vertex.getLon()) {
                differenceLon = vertexInput.getLon() - vertex.getLon();
            }else {
                differenceLon = vertex.getLon() - vertexInput.getLon();
            }

            if(vertexInput.getLat() > vertex.getLat()) {
                differenceLat = vertexInput.getLat() - vertex.getLat();
            } else {
                differenceLat = vertex.getLat() - vertexInput.getLat();
            }

            double currentdifference = Math.sqrt((differenceLat*differenceLat)+(differenceLon*differenceLon));
            if((selectedVertex == null || shortesdifference > currentdifference) && currentdifference != 0){
                selectedVertex = vertex;
                shortesdifference = currentdifference;
            }
        }
        return selectedVertex;
    }

    /**
     * Checks whether there is an edge crossing the edge e. If so, it creates a vertex at the intersection, deletes both edges and create four new ones with the intersection as one of the vertexes
     * @param e Edge which will be checked whether its crossing another edge or not
     */

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

            //Steigungen auf andere Seite
            eSteigung = eSteigung*-1;
            cSteigung = cSteigung*-1;

            //x-Achsenabschnitt von e berechnen
            eXAchsenKomponente = eSteigung*e1.getLon()+e1.getLat();

            //x-Achsenabschnitt von c berechnen
            cXAchsenKomponente = cSteigung*c1.getLon()+c1.getLat();

            double lon = (eXAchsenKomponente*1-cXAchsenKomponente*cSteigung)/(eSteigung*1-1*cSteigung);//Schnittpunkt berechnen
            double lat = (cXAchsenKomponente*eSteigung-eXAchsenKomponente*cSteigung)/(1*eSteigung-cSteigung*1);

            boolean inRange = false;

            if(e1.getLat()<e2.getLat()){
                if(lat>e1.getLat()&&lat<e2.getLat()){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }else{
                if(lat<e1.getLat()&&lat>e2.getLat()){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }

            if(e1.getLon()<e2.getLon()&&inRange){
                if(lon>e1.getLon()&&lon<e2.getLon()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }else{
                if(lon<e1.getLon()&&lon>e2.getLon()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }

            if(c1.getLat()<c2.getLat()&&inRange){
                if(lat>c1.getLat()&&lat<c2.getLat()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }else{
                if(lat<c1.getLat()&&lat>c2.getLat()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }

            if(c1.getLon()<c2.getLon()&&inRange){
                if(lon>c1.getLon()&&lon<c2.getLon()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }else{
                if(lon<c1.getLon()&&lon>c2.getLon()&&inRange){
                    inRange=true;
                }else{
                    inRange=false;
                }
            }


            if(inRange){//alte Edges löschen, vier neue + crossing Vertex hinzufügen
                edgeList.remove(e);
                edgeList.remove(compare);

                Vertex v = new Vertex(0,lat,lon);
                v.setCrossing(true);
                vertexList.add(v);

                Edge enew1 = new Edge(e1,v);
                Edge enew2 = new Edge(e2,v);
                Edge enew3 = new Edge(c1,v);
                Edge enew4 = new Edge(c2,v);

                edgeList.add(enew1);
                edgeList.add(enew2);
                edgeList.add(enew3);
                edgeList.add(enew4);

                break;
            }
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

     public void deleteEdge(Edge e){
        this.edgeList.remove(e);

    }

    public void deleteVertex(Vertex v){
        this.vertexList.remove(v);

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
