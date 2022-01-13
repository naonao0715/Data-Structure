package problem4;

/** Problem 4.
 *  A class that implements a binary search tree.
 *  Implement the method find  (that searches for a given key) iteratively, without recursion.
 *  This is different from the implementation we wrote in class.
 *  The method must be efficient and should return true if the key is in the tree and
 *  false otherwise.
 *  Also, the method should print the values of the nodes it visited during the search.
 *  Please refer to the example in the pdf.
 *  The instructor created this BST tree in the main method, so that you could test your code
 *  on this tree. Note that your code must be general and work on any BST.
 * Important: the method must be non-recursive; no credit if you write it recursively.
 */
class BinarySearchTree {

    private BSTNode root; // the root of the tree

    /**
     * An inner class representing a node in a BST tree
     */
    private class BSTNode {
        public int data; // value stored at the node
        public BSTNode left; // left subtree
        public BSTNode right;  // right subtree

        BSTNode(int newData) {
            data = newData;
        }
    }

    /** Returns true if the given element is in the tree and false otherwise.
     *  Must be non-recursive and efficient.
     *  Should print the elements of the nodes visited during the search
     * @param tree
     * @param elem
     * @return
     */
    private boolean find(BSTNode tree, int elem) {
        BSTNode curr = tree;
        // FILL IN CODE: do NOT use recursion; implement using a loop.
        while (curr != null) {
            System.out.println(curr.data);
            if (curr.data == elem) {
                return true;
            }
            if (elem > curr.data) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }

        return false;
    }

    /** Return true if a given element is in the tree */
    public boolean find(int elem) {
        return find(root, elem);
    }

    /** Insert a given element into the BST tree */
    public void insert(int elem) {
        root = insert(root, elem);
    }

    /** Insert elem into the tree with the given root.
     *  Do not modify. */
    private BSTNode insert(BSTNode tree, int elem) {
        if (tree == null) {
            return new BSTNode(elem);
        }
        if (elem < tree.data) {
            tree.left = insert(tree.left, elem);
            return tree;
        } else {
            tree.right = insert(tree.right, elem);
            return tree;
        }
    }

    public static void main(String[] args) {
        // The tree is shown in the pdf
        // Note that your code should be general and work on any BST.
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(5);
        bst.insert(10);
        bst.insert(3);
        bst.insert(1);
        bst.insert(9);
        bst.insert(2);
        bst.insert(11);
        bst.insert(8);
        bst.insert(0);

        // Test 1: check if we can find 2 and what nodes we visit
        System.out.println("Test 1: find (2) ");
        System.out.println(bst.find(2));
        System.out.println();
        /* Should print:
        5
        3
        1
        2
        true
         */

        // Test 2: check if we can find 12 and what nodes we visit
        System.out.println("Test 2: find (12) ");
        System.out.println(bst.find(12));
        /* Should print:
        5
        10
        11
        false
         */

    }
}
