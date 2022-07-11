package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;

public class GraphShrinker {
    Graph BigGraph;
    Graph SmallGraph;

    public GraphShrinker(Graph g) {
        BigGraph = g;
        SmallGraph = g;
    }

    public void shrinkGraph() {
        Edge firstEdge = null;
        Edge secondEdge = null;
        Vertex start;
        Vertex end;
        for (int i = 0; i < this.BigGraph.getVertexList().size(); i++) {
            Vertex currentvertex = this.BigGraph.getVertexList().get(i);
            if (currentvertex.getIdentifier().equals("Hannover")) {
                System.out.println("deleteNode");
                //Go trough Edgelist and search the two Edges
                for (int j = 0; j < this.BigGraph.getEdgeList().size(); j++) {
                    Edge currentEdge = this.BigGraph.getEdgeList().get(j);
                    if ((currentEdge.getStartingVertex() == currentvertex || currentEdge.getEndingVertex() == currentvertex) && firstEdge == null){
                        this.SmallGraph.deleteEdge(currentEdge);
                        firstEdge = currentEdge;
                        j--;
                    }
                    else if((currentEdge.getStartingVertex() == currentvertex || currentEdge.getEndingVertex() == currentvertex) && !(firstEdge.equals(currentEdge))){
                        this.SmallGraph.deleteEdge(currentEdge);
                        secondEdge = currentEdge;
                        j--;
                    }
                }
                if(firstEdge.getStartingVertex().equals(currentvertex)){
                    start = firstEdge.getEndingVertex();
                }else{
                    start = firstEdge.getStartingVertex();
                }

                if(secondEdge.getStartingVertex().equals(currentvertex)){
                    end = secondEdge.getEndingVertex();
                }else{
                    end = secondEdge.getStartingVertex();
                }
                SmallGraph.addEdge(new Edge(start, end));
                SmallGraph.deleteVertex(currentvertex);
                i--;
            }
        }
    }

    public Graph getMinimizedGraph() {
        return this.SmallGraph;
    }

}
