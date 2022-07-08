package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a single way in a Graph with start and end Vertex and the length of the complete way.
 * @author i21005
 */
public class GraphWay {
    private final Graph wayGraph;
    private final Vertex startVertex;
    private Vertex endVertex;
    private Edge lastEdge;
    private double length;

    /**
     * Constructor for a Way with only a single Vertex
     * @param vertex the only Vertex in this Way.
     */
    public GraphWay(Vertex vertex){
        this.startVertex = vertex;
        this.endVertex = vertex;
        this.length=0;
        this.wayGraph = new Graph();
        this.lastEdge = null;
        wayGraph.addVertex(vertex);
    }

    /**
     * Returns a new Way.
     * @param start the first Vertex at teh beginning of the Way
     * @param first the first Edge oft the Way connected to the start Vertex.
     */
    public GraphWay(Vertex start, Edge first){
        this.startVertex = start;
        //this.endVertex = first.getOtherVertex(start);
        this.length = first.getLength();
        this.wayGraph = new Graph();
        this.lastEdge = first;
        wayGraph.addVertex(startVertex);
        wayGraph.addVertex(endVertex);
        wayGraph.addEdge(first);
    }

    /**
     * Constructor to make a new Way on base of another one.
     * @param graphWay the Way the new is based on.
     * @param edge the new Way.
     */
    public GraphWay(GraphWay graphWay, Edge edge) throws Exception {
        this.startVertex = graphWay.startVertex;
        this.endVertex = graphWay.endVertex;
        this.length = graphWay.length;
        this.wayGraph = new Graph((ArrayList<Edge>) graphWay.getGraph().getEdgeList().clone(),(ArrayList<Vertex>) graphWay.getGraph().getVertexList().clone()); //use of clone() to force a copy by value not a copy by reference.
        this.lastEdge = graphWay.lastEdge;
        addEdge(edge);
    }

    /**
     * Add an Edge to the Way.
     * @param edge the Edge to add to the Way.
     */
    public void addEdge(Edge edge){
        this.lastEdge=edge;
        wayGraph.addEdge(edge);
        endVertex = edge.getOtherVertex(endVertex);
        length += edge.getLength();
    }

    /**
     * Returns the Graph that represent the Way.
     * @return a Graph contain a single way.
     */
    public Graph getGraph(){
        return this.wayGraph;
    }

    /**
     * Returns the length of the complete way between start and end Vertex.
     * @return the length as a double.
     */
    public double getLength(){
        return length;
    }

    /**
     * Returns the first Vertex of the Way.
     * @return the first Vertex.
     */
    public Vertex getStartVertex() {
        return startVertex;
    }

    /**
     * Returns the last Vertex at the end of the Way
     * @return the last Vertex of the Way.
     */
    public Vertex getEndVertex() {
        return endVertex;
    }

    public Edge getLastEdge() {
        return lastEdge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphWay graphWay)) return false;
        return Double.compare(graphWay.getLength(), getLength()) == 0 && Objects.equals(wayGraph, graphWay.wayGraph) && Objects.equals(getStartVertex(), graphWay.getStartVertex()) && Objects.equals(getEndVertex(), graphWay.getEndVertex()) && Objects.equals(getLastEdge(), graphWay.getLastEdge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(wayGraph, getStartVertex(), getEndVertex(), getLastEdge(), getLength());
    }
}
