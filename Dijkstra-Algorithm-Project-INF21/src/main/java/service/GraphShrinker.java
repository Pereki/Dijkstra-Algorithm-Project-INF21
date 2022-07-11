package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;

public class GraphShrinker {
    Graph BigGraph;
    Graph SmallGraph;

    public GraphShrinker(Graph g){
        BigGraph = g;
        SmallGraph = g;
    }

    public void shrinkGraph(){
        ArrayList<Edge> finishingEdges = new ArrayList<Edge>();
        ArrayList<Edge> startingEdges = new ArrayList<Edge>();
        for (Vertex currentVertex : this.BigGraph.getVertexList()) {
            if(currentVertex.getIdentifier() == ""){
               this.SmallGraph.deleteVertex(currentVertex);
                for(Edge currentedge : this.BigGraph.getEdgeList()){
                    if(currentedge.getEndingVertex().equals(currentVertex)){
                       this.SmallGraph.deleteEdge(currentedge);
                        finishingEdges.add(currentedge);
                    }
                    if(currentedge.getStartingVertex().equals(currentVertex)){
                      this.SmallGraph.deleteEdge(currentedge);
                        startingEdges.add(currentedge);
                    }
                }
                for(Edge startingVertexes : finishingEdges){
                    for(Edge endingVertexes : startingEdges){
                        SmallGraph.addEdge(new Edge(startingVertexes.getStartingVertex(), endingVertexes.getEndingVertex()));
                    }
                }
                finishingEdges = new ArrayList<Edge>();
                startingEdges = new ArrayList<Edge>(); 
            }
        }
    }

    public Graph getMinimizedGraph(){
        return this.SmallGraph;
    }

}
