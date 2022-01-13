package priorityQueue;

import java.awt.*;

/** A priority queue: represented by the min heap.
 *  Used in Prim's algorithm. */
public class MinHeap {
    private int size;
    private int[] positions;
    private Point[] heap;
    /**
     * Swap two elements in the heap
     * @param i ith element
     * @param j jth element
     */
    private void swap(int i, int j) {

        positions[heap[i].x] = j;
        positions[heap[j].x] = i;
        Point tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;

    }
    public MinHeap(int numNode) {
        size = 0;
        positions = new int[numNode];
        heap = new Point[numNode + 1];
        heap[0] = new Point(-1, -1);
    }
    // FILL IN CODE
    /**
     * Insert node Id with priority to the heap
     * @param nodeId
     * @param priority
     */
    public void insert(int nodeId, int priority) {
        size++;
        heap[size] = new Point(nodeId, priority);
        positions[nodeId] = size;
        int current = size;
        while (heap[current].y < heap[current / 2].y) {
            swap(current, current / 2);
            current = current / 2;
        }
    }

    /**
     * Remove element with min cost
     * @return return min element node id
     */
    public int removeMin() {
        swap(1, size);
        size--;

        if (size != 0) {
            int current = 1;
            while (2 * current <= size) {
                int minChild = 2 * current;
                if ((2 * current + 1 <= size) && (heap[2 * current + 1].y < heap[2 * current].y)){
                    minChild = 2 * current + 1;
                }
                if (heap[current].y < heap[minChild].y)
                    break;
                swap(current, minChild);
                current = minChild;
            }
        }
        return heap[size + 1].x;
    }

    /**
     * Reduce given node Id priority
     * @param nodeId
     * @param newPriority new priority
     */
    public void reduceKey(int nodeId, int newPriority) {
        int pos = positions[nodeId];
        heap[pos].y = newPriority;
        int current = pos;
        while (heap[current].y < heap[current / 2].y) {
            swap(current, current / 2);
            current = current / 2;
        }
    }


}
