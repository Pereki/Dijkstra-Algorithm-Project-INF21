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
        ArrayList<Edge> finishingEdges = new ArrayList<Edge>();
        ArrayList<Edge> startingEdges = new ArrayList<Edge>();
        for (Vertex currentVertex : this.BigGraph.getVertexList()) {
            if(currentVertex.getIdentifier() == ""){
                for(Edge currentedge : this.BigGraph.getEdgeList()){
                    if(currentedge.getEndingVertex().equals(currentVertex)){
                        finishingEdges.add(currentedge);

                    }
                    if(currentedge.getStartingVertex().equals(currentVertex)){
                        startingEdges.add(currentedge);
                    }
                }
                for(Edge fin : finishingEdges){

                }
            }

        }
    }

}
