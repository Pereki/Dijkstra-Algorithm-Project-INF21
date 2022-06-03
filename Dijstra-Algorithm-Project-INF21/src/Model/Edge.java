package Model;

public class Edge {
    private Vertex v1;
    private Vertex v2;
    private int marking;
    private double length;

    public Edge(Vertex v1, Vertex v2, int marking, double length){
        this.v1 = v1;
        this.v2 = v2;
        this.marking = marking;
        this.length = length;
    }

    public Vertex getStartingVertex(){return v1;}

    public Vertex getEndingVertex(){return v2;}

    public Vertex getOtherVertex(Vertex vertex) throws Exception {
        if(vertex.equals(v1)){return v2;
        }else if(vertex.equals(v2)){return v1;
        }else{throw new Exception("Invalid Vertex");}
    }

    public int getMarking(){return marking;}

    public double getLength(){return length;}
}
