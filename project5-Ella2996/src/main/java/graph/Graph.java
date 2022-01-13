package graph;

import org.junit.Assert;
import userInterface.GUIApp;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a graph: stores the array of city nodes, the
 * adjacency list, as well as the hash table that maps city names to node ids.
 * Nodes are cities (of type CityNode); edges connect them and the cost of each edge
 * is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a MinHeap from scratch, instead of using Java's built in PriorityQueue.
 */
public class Graph {
    private CityNode[] nodes; // nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private int numEdges; // total number of edges
    private Map<String, Integer> cityToId;
    // Add other variable(s) as needed:
    // add a HashMap to map cities to vertexIds.

    /**
     * Constructor. Read graph info from the given file,
     * and create nodes and edges of the graph.
     *
     *   @param filename name of the file that has nodes and edges
     */
    public Graph(String filename) {
        // FILL IN CODE
        cityToId = new HashMap<>();
        try  (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String s;
            s = br.readLine(); // Nodes
            s = br.readLine(); // number of nodes
            int numNodes = Integer.valueOf(s);
            nodes = new CityNode[numNodes];
            adjacencyList = new Edge[numNodes];
            for (int i = 0; i < numNodes; i++) {
                s = br.readLine();
                String[] city = s.split(" ");
                nodes[i] = new CityNode(city[0], Double.valueOf(city[1]), Double.valueOf(city[2]));
                cityToId.put(city[0], i);
            }
            s = br.readLine(); // ARCS
            // start to build the edges
            numEdges = 0;
            while ((s = br.readLine()) != null) {
                numEdges += 2;
                String[] edge = s.split(" ");
                int sourceId = cityToId.get(edge[0]);
                int targetId = cityToId.get(edge[1]);
                int cost = Integer.valueOf(edge[2]);
                Edge e1 = new Edge(sourceId, targetId, cost);
                Edge e2 = new Edge(targetId, sourceId, cost);
                e1.setNext(adjacencyList[sourceId]);
                adjacencyList[sourceId] = e1;
                e2.setNext(adjacencyList[targetId]);
                adjacencyList[targetId] = e2;
            }
        }
        catch (IOException e) {
            Assert.fail("IOException occured while reading from the file: " + e);
        }
    }

    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return nodes.length;
    }

    /** Return the head of the linked list that contains all edges outgoing
     * from nodeId
     * @param nodeId id of the node
     * @return head of the linked list of Edges
     */
    public Edge getFirstEdge(int nodeId) {
        return adjacencyList[nodeId];
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        if (adjacencyList == null || adjacencyList.length == 0) {
            System.out.println("Adjacency list is empty. Load the graph first.");
            return null;
        }
        Point[][] edges2D = new Point[numEdges][2];
        int idx = 0;
        for (int i = 0; i < adjacencyList.length; i++) {
            for (Edge tmp = adjacencyList[i]; tmp != null; tmp = tmp.next(), idx++) {
                edges2D[idx][0] = nodes[tmp.getId1()].getLocation();
                edges2D[idx][1] = nodes[tmp.getId2()].getLocation();
            }
        }

        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (nodes == null) {
            System.out.println("Array of nodes is empty. Load the graph first.");
            return null;
        }
        Point[] nodes = new Point[this.nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = this.nodes[i].getLocation();
        }

        return nodes;
    }

    /**
     * Used in GUIApp to display the names of the cities.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (nodes == null) {
            return null;
        }
        String[] labels = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            labels[i] = nodes[i].getCity();
        }

        return labels;

    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }

    public static void main(String[] args) {
        Graph graph = new Graph("/Users/qianyunwang/Documents/data_structure/project5-Ella2996/input/USA.txt");
        Point[][] p = graph.getEdges();
        System.out.println("hello world");
    }


}
