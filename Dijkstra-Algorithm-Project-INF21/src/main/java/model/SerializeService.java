package model;

import java.io.*;

public abstract class SerializeService {

    /**
     * save the Graph to a specified path by use of serialisation.
     * @param graph the Graph to save.
     * @param path  the path to which the Graph is saves
     * @throws IOException throw when path is not accessible
     */
    public static void saveGraph(Graph graph, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(graph);
        oos.flush();
        oos.close();
    }

    /**
     * load a File and deserialize it to a Graph.
     * @param path the path to the serialized Graph
     * @return  a Graph
     * @throws IOException throw when path ist not accessible
     * @throws ClassNotFoundException throw when the file don't contain a serialized Graph.
     */
    public static Graph loadGraph(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Graph graph = (Graph) ois.readObject();
        ois.close();
        return graph;
    }
}
