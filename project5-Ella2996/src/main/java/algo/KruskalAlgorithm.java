package algo;

import graph.*;

import java.util.*;

import sets.*;
/** Subclass of MSTAlgorithm. Computes MST of the graph using Kruskal's algorithm. */
public class KruskalAlgorithm extends MSTAlgorithm {

    /**
     * Constructor for KruskalAlgorithm. Takes the graph
     * @param graph input graph
     */
    public KruskalAlgorithm(Graph graph) {
        super(graph);
    }

    /**
     * Compute minimum spanning tree for this graph. Add edges of MST to
     * edgesMST list. Should use Kruskal's algorithm and DisjointSets class.
     */
    @Override
    public void computeMST() {
        // FILL IN CODE
        int numNodes = numNodes();

        List<Edge> edges = new ArrayList<>();
        DisjointSets dSet = new DisjointSets();
        dSet.createSets(numNodes);
        for (int i = 0; i < numNodes; i++) {
            Edge cur = getFirstEdge(i);
            while (cur != null) {
                if (cur.getId1() < cur.getId2()) {
                    edges.add(cur);
                }
                cur = cur.next();
            }
        }

        Collections.sort(edges);
        int numEdges = edges.size();
        for (int i = 0; i < numEdges; i++) {
            Edge e = edges.get(i);
            int id1 = e.getId1();
            int id2 = e.getId2();
            if (dSet.find(id1) == dSet.find(id2)) {
                continue;
            }
            addMSTEdge(e);
            dSet.union(id1, id2);
        }
    }

}

