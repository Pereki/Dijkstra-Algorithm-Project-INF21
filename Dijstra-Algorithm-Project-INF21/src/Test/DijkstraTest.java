package Test;

import Model.Edge;
import Model.Graph;
import Model.Vertex;
import Model.Way;
import Services.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DijkstraTest {
    public static void main(String[] args) throws Exception {
        Vertex A = new Vertex(1,34.4,57.8);
        Vertex B = new Vertex(2,34.5,57.8);
        Vertex C = new Vertex(3,34.6,57.8);
        Vertex D = new Vertex(4,34.7,57.8);
        Vertex E = new Vertex(5,34.8,57.8);
        Vertex F = new Vertex(6,34.9,57.8);
        Vertex G = new Vertex(7,34.0,57.8);
        ArrayList vertexes = new ArrayList(Arrays.asList(new Vertex[]{A,B,C,D,E,F,G}));

        Edge e1 = new Edge(C,A,0,4);
        Edge e2 = new Edge(C,D,0,5);
        Edge e3 = new Edge(C,F,0,10);
        Edge e4 = new Edge(F,G,0,3);
        Edge e5 = new Edge(F,D,0,4);
        Edge e6 = new Edge(A,B,0,7);
        Edge e7 = new Edge(A,D,0,2);
        Edge e8 = new Edge(D,B,0,1);
        Edge e9 = new Edge(D,G,0,8);
        Edge e10 = new Edge(G,E,0,4);
        Edge e11 = new Edge(B,E,0,12);
        ArrayList edges = new ArrayList(Arrays.asList(new Edge[]{e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11}));

        Graph raw = new Graph(edges,vertexes);



        Graph g = Dijkstra.getShortWay(raw,C,E);

        for(Vertex v:g.getVertexList()){
            System.out.print(v.getId());
        }

        System.out.println("finish");




    }

}
