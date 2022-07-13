package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This Class shrinks the Graph for better performance.
 * @author i21015
 */
public class GraphShrinker {
    private Graph BigGraph;
    private Graph SmallGraph;

    public GraphShrinker(Graph g) {
        BigGraph = g;
        SmallGraph = g;
    }

    /**
     * merges vertexes that are more than once in a graph.
     */
    public void mergeGraph(){
        for(int i=0; i<SmallGraph.getVertexList().size();i++){
            System.out.println("Fortschritt: " + i + "/" + SmallGraph.getVertexList().size());
            Vertex currentVertex = SmallGraph.getVertexList().get(i);

            if(currentVertex.getIdentifier() != null && !currentVertex.getIdentifier().equals("")){
                for(int j = 0; j < SmallGraph.getVertexList().size(); j++) {
                    Vertex secondVertex = SmallGraph.getVertexList().get(j);

                    if (currentVertex.getIdentifier().equals(secondVertex.getIdentifier()) && !(j == i)) {

                        for (int k = 0; k < SmallGraph.getEdgeList().size(); k++) {
                            Edge currentEdge = SmallGraph.getEdgeList().get(k);

                            if (currentEdge.getStartingVertex().equals(secondVertex) || currentEdge.getEndingVertex().equals(secondVertex)) {
                                Edge newEdge = new Edge(currentEdge.getOtherVertex(secondVertex), currentVertex);

                                if(newEdge.getLength() < 5){
                                    SmallGraph.deleteEdge(currentEdge);
                                    SmallGraph.deleteVertex(secondVertex);
                                    SmallGraph.addEdge(newEdge);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * shrinks the graph and minimalizes it.
     * Deletes all Vertexes with an empty identifier and reorganiszing the edges of the given vertexes.
     * This Algorithm does <bold>NOT</bold> delete Vertexes that are have the boolean isCrossing set as well as Vertexes that have more than 2 edges.
     */
    public void shrinkGraph() {
        int i = 0;
        HashMap<Vertex, Integer> count = new HashMap<Vertex, Integer>();

        for(Edge currentEdge : this.BigGraph.getEdgeList()){
            Vertex startingVertex = currentEdge.getStartingVertex();
            Vertex endingVertex = currentEdge.getEndingVertex();

            if(!count.containsKey(startingVertex)){
                count.put(startingVertex, 1);
            }else {
                count.put(startingVertex, count.get(startingVertex)+1);
            }

            if(!count.containsKey(endingVertex)){
                count.put(endingVertex, 1);
            }else{
                count.put(endingVertex, count.get(endingVertex)+1);
            }
        }

        ArrayList<Vertex> onlyTwo = new ArrayList<>();
        ArrayList<Vertex> onlyOne = new ArrayList<>();

        for(Map.Entry<Vertex, Integer> currentEntry : count.entrySet()){
            if(currentEntry.getValue() == 2 && !currentEntry.getKey().isCrossing()) {
                onlyTwo.add(currentEntry.getKey());
            }
            if(currentEntry.getValue() == 1 && !currentEntry.getKey().isCrossing()) {
                onlyOne.add(currentEntry.getKey());
            }
        }


        ArrayList<Edge> edgesOnCurrentVertex = new ArrayList<>();
        for(Vertex v : onlyTwo){
            Vertex currentvertex = v;
            i++;

            for(int n = 0; n < this.SmallGraph.getEdgeList().size(); n++){
                Edge currentEdge = this.SmallGraph.getEdgeList().get(n);

                if(currentEdge.getStartingVertex().equals(v) || currentEdge.getEndingVertex().equals(v)){
                    if(edgesOnCurrentVertex.size() == 1){
                        if(!(edgesOnCurrentVertex.get(0).equals(currentEdge))){
                            edgesOnCurrentVertex.add(currentEdge);
                        }
                    }else{
                        edgesOnCurrentVertex.add(currentEdge);
                    }
                }
            }

            Edge firstEdge = edgesOnCurrentVertex.get(0);
            Edge secondEdge = edgesOnCurrentVertex.get(1);
            Vertex start;
            Vertex end;

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
            this.SmallGraph.addEdge(new Edge(start, end, firstEdge.getLength()+secondEdge.getLength()));
            this.SmallGraph.deleteEdge(firstEdge);
            this.SmallGraph.deleteEdge(secondEdge);
            this.SmallGraph.deleteVertex(currentvertex);
            edgesOnCurrentVertex = new ArrayList<>();
        }

        i = 0;
        for(Vertex v : onlyOne){
            i++;

            for(int n = 0; n < this.SmallGraph.getEdgeList().size(); n++){
                if(this.SmallGraph.getEdgeList().get(n).getStartingVertex().equals(v) || this.SmallGraph.getEdgeList().get(n).getEndingVertex().equals(v)){
                    Edge currentEdge = this.SmallGraph.getEdgeList().get(n);
                    this.SmallGraph.deleteEdge(currentEdge);
                    this.SmallGraph.deleteVertex(v);
                }

            }

        }
    }

    /**
     * Returns the minimized Graph or the normal Graph
     * @return the minimized Graph after it was shrinked. If it was not shrinked it will return the normal Graph.
     */
    public Graph getMinimizedGraph() {
        return this.SmallGraph;
    }

}
