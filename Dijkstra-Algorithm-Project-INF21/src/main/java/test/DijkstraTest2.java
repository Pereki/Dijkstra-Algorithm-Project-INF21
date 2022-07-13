package test;

import model.Edge;
import model.Graph;
import model.Vertex;
import service.SerializeService;

import java.util.ArrayList;
import java.util.Arrays;


public class DijkstraTest2 {
    public static void main(String[] args) throws Exception {
        Vertex A = new Vertex(1,48.973201, 8.138205);
        Vertex B = new Vertex(2,47.645286, 7.668395);
        Vertex C = new Vertex(3,48.469548, 13.795498);
        Vertex D = new Vertex(4,50.266664, 12.151164);
        Vertex E = new Vertex(5,50.999213, 14.832995);
        Vertex F = new Vertex(6,54.447044, 13.345264);
        Vertex G = new Vertex(7,54.820937, 9.430182);
        Vertex H = new Vertex(8,53.398108, 7.100708);
        Vertex I = new Vertex(9,50.128823, 6.121938);

        ArrayList vertexes = new ArrayList(Arrays.asList(new Vertex[]{A, B, C, D, E, F, G, H, I}));

        Edge e1 = new Edge(A,B);
        Edge e2 = new Edge(B,C);
        Edge e3 = new Edge(C,D);
        Edge e4 = new Edge(D,E);
        Edge e5 = new Edge(E,F);
        Edge e6 = new Edge(F,G);
        Edge e7 = new Edge(G,H);
        Edge e8 = new Edge(H,I);
        Edge e9 = new Edge(I,A);
        ArrayList edges = new ArrayList(Arrays.asList(new Edge[]{e1,e2,e3,e4,e5,e6,e7,e8,e9}));

        Graph raw = new Graph(edges,vertexes);
        SerializeService.saveGraph(raw, "graphs/de_roads_rough.graph");
    }
    static public Vertex getVertex(Graph graph,String  identifier){
        for(Vertex vertex:graph.getVertexList()){
            if(vertex.getIdentifier().equals(identifier)){
                return vertex;
            }
        }
        return null;
    }




}
