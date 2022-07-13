package service;

import model.Edge;
import model.Graph;
import model.SerializeService;
import model.Vertex;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphShrinker {
    Graph BigGraph;
    Graph SmallGraph;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int loeschen = 0;
        int leerzeichen = 0;
        int hasnull = 0;
        Graph f = SerializeService.loadGraph("C:/Users/ruben/Downloads/test.txt");
        while(true) {
            for (int i = 0; i < f.getEdgeList().size(); i++) {
                // if(f.getVertexList().get(i).getIdentifier() == "autobahndreieck" || f.getVertexList().get(i).getIdentifier() == "kreuz" ||f.getVertexList().get(i).getIdentifier() == "westkreuz"  ||f.getVertexList().get(i).getIdentifier() == "rasthof" || f.getVertexList().get(i).getIdentifier() == "rastplatz" )
                if (f.getEdgeList().get(i).getEndingVertex().getIdentifier() != null) {
                    if (f.getEdgeList().get(i).getEndingVertex().getIdentifier().equals("horb") || f.getEdgeList().get(i).getStartingVertex().getIdentifier() == "horb") {
                        System.out.println(f.getEdgeList().get(i).getStartingVertex().getIdentifier() + "  ---->  " + f.getEdgeList().get(i).getEndingVertex().getIdentifier());
                    }
                    loeschen++;
                }
            }
        }


       // GraphShrinker z = new GraphShrinker(f);
       // z.shrinkGraph_2();
       // SerializeService.saveGraph(z.getMinimizedGraph(), "C:/Users/ruben/Downloads/finishedsmall.txt");

    }


    public GraphShrinker(Graph g) {
        BigGraph = g;
        SmallGraph = g;
    }

    public void mergeGraph(){
        for(int i=0; i<SmallGraph.getVertexList().size();i++){
            Vertex toCompare = SmallGraph.getVertexList().get(i);

            if(toCompare.getIdentifier()!=null && !(toCompare.getIdentifier().equals(""))){
                for(int a=0; a<SmallGraph.getVertexList().size();a++){
                    if(toCompare.getIdentifier().equals(SmallGraph.getVertexList().get(a))&&a!=i){
                        Vertex secondCompare = SmallGraph.getVertexList().get(a);
                        for(int b=0;b<SmallGraph.getEdgeList().size();b++){
                            Edge e = SmallGraph.getEdgeList().get(b);
                            if(e.getStartingVertex().equals(secondCompare)||e.getEndingVertex().equals(secondCompare)){
                                Edge newEdge = new Edge(e.getOtherVertex(secondCompare),toCompare);
                                if(newEdge.getLength()<5){
                                    SmallGraph.getEdgeList().add(newEdge);
                                    SmallGraph.getEdgeList().remove(e);
                                    SmallGraph.deleteVertex(secondCompare);
                                    b--;
                                    a--;
                                    i--;
                                }
                            }
                        }
                    }
                }
            }
        }
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
        ArrayList<Vertex> onlyTwo = new ArrayList<>();
        for(Map.Entry<Vertex, ArrayList<Edge>> currentEntry : count.entrySet()){
            if(currentEntry.getValue().size() == 2 && !currentEntry.getKey().getJunction() && !currentEntry.getKey().isCrossing()) {
                onlyTwo.add(currentEntry.getKey());
            }
        }
        System.out.println("This will be deleted: " + onlyTwo.size());
        ArrayList<Edge> edges = new ArrayList<>();
        for(Vertex v : onlyTwo){
            i++;

            for(int n = 0; n < this.SmallGraph.getEdgeList().size(); n++){
                if(this.SmallGraph.getEdgeList().get(n).getStartingVertex().equals(v) || this.SmallGraph.getEdgeList().get(n).getEndingVertex().equals(v)){
                    if(edges.size() >= 1){
                        if(!(edges.get(0).equals(this.SmallGraph.getEdgeList().get(n)))){
                            edges.add(this.SmallGraph.getEdgeList().get(n));
                        }
                    }else{
                        edges.add(this.SmallGraph.getEdgeList().get(n));
                    }
                }
            }
            System.out.println("Abgearbeitet Knoten: " + i + "/" + onlyTwo.size());
            Vertex currentvertex = v;

                Edge firstEdge = edges.get(0);
                Edge secondEdge = edges.get(1);
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
                this.SmallGraph.deleteEdge(firstEdge);
                this.SmallGraph.deleteEdge(secondEdge);
                this.SmallGraph.deleteVertex(currentvertex);
                edges = new ArrayList<>();
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
