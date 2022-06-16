package Services.XmlElements;

import java.util.ArrayList;

public class Way {
    private ArrayList<Node> nodes;

    public Node getNode(){
        return nodes.get(0);
    }

    public void removeNode(){
        nodes.remove(0);
    }

    public void addNode(Node n){
        nodes.add(n);
    }
}
