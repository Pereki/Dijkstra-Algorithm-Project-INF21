package Services.XmlElements;

import java.util.ArrayList;

public class Way {
    private ArrayList<Node> nodes;

    public Node getNode(){
        if(!nodes.isEmpty()){
            return nodes.get(0);
        }else{
            return null;
        }
    }

    public void removeNode(){
        if(!nodes.isEmpty()){
            nodes.remove(0);
        }
    }

    public void addNode(Node n){
        nodes.add(n);
    }
}
