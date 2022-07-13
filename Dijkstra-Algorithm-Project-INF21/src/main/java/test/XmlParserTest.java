package test;

import model.*;
import service.Dijkstra;
import service.XmlBorders;
import service.XmlParser;

public class XmlParserTest {
    public boolean crossingTest(){


        Graph g = new Graph();

        g.addEdge(new Edge(new Vertex(0, 0, 0), new Vertex(0, 10, 10)));

        Edge e = new Edge(new Vertex(0, 0, 10), new Vertex(0, 10, 0));

        g.addEdge(e);
        g.createCrossingIfNeeded(e);

        Edge f = new Edge(new Vertex(0, 0,15), new Vertex(0,15,17));
        g.addEdge(f);
        g.createCrossingIfNeeded(f);


        if(7==g.getVertexList().size()){
            return true;
        }else{
            return false;
        }
    }

    public void graphTest(){
        XmlParser x = new XmlParser("C:\\Users\\ykont\\Documents\\GitHub\\Dijkstra-Algorithm-Project-INF21\\test2.osm");

        Graph g = x.getGraph();
    }

    public void borderTest(){
        XmlBorders borders = new XmlBorders("C:\\Users\\ykont\\Documents\\GitHub\\Dijkstra-Algorithm-Project-INF21\\Dijkstra-Algorithm-Project-INF21\\src\\main\\java\\service\\border.osm");

        Graph g = borders.getGraph();

        System.out.println(g.getVertexList().size());
        System.out.println(g.getEdgeList().size());
    }
}
