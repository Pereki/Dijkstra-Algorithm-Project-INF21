package Model;//represents a node in osm xml

import java.io.Serializable;
import java.util.Objects;

public class Vertex implements Serializable {
    private int id;
    private int costs = 0;
    private Vertex precessor;
    private double lat;//Breitengrad
    private double lon;//Längengrad
    private String identifier;//Bezeichner
    private boolean junction;//anschlussstelle

    public Vertex(int id, double lat, double lon, Vertex precessor, String identifier, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor =  precessor;
        this.identifier = identifier;
        this.junction = junction;
    }
    public Vertex(int id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor = null;
        this.identifier = null;
        this.junction = false;
    }

    public int getId(){
        return id;
    }

    public int getCosts(){return costs;}

    public Vertex getPrecessor(){return precessor;}

    public double getLat(){return lat;}

    public double getLon(){return  lon;}

    public String getIdentifier(){return identifier;}

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
        return Objects.hash(getId(), getCosts(), getLat(), getLon(), getIdentifier(), getJunction());
    }
}
