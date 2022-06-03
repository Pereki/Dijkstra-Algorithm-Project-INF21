package Services.XmlElements;

import java.util.ArrayList;

public class Way {
    private ArrayList<Node> nodes;

    public Node getAndDeleteFirstNode(){
        Node n = nodes.get(0);
        nodes.remove(0);

        return n;
    }

    public void addLastNode(Node n){
        nodes.add(n);
    }
}
