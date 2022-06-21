package service;


import model.Edge;
import model.Graph;
import model.Vertex;
import model.Way;

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
    ArrayList<Way> possibleWays = new ArrayList<>();
    Vertex actualVertex = start;
    Way shortestWay = new Way(actualVertex);
    possibleWays.add(shortestWay);
    while (!actualVertex.equals(target)){
        for(Edge edge:rawGraph.getEdges(actualVertex)){ //generate new possible ways from the actual vertex
            possibleWays.add(new Way(shortestWay,edge));
        }
        possibleWays.remove(shortestWay); //remove the way to the actual vertex
        shortestWay = possibleWays.get(0);
        for(Way way:possibleWays){ //get the new shortest way
            if(way.getLength()<shortestWay.getLength()){
                shortestWay=way;
            }
        }
        actualVertex = shortestWay.getEndVertex(); //set the end vertex of the shortest way as the actual vertex
        }
    return shortestWay.getGraph(); // return the shortest way as a Graph
}
}
