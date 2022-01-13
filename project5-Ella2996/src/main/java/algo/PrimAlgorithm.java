package algo;

import graph.*;
import priorityQueue.MinHeap;

/** Subclass of MSTAlgorithm. Uses Prim's algorithm to compute MST of the graph. */
public class PrimAlgorithm extends MSTAlgorithm {
    class Table {
        public int cost;
        public int path;
    }
    private int sourceVertex;
    private boolean[] added;
    private Table[] table;
    private MinHeap minheap;

    /**
     * Constructor for PrimAlgorithm. Takes the graph
     * @param graph input graph
     * @param sourceVertex the first vertex of MST
     */
    public PrimAlgorithm(Graph graph, int sourceVertex) {
        super(graph);
        int numNodes = numNodes();
        minheap = new MinHeap(numNodes);
        this.sourceVertex = sourceVertex;
        added = new boolean[numNodes];
        table = new Table[numNodes];
        for (int i = 0; i < numNodes; i++) {
            table[i] = new Table();
            table[i].path = -1;
            table[i].cost = Integer.MAX_VALUE;
            if (i == sourceVertex) {
                table[i].cost = 0;
                minheap.insert(i, 0);
                continue;
            }
            minheap.insert(i, Integer.MAX_VALUE);
        }
    }

    /**
     * Compute minimum spanning tree for this graph using Prim's algorithm.
     * Add edges of MST to edgesMST list.
     * */
    @Override
    public void computeMST() {
        // FILL IN CODE
        // Note: must use a MinHeap and be efficient
        int numNodes = numNodes();
        for (int i = 0; i < numNodes; i++) {
            int idx = minheap.removeMin();
            added[idx] = true;
            if (table[idx].path != -1) {
                Edge e = new Edge(table[idx].path, idx, table[idx].cost);
                addMSTEdge(e);
            }
            Edge cur = getFirstEdge(idx);
            while (cur != null) {
                int nb = cur.getId2();
                if (!added[nb]) {
                    int c = cur.getCost();
                    if (c < table[nb].cost) {
                        table[nb].cost = c;
                        table[nb].path = idx;
                        minheap.reduceKey(nb, c);
                    }
                }
                cur = cur.next();
            }

        }

    }
}
