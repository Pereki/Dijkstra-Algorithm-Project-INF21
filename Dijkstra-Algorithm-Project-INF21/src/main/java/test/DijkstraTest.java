package test;

import model.Edge;
import model.Graph;
import model.Vertex;
import model.Way;
import service.Dijkstra;

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
        Vertex G = new Vertex(7,35.0,57.8);
        Vertex H = new Vertex(8,36.0,57.8);
        Vertex I = new Vertex(9,37.0,57.8);


        ArrayList vertexes = new ArrayList(Arrays.asList(new Vertex[]{A,B,C,D,E,F,G,H,I}));

        Edge e1 = new Edge(A,B,2);
        Edge e2 = new Edge(B,C,4);
        Edge e3 = new Edge(C,D,2);
        Edge e4 = new Edge(D,E,1);
        Edge e5 = new Edge(E,F,6);
        Edge e6 = new Edge(F,A,9);
        Edge e7 = new Edge(A,G,15);
        Edge e8 = new Edge(B,G,6);
        Edge e9 = new Edge(C,I,15);
        Edge e10 = new Edge(D,I,1);
        Edge e11 = new Edge(E,H,3);
        Edge e12 = new Edge(F,H,11);
        Edge e13 = new Edge(G,I,2);
        Edge e14 = new Edge(I,H,4);
        Edge e15 = new Edge(H,G,15);
        ArrayList edges = new ArrayList(Arrays.asList(new Edge[]{e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14,e15}));

        Graph raw = new Graph(edges,vertexes);



        Graph g = Dijkstra.getShortWay(raw,A,D);

        for(Vertex v:g.getVertexList()){
            System.out.print(v.getId());
        }

        System.out.println("finish");




    }

}
