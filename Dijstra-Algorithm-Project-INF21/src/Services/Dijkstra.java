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
public static Graph getShortWay(Graph rawGraph, Vertex start,Vertex target) throws Exception {
    ArrayList<Way> possibleWays = new ArrayList<>();
    Vertex actualVertex = start;
    Way shortestWay = new Way(actualVertex);
    possibleWays.add(shortestWay);
    //for(int i =0;i<10;i++){
    while (!actualVertex.equals(target)){
        for(Edge edge:rawGraph.getEdges(actualVertex)){
            Way xway = new Way(shortestWay,edge);
            possibleWays.add(xway);
        }
        possibleWays.remove(shortestWay);
        //get the new shortest Way
        shortestWay = possibleWays.get(0);
        for(Way way:possibleWays){
            if(way.getLength()<shortestWay.getLength()){
                shortestWay=way;
            }
        }
        actualVertex = shortestWay.getEndVertex();
        for(Way way:possibleWays){
            for(Vertex v:way.getGraph().getVertexList()){
                System.out.print(v.getId());
            }
            System.out.printf(" Länge: %f",way.getLength());
            System.out.println();
        }
        System.out.println("Next Line");
    }


    //This section is a dummy to provide an output for dependent components, this output is not the correct way.
    System.out.printf("Länge: %f\n",shortestWay.getLength());
    return shortestWay.getGraph();
}
}
