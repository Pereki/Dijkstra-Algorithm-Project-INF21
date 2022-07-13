package service.XmlElements;

import java.util.ArrayList;

/**
 * this class represents a way in an .osm file
 * @author i21017
 */

public class Way {
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public Node getNode(int index){
            return nodes.get(index);
    }

    public void removeNode(int index){
            nodes.remove(index);
    }

    public int size(){
        return nodes.size();
    }

    public void addNode(Node n){
        nodes.add(n);
    }
}
