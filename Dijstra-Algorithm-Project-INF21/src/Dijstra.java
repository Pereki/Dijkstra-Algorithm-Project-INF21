import java.sql.Array;
import java.util.ArrayList;

public class Dijstra {
     private Graph handledGraph;

     public Dijstra(Graph currentGraph){
         handledGraph = currentGraph;
     }

     public int getShortesWay(Vertex start, Vertex end) {
         ArrayList<Vertex> visited = new ArrayList<Vertex>();
         ArrayList<Vertex> availableVertex = new ArrayList<Vertex>();
         availableVertex.add(start);
         while (true) {
             for(int i = 0; i < availableVertex.size(); i++){
                ArrayList<Edge> edges = handledGraph.getOptionsOfVertex(availableVertex.get(i));

                for(Edge edge : edges){
                    if(!(availableVertex.contains(edge))){
                        availableVertex.add(edge.getEndingVertex());
                    }

                }
            }
        }

    }
}
