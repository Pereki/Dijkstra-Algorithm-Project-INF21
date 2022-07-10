package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;

public class GraphShrinker {
    Graph BigGraph;


    public GraphShrinker(Graph g){
        BigGraph = g;
    }

    public void shrinkGraph(){
        ArrayList<Edge> edgelist = this.BigGraph.getEdgeList();
        for (Vertex currentVertex : this.BigGraph.getVertexList()) {
            if(currentVertex.getIdentifier() == ""){
                for(Edge currentedge : this.BigGraph.getEdgeList()){
                    if(currentedge.getEndingVertex().equals(currentVertex)){

                    }
                }
            }

        }
    }

}
