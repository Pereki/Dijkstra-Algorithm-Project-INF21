package Model;//represents a node in osm xml

import java.io.Serializable;
import java.util.Objects;

public class Vertex implements Serializable {
    private int id;
    private int costs = 0;
    private Vertex predecessor;
    private double lat;//Breitengrad
    private double lon;//Längengrad
    private boolean junction;//anschlussstelle

    public Vertex(int id, double lat, double lon, Vertex predecessor, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.predecessor =  predecessor;
        this.junction = junction;
    }

    public Vertex(int id, double lat, double lon, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.predecessor =  null;

        this.junction = junction;
    }

    public Vertex(int id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.predecessor = null;
        this.junction = false;
    }

    public int getId(){
        return id;
    }

    public int getCosts(){return costs;}

    public Vertex getPredecessor(){return predecessor;}

    public double getLat(){return lat;}

    public double getLon(){return  lon;}

    public boolean getJunction(){return junction;}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.getLat(), getLat()) == 0 && Double.compare(vertex.getLon(), getLon()) == 0 ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCosts(), getLat(), getLon(), getJunction());
    }
}
