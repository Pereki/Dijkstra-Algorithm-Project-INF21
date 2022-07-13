package service;


import application.Application;
import controller.Controller;
import model.Edge;
import model.Graph;
import model.GraphWay;
import model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class provides the function of the Dijkstra algorithm.
 * @author i21005
 */
public class Dijkstra {
/**
 * Returns the shortest way between two points in a Graph.
 * This method is using the dijkstra algorithm to find the shortest way.
 * <bold>!!!!!!!This method is still in construction, for this reason is the return value an empty Graph!!!!!!! </bold>
 * @param rawGraph a unidirectional Graph as the base of the route.
 * @param start the Vertex to start from.
 * @param target the target Vertex.
 * @return the Graph with the shortest way.
 */
public static Graph getShortWay(Graph rawGraph, Vertex start,Vertex target) throws Exception {
    ArrayList<GraphWay> possibleGraphWays = new ArrayList<>();
    Vertex actualVertex = start;
    HashSet<Edge> marked = new HashSet<>();
    GraphWay shortestGraphWay = new GraphWay(actualVertex);
    possibleGraphWays.add(shortestGraphWay);
    while (!actualVertex.equals(target)){
        for(Edge edge:rawGraph.getEdges(actualVertex)){ //generate new possible ways from the actual vertex
            if(!marked.contains(edge)) {
                possibleGraphWays.add(new GraphWay(shortestGraphWay, edge));
            }
            marked.add(edge);
        }
        possibleGraphWays.remove(shortestGraphWay); //remove the way to the actual vertex
        shortestGraphWay = possibleGraphWays.get(0);
        for(GraphWay graphWay : possibleGraphWays){ //get the new shortest way
            if(graphWay.getLength()< shortestGraphWay.getLength()){
                shortestGraphWay = graphWay;
            }
        }
        actualVertex = shortestGraphWay.getEndVertex(); //set the end vertex of the shortest way as the actual vertex

        //printGraph(shortestGraphWay.getGraph());
        }
    return shortestGraphWay.getGraph(); // return the shortest way as a Graph
}

    /**
     * For Debugging to print a Graph
     * @param g the Graph to print out on cmd
     */
    private static void printGraph(Graph g){
    for(Vertex v: g.getVertexList()){
        if(!v.getIdentifier().equals("")){
            System.out.print(v.getIdentifier()+"->");
        }
    }
    System.out.println();
    System.out.println();
}
}
