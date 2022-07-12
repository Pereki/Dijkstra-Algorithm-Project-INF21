package service;

import model.Edge;
import model.Graph;
import model.SerializeService;
import model.Vertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphShrinker {
    Graph BigGraph;
    Graph SmallGraph;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int leer = 0;
        int leerzeichen = 0;
        int hasnull = 0;
        Graph f = SerializeService.loadGraph("C:/Users/ruben/Downloads/test.txt");
        Graph g = SerializeService.loadGraph("C:/Users/ruben/Downloads/output.txt");

    }
    public GraphShrinker(Graph g) {
        BigGraph = g;
        SmallGraph = g;
    }

    public void shrinkGraph_2() {
        int i = 0, j = 0, k = 0;
        HashMap<Vertex, ArrayList<Edge>> count = new HashMap<Vertex, ArrayList<Edge>>();
        for(Edge currentEdge : this.BigGraph.getEdgeList()){

            //System.out.println("Abgearbeitet Edges: " + i + "/" + this.BigGraph.getEdgeList().size());
            Vertex startingVertex = currentEdge.getStartingVertex();
            Vertex endingVertex = currentEdge.getEndingVertex();
            if(!count.containsKey(startingVertex)){
                count.put(startingVertex, new ArrayList<>());
            }
            count.get(startingVertex).add(currentEdge);

            if(!count.containsKey(endingVertex)){
                count.put(endingVertex, new ArrayList<>());
            }
            count.get(endingVertex).add(currentEdge);
        }

        i = 0;
        System.out.println("\n\n\n\n\n\n\n\n\n\n NOW GOING THROUGH HASHMAP \n\n\n\n\n\n\n\n\n\n");
        for(Map.Entry<Vertex, ArrayList<Edge>> currentEntry : count.entrySet()){
            i++;
            System.out.println("Abgearbeitet Knoten: " + i + "/" + count.size());
            ArrayList<Edge> currentValue =  currentEntry.getValue();
            Vertex currentvertex = currentEntry.getKey();
            if(currentValue.size() == 2 && !currentvertex.getJunction() && !currentvertex.isCrossing()){

                Edge firstEdge = currentValue.get(0);
                Edge secondEdge = currentValue.get(1);
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
                SmallGraph.addEdge(new Edge(start, end, firstEdge.getLength()+secondEdge.getLength()));
                this.SmallGraph.deleteEdge(currentValue.get(0));
                this.SmallGraph.deleteEdge(currentValue.get(1));
                this.SmallGraph.deleteVertex(currentvertex);
            }
        }
    }



    public void shrinkGraph() {
        Edge firstEdge = null;
        Edge secondEdge = null;
        boolean firstfull = false;
        int z = 0;
        Vertex start;
        Vertex end;
        for (int i = 0; i < this.BigGraph.getVertexList().size(); i++) {
            Vertex currentvertex = this.BigGraph.getVertexList().get(i);
            if (currentvertex.getIdentifier().equals("") && !currentvertex.isCrossing()) {
                //System.out.println("deleteNode: " + currentvertex.getIdentifier());
                //Go trough Edgelist and search the two Edges
                for (int j = 0; j < this.BigGraph.getEdgeList().size(); j++) {
                    Edge currentEdge = this.BigGraph.getEdgeList().get(j);


                    if ((currentEdge.getStartingVertex() == currentvertex || currentEdge.getEndingVertex() == currentvertex) && !firstfull){
                        this.SmallGraph.deleteEdge(currentEdge);
                        firstEdge = currentEdge;
                        firstfull = true;
                        z++;
                        j--;
                    }
                    else if((currentEdge.getStartingVertex() == currentvertex || currentEdge.getEndingVertex() == currentvertex) && !(firstEdge.equals(currentEdge)) && firstfull){
                        this.SmallGraph.deleteEdge(currentEdge);
                        secondEdge = currentEdge;
                        z++;
                        j--;
                    }
                }
                firstfull = false;
                if(z == 2){
                    //System.out.println("moin");
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
                SmallGraph.addEdge(new Edge(start, end, firstEdge.getLength()+secondEdge.getLength()));
                //System.out.println("newEdge: " + start.getIdentifier() + " ---- " + end.getIdentifier());
                SmallGraph.deleteVertex(currentvertex);
                    i--;
                    System.out.println("Abgearbeitet Knoten: " + i + "/" + this.BigGraph.getVertexList().size());
            }
                z = 0;
                System.out.println("Nicht gelöscht: " + i);
                firstEdge = null;
                secondEdge = null;
            }
        }
    }

    public Graph getMinimizedGraph() {
        return this.SmallGraph;
    }

}
