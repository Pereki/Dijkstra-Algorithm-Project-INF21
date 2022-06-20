package service.XmlElements;

import java.util.ArrayList;

public class Way {
    private ArrayList<Node> nodes;

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
