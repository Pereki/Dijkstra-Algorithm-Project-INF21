package Services;


import Model.Edge;
import Model.Graph;
import Model.Vertex;
import Model.Way;

import java.util.ArrayList;
import java.util.List;

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
public static Graph getShortWay(Graph rawGraph, Vertex start,Vertex target){
    ArrayList<Way> possibleWays = new ArrayList<Way>();
    Vertex actualVertex = start;
    //1.Create new Graph with start Vertex as single Vertex
    Graph dijkstraGraph = new Graph();
    dijkstraGraph.addVertex(start);
    //2.Add all accessible ways to the possible way list
    for(Edge edge:dijkstraGraph.getEdges(start)){
        possibleWays.add(new Way(start,edge));
    }
    //3.Add the last Edge from the shortest way of the possible way list to the Graph
    Way shortestWay = possibleWays.get(0);
    for(Way way:possibleWays){
        if(way.getLength()<shortestWay.getLength()){
            shortestWay=way;
        }
    }
    dijkstraGraph.addEdge(shortestWay.getLastEdge());
    actualVertex = shortestWay.getEndVertex();
    //5.If the Vertex is the target Vertex, build a Graph with the way and return it, else repeat all from 2. on.
    if(actualVertex.equals(target)){
        return shortestWay.getGraph();
    }
    while(true){
        for(Edge edge:dijkstraGraph.getEdges(actualVertex)){
            possibleWays.add(new Way(shortestWay,edge));
        }
        possibleWays.remove(shortestWay);
        shortestWay = possibleWays.get(0);
        for(Way way:possibleWays){
            if(way.getLength()<shortestWay.getLength()){
                shortestWay=way;
            }
        }
        dijkstraGraph.addEdge(shortestWay.getLastEdge());
        actualVertex = shortestWay.getEndVertex();
        if(actualVertex.equals(target)){
            break;
        }
    }
    //This section is a dummy to provide an output for dependent components, this output is not the correct way.
    return shortestWay.getGraph();
}
}
