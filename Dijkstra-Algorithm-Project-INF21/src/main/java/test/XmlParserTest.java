package test;

import model.Edge;
import model.Graph;
import model.Vertex;
import service.Dijkstra;

public class XmlParserTest {
    public boolean crossingTest(){


        Graph g = new Graph();

        g.addEdge(new Edge(new Vertex(0, 0, 0), new Vertex(0, 10, 10)));

        Edge e = new Edge(new Vertex(0, 0, 10), new Vertex(0, 10, 0));

        g.addEdge(e);
        g.createCrossingIfNeeded(e);

        Edge f = new Edge(new Vertex(0, 0,15), new Vertex(0,15,15));
        g.addEdge(f);
        g.createCrossingIfNeeded(f);

        System.out.println(g.getVertexList().size());

        if(7==g.getVertexList().size()){
            return true;
        }else{
            return false;
        }
    }
}
