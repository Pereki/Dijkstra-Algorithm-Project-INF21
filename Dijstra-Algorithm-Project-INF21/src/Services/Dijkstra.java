package Services;


import Model.Graph;
import Model.Vertex;

import java.util.ArrayList;

/**
 * This class provides the function of the Dijkstra algorithm.
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
public static Graph getShortWay(Graph rawGraph, Vertex start,Vertex target){
    //1.Create new Graph with start Vertex as single Vertex
    //2.Add all accessible ways to the possible way list
    //3.Add the last Edge from the shortest way of the possible way list to the Graph
    //4.Add the Vertex to which the Edge is leading to the new Graph
    //5.If the Vertex is the target Vertex, build a Graph with the way and return it, else repeat all from 2. on.
    //This section is a dummy to provide an output for dependent components, this output is not the correct way.
    return new Graph(new ArrayList<>());
}
}
