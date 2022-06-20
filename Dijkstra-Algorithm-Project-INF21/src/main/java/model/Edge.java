package model;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {
    private final Vertex v1;
    private final Vertex v2;
    private final int marking;
    private final double length;

    public Edge(Vertex v1, Vertex v2, int marking, double length){
        this.v1 = v1;
        this.v2 = v2;
        this.marking = marking;
        this.length = length;
    }

    public Vertex getStartingVertex(){return v1;}

    public Vertex getEndingVertex(){return v2;}

    public Vertex getOtherVertex(Vertex vertex){
        if(vertex.equals(v1)){return v2;
        }else if(vertex.equals(v2)){return v1;
        }else{return null;}
    }

    public int getMarking(){return marking;}

    public double getLength(){return length;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return getMarking() == edge.getMarking() && Double.compare(edge.getLength(), getLength()) == 0 && Objects.equals(v1, edge.v1) && Objects.equals(v2, edge.v2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, getMarking(), getLength());
    }
}
