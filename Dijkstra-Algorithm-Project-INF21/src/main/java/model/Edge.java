package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;
    private Vertex v1;
    private Vertex v2;
    private double length;

    public Edge(Vertex v1, Vertex v2, double length){
        this.v1 = v1;
        this.v2 = v2;
        this.length = length;
    }

    /**
     * Generate a new Edge with integrated calculating of the length based on the coordinates of the vertexes.
     * @param v1 One Vertex of the Edge
     * @param v2 The other Vertex of the Edge
     * @author i21005
     */
    public Edge(Vertex v1, Vertex v2){
        this.v1 = v1;
        this.v2 = v2;
        this.length=calcDistance();
    }

    /**
     * calculates the distance in km between the two vertexes of this edge.
     * @return the distance in km.
     * @author i21005
     */
    private double calcDistance(){
        double dy = 111.3*(v1.getLat()- v2.getLat()); //the y distance between two y coordinates
        double dx = 111.3*(Math.cos(Math.toRadians((v1.getLat()+v2.getLat())/2)))*(v1.getLon()-v2.getLon());// the x distance between two x coordinates.
        return Math.sqrt(dx*dx+dy*dy);
    }

    public Vertex getStartingVertex(){return v1;}

    public Vertex getEndingVertex(){return v2;}

    /**
     * Returns the other Vertex of a Edge
     * @param vertex the known Vertex to get the other one
     * @return the other Vertex of the Edge
     */
    public Vertex getOtherVertex(Vertex vertex){
        if(vertex.equals(v1)){return v2;
        }else if(vertex.equals(v2)){return v1;
        }else{return null;}
    }


    public double getLength(){return length;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.getLength(), getLength()) == 0 && Objects.equals(v1, edge.v1) && Objects.equals(v2, edge.v2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, getLength());
    }


}
