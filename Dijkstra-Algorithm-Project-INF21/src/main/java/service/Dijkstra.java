package service;


import model.Edge;
import model.Graph;
import model.GraphWay;
import model.Vertex;

import java.util.ArrayList;

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
    GraphWay shortestGraphWay = new GraphWay(actualVertex);
    possibleGraphWays.add(shortestGraphWay);
    while (!actualVertex.equals(target)){
        for(Edge edge:rawGraph.getEdges(actualVertex)){ //generate new possible ways from the actual vertex
            possibleGraphWays.add(new GraphWay(shortestGraphWay,edge));
        }
        possibleGraphWays.remove(shortestGraphWay); //remove the way to the actual vertex
        shortestGraphWay = possibleGraphWays.get(0);
        for(GraphWay graphWay : possibleGraphWays){ //get the new shortest way
            if(graphWay.getLength()< shortestGraphWay.getLength()){
                shortestGraphWay = graphWay;
            }
        }
        actualVertex = shortestGraphWay.getEndVertex(); //set the end vertex of the shortest way as the actual vertex
        }
    return shortestGraphWay.getGraph(); // return the shortest way as a Graph
}

    /**
     * For Debugging to print a Graph
     * @param g the Graph to print out on cmd
     */
    private static void printGraph(Graph g){
    for (Vertex v : g.getVertexList()) {
        System.out.print(v.getId());
    }
    System.out.println();
}
}
