package dictionary;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** CompactPrefixTree class, implements Dictionary ADT and
 *  several additional methods. Can be used as a spell checker.
 *  Fill in code and feel free to add additional methods as needed.
 *  S21 */
public class CompactPrefixTree implements Dictionary {

    private Node root; // the root of the tree

    /** Default constructor  */
    public CompactPrefixTree() {
        root = new Node();
    }

    /**
     * Creates a dictionary ("compact prefix tree")
     * using words from the given file.
     * @param filename the name of the file with words
     */
    public CompactPrefixTree(String filename) {
        // FILL IN CODE:
        // Read each word from the file, add it to the tree
        root = new Node();
        try  (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String s;
            while ((s = br.readLine()) != null) {
                String word = s.strip();
                add(word);
            }
        }
        catch (IOException e) {
            Assert.fail("IOException occured while reading from the file: " + e);
        }

    }

    /** Adds a given word to the dictionary.
     * @param word the word to add to the dictionary
     */
    public void add(String word) {
        if (word.isEmpty()) return;
        int idx = word.charAt(0) - 'a';
        root.children[idx] = add(word.toLowerCase(), root.children[idx]); // Calling private add method
    }

    /**
     * Checks if a given word is in the dictionary
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean check(String word) {
        return check(word.toLowerCase(), root); // Calling private check method
    }

    /**
     * Checks if a given prefix is stored in the dictionary
     * @param prefix The prefix of a word
     * @return true if this prefix is a prefix of any word in the dictionary,
     * and false otherwise
     */
    public boolean checkPrefix(String prefix) {
        if (prefix.isEmpty()) return true;
        int idx = prefix.charAt(0) - 'a';
        return checkPrefix(prefix.toLowerCase(), root.children[idx]); // Calling private checkPrefix method
    }

    /**
     * Returns a human-readable string representation of the compact prefix tree;
     * contains nostdes listed using pre-order traversal and uses indentations to show the level of the node.
     * An asterisk after the node means the node's boolean flag is set to true.
     * The root is at the current indentation level (followed by * if the node's valid bit is set to true),
     * then there are children of the node at a higher indentation level.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // FILL IN CODE
        getString(root, sb, -1);
        return sb.toString();
    }

    /**
     * Print out the nodes of the tree to a file, using indentations to specify the level
     * of the node.
     * @param filename the name of the file where to output the tree
     */
    public void printTree(String filename) {
        // FILL IN CODE
        // Uses toString() method; outputs info to a file
        // FILL IN CODE
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Return an array of the entries in the dictionary that are as close as possible to
     * the parameter word.  If the word passed in is in the dictionary, then
     * return an array of length 1 that contains only that word.  If the word is
     * not in the dictionary, then return an array of numSuggestions different words
     * that are in the dictionary, that are as close as possible to the target word.
     * Implementation details are up to you, but you are required to make it efficient
     * and make good use ot the compact prefix tree.
     *
     * @param word The word to check
     * @param numSuggestions The length of the array to return.  Note that if the word is
     * in the dictionary, this parameter will be ignored, and the array will contain a
     * single world.
     * @return An array of the closest entries in the dictionary to the target word
     */

    public String[] suggest(String word, int numSuggestions) {
        if (check(word)) {
            return new String[] {word };
        }
        int idx = word.charAt(0) - 'a';

        ArrayList<String> arr = new ArrayList<>();
        String curWord = "";
        suggest(word, root.children[idx], numSuggestions, arr, curWord);

        //Convert to string array
        String[] array = arr.toArray(new String[arr.size()]);

        return array;
        // FILL IN CODE
        // Note: you need to create a private suggest method in this class
        // (like we did for methods add, check, checkPrefix)


    }
    /** A private method to get string from a node
     *
     * @param word
     * @param root root of tree
     * @param numSuggestions
     * @param arr store the suggestion results
     */
    private boolean suggest(String word, Node root, int numSuggestions, ArrayList<String> arr, String curWorld) {
        if (arr.size() == numSuggestions) return true;
        if (root == null) return false;
        curWorld += root.prefix; // always update curWorld with root

        // when the longest common prefix is found, can add any words
        if (word.isEmpty() || root.prefix.startsWith(word) || !word.startsWith(root.prefix)) {
            if (root.isWord) {
                arr.add(curWorld);
            }
            for (int i = 0; i < root.children.length; i++) {
                // early return if getting enough words
                if (suggest("", root.children[i], numSuggestions, arr, curWorld))
                    return true;
            }
        }

        // looking for longest common prefix
        if (word.startsWith(root.prefix)) {
            String suffix = word.substring(root.prefix.length());
            int idx = suffix.charAt(0) - 'a';
            if (suggest(suffix, root.children[idx], numSuggestions, arr, curWorld))
                return true;

            if (root.isWord) {
                arr.add(curWorld);
                if (arr.size() == numSuggestions)
                    return true;
            }

            // add more words in case not enough words with longest common prefix
            for (int i = 0; i < root.children.length; i++) {
                if (i == idx) continue;
                if (suggest(suffix, root.children[i], numSuggestions, arr, curWorld))
                    return true;
            }
        }

        return false;
    }
    /** A private method to get string from a node
     *
     * @param root the root of a tree
     * @param sb StringBuilder
     * @param level current level of tree
     */
    private void getString(Node root, StringBuilder sb, int level) {
        if (root == null) return;
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        sb.append(root.prefix);
        if (root.isWord) {
            sb.append("*");
        }
        sb.append("\n");
        for (int i = 0; i < root.children.length; i++) {
            getString(root.children[i], sb, level + 1);
        }
    }
        /**
         *  A private add method that find longest Common Prefix between 2 words
         * @param word1
         * @param word2

         * @return longest Common Prefix string
         */
    private String longestCommonPrefix(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int i = 0;
        for (; i < Math.min(len1, len2); i++) {
            if (word1.charAt(i) != word2.charAt(i))
                break;
        }
        return word1.substring(0, i);
    }

    // ---------- Private helper methods ---------------

    /**
     *  A private add method that adds a given string to the tree
     * @param s the string to add
     * @param node the root of a tree where we want to add a new string

     * @return a reference to the root of the tree that contains s
     */
    private Node add(String s, Node node) {
        // FILL IN CODE

        if (node == null) {
            Node newWord = new Node();
            newWord.prefix = s;
            newWord.isWord = true;
            return newWord;
        }

        if (s.equals(node.prefix)) {
            node.isWord = true;
            return node;
        }
        // s: p node: ple
        // example: ham, hamster
        if (node.prefix.startsWith(s)) {
            // create a new node for p
            Node root = new Node();
            root.prefix = s; // p
            root.isWord = true;

            // change ple to le here
            String suffix = node.prefix.substring(s.length());
            int idx = suffix.charAt(0) - 'a';
            root.children[idx] = node;
            node.prefix = suffix;
            return root;
        }
        // example: hamster, ham
        if (s.startsWith(node.prefix)) {
            String suffixWord = s.substring(node.prefix.length());
            int idxWord = suffixWord.charAt(0) - 'a';
            node.children[idxWord] = add(suffixWord, node.children[idxWord]);
            return node;
        }
        // pointment ple
                // hamp hamsterw
        String prefix = longestCommonPrefix(s, node.prefix); // p
        String suffix = node.prefix.substring(prefix.length()); // le
        String suffixWord = s.substring(prefix.length()); //ointment

        Node root = new Node();
        root.prefix = prefix; // p

        int idx = suffix.charAt(0) - 'a'; // 'l'
        root.children[idx] = node;
        node.prefix = suffix; //

        int idxWord = suffixWord.charAt(0) - 'a';
       // root.children[idxWord] = add(suffixWord, root.children[idxWord]); // ointment,
        Node child = new Node();
        child.prefix = suffixWord;
        child.isWord = true;
        root.children[idxWord] = child;

        return root;


    }


    /** A private method to check whether a given string is stored in the tree.
     *
     * @param s the string to check
     * @param node the root of a tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean check(String s, Node node) {
        // FILL IN CODE
        if (node == null) return false;
        if (!s.startsWith(node.prefix)) return false;
        if (s.equals(node.prefix)) return node.isWord;

        String suffix = s.substring(node.prefix.length());
        int childIdx = (suffix.charAt(0) - 'a');
        return check(suffix, node.children[childIdx]);
    }

    /**
     * A private recursive method to check whether a given prefix is in the tree
     *
     * @param prefix the prefix
     * @param node the root of the tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean checkPrefix(String prefix, Node node) {
        // FILL IN CODE
        if (prefix.isEmpty()) return true;
        if (node == null) return false;

        if (prefix.equals(node.prefix)) return true;
        // hamburge ham
        if (prefix.startsWith(node.prefix)) {
            String suffix = prefix.substring(node.prefix.length());
            int idx = suffix.charAt(0) - 'a';
            return checkPrefix(suffix, node.children[idx]);
        }
        // ham hamburge
        return node.prefix.startsWith(prefix);
    }

    // You might want to create a private recursive helper method for toString
    // that takes the node and the number of indentations, and returns the tree  (printed with indentations) in a string.
    // private String toString(Node node, int numIndentations)


    // Add a private suggest method. Decide which parameters it should have

    // --------- Private class Node ------------
    // Represents a node in a compact prefix tree
    private class Node {
        String prefix; // prefix stored in the node
        Node children[]; // array of children (26 children)
        boolean isWord; // true if by concatenating all prefixes on the path from the root to this node, we get a valid word

        Node() {
            isWord = false;
            prefix = "";
            children = new Node[26]; // initialize the array of children
        }

        // FILL IN CODE: Add other methods to class Node as needed
    }

}
