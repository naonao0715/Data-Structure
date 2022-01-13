package problem8;

// Problem 8:

import java.security.spec.ECGenParameterSpec;

/** Assume the graph is represented using the adjacency list.
 * Fill in the code in class Graph in the method hasPathFromV1toV2 where you use
 * non-recursive DFS (Depth First Search) to determine if there is a path from
 * vertex v1 to vertex v2, and print the discovery time of v2 (assuming the discovery time of v1 is 0).
 * Note: the path may consist of several edges.
 * You do not need to print the actual path, just determine if it exists.
 * You are expected to use the provided starter code and implement DFS using a stack
 * (non-recursively).
 * See the example in the pdf. The instructor created this graph in the main method of class Graph,
 * so that you could test your code. Remember, that your code should be general, and work on any graph.*/
public class Graph {
    private Edge[] graph; // adjacency list for this graph

    public Graph(int numVertices) {
        graph = new Edge[numVertices];
    }

    /**
     * Adds the given edge as an outgoing edge for the given vertex.
     * Modifies the adjacency list.
     * @param vertexId  id of the vertex
     * @param edge outgoing edge
     * Do not modify.
     */
    public void addEdge(int vertexId, Edge edge) {
        Edge head = graph[vertexId]; // head of the linked list for this  node
        graph[vertexId] = edge; // insert in front
        if (head != null) {
            edge.next = head;
        }
    }

    /**
     * Use DFS (Depth First Search) to determine if there is a path from v1 to v2,
     * and print the discovery time of v2.
     * Note: the path may consist of several edges. You do not need to print the actual path,
     * just determine if it exists.
     * @param v1 id of the source vertex
     * @param v2 if of the destination vertex
     */
    public boolean hasPathFromV1toV2(int v1, int v2) {
        boolean[] visited = new boolean[graph.length]; // initialized to false
        int[] discoveryTimes = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            discoveryTimes[i] = -1;
        }
        Stack stack = new ArrayStack();
        stack.push(v1);
        int dTime = 0;
        while (!stack.empty()) {
            int s = (Integer) (stack.pop());

            if (visited[s] == false) {
                visited[s] = true;
                discoveryTimes[s] = dTime;
                dTime++;
            }

            if (s == v2) {
                System.out.println("The discovery time is " + discoveryTimes[v2]);
                return true;
            }

            Edge cur = graph[s];
            while (cur != null) {
                int nb = cur.neighbor;
                if (!visited[nb]) {
                    stack.push(nb);
                }
                cur = cur.next;
            }
        }
        return false;


    }

    // Static nested class Edge
    public static class Edge { // Class Edge
        private int neighbor; // id of the neighbor
        private Edge next; // reference to the next "edge"

        public Edge(int neighbor) {
            this.neighbor = neighbor;
            next = null;
        }
    } // class Edge

    public static void main(String[] args) {
        // You can test your code on the following graph (shown in the pdf).
        // Note that your code should be general and work on any graph
        Graph g = new Graph(8);

        // edges going out of vertex 1
        Edge edge10 = new Edge(0);
        Edge edge12 = new Edge(2);
        g.addEdge(1, edge10);
        g.addEdge(1, edge12);

       // edge going out of 0
        Edge edge05 = new Edge(5);
        g.addEdge(0, edge05);

        //edge going out of 2
        Edge edge26 = new Edge(6);
        g.addEdge(2, edge26);

        // edges going out of 5
        Edge edge54 = new Edge(4);
        Edge edge56 = new Edge(6);
        g.addEdge(5, edge54);
        g.addEdge(5, edge56);

        // edge going out of 6
        Edge edge67 = new Edge(7);
        g.addEdge(6, edge67);

        //edge going out of 4
        Edge edge47 = new Edge(7);
        g.addEdge(4, edge47);

        // edge going out of 7
        Edge edge75 = new Edge(5);
        g.addEdge(7, edge75);

        // Tests you can use to test your code:
        System.out.println(g.hasPathFromV1toV2(1, 4));
        System.out.println();
        /* Should print:
         The discovery time is 3
         true
         */

        System.out.println(g.hasPathFromV1toV2(1, 7));
        System.out.println();
        /* Should print:
         The discovery time is 4
         true
         */

        System.out.println(g.hasPathFromV1toV2(6, 4));
        System.out.println();
        /* Should print:
         The discovery time is 3
         true
         */

        System.out.println(g.hasPathFromV1toV2(2, 4));
        System.out.println();
        /* Should print:
         The discovery time is 4
         true
         */

        System.out.println(g.hasPathFromV1toV2(0, 2)); // Should print false
        System.out.println(g.hasPathFromV1toV2(2, 0)); // Should print false
        System.out.println(g.hasPathFromV1toV2(7, 1)); // Should print false
        System.out.println(g.hasPathFromV1toV2(4, 1)); // Should print false

    }
}
