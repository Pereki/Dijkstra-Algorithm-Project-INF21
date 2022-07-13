package test;

import model.Edge;
import model.Graph;
import service.SerializeService;
import model.Vertex;
import service.XmlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class DijkstraTest {
    public static void main(String[] args) throws Exception {
        Vertex S = new Vertex(1,48.76976,9.17399, "Stuttgart", true);  //Berlin
        Vertex B = new Vertex(2,52.52204,13.41648, "Berlin", true); //Stuttgart
        Vertex L = new Vertex(3,51.34556,12.28058, "Leipzig", true); //Leipzig
        Vertex N = new Vertex(4,49.45598,11.08208, "Nürnberg", true); //Nürnberg
        Vertex M = new Vertex(5,48.13899,11.56157, "München", true); //München
        Vertex F = new Vertex(6,50.11145,8.66331, "Frankfurt a.M.", true);  //Frankfurt a.M.
        Vertex D = new Vertex(7,51.50876,7.50876, "Dortmund", true);  //Dortmund
        Vertex H = new Vertex(8,52.36271,9.72252, "Hannover", true);  //Hannover
        Vertex HH = new Vertex(9,53.54767,10.00187, "Hamburg", true); //Hamburg

        Vertex v1 = new Vertex(10, 48.022071, 9.987905, null, false);
        Vertex v2 = new Vertex(10, 47.621026, 9.432503, null, false);
        Vertex v3 = new Vertex(10, 46.420502, 7.511061, null, false);

        ArrayList vertexes = new ArrayList(Arrays.asList(new Vertex[]{B,S,L,N,M,F,D,H,HH}));

        Edge e1 = new Edge(B,L);
        Edge e2 = new Edge(B,H);
        Edge e3 = new Edge(B,HH);
        Edge e4 = new Edge(L,N);
        Edge e5 = new Edge(N,S);
        Edge e6 = new Edge(N,M);
        Edge e7 = new Edge(S,v1);
        Edge e7b = new Edge(v1,M);
        Edge e8 = new Edge(S,F);
        Edge e9 = new Edge(F,N);
        Edge e10 = new Edge(F,L);
        Edge e11 = new Edge(F,D);
        Edge e12 = new Edge(D,H);
        Edge e13 = new Edge(D,L);
        ArrayList edges = new ArrayList(Arrays.asList(new Edge[]{e1,e2,e3,e4,e5,e6,e7,e7b,e8,e9,e10,e11,e12,e13}));

        //Graph raw = new Graph(edges,vertexes);
        //SerializeService.saveGraph(raw,"graph_non_junctions.graph");
        //Graph raw = SerializeService.loadGraph("C:\\Users\\Lukas\\Downloads\\finishedsmall.txt");


        //Vertex Horb = getVertex(raw,"horb");
        //Vertex Empf =   getVertex(raw,"würzburg");

        //Graph g = Dijkstra.getShortWay(raw,Horb,Empf).getGraph();



        //double length =0;
        //for(Edge e: g.getEdgeList()){
            //length+=e.getLength();
       //}
       //System.out.println(length);
        //System.out.println("finish");

/*
        XmlParserTest parser = new XmlParserTest();

        //if(parser.crossingTest()){
            //System.out.println("Parser läuft");
        //}

        //parser.graphTest();

        parser.borderTest();
*/
        XmlParser x = new XmlParser("C:\\Users\\ykont\\Documents\\GitHub\\Dijkstra-Algorithm-Project-INF21\\test2.osm");
        Graph g = x.getGraph();

        SerializeService.saveGraph(g,"graph.graph");
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
