package problem6;

import java.util.ArrayList;
import java.util.List;

/** Problem 6:
 * Suppose we want to implement a hash table using closed hashing with double hashing.
 * Each entry in the table contains a key and a value, both of type int.
 * Consider class HashTableClosedHashingDH that implements a hash table using closed hashing and
 * double hashing as we discussed in class.
 * The class is different from the one in project 4 in the following ways:
 *
 * - Each HashEntry contains an integer key and an integer value
 * - The primary hash function h(k) is computed using the following formula
 *        h(k) = k % maxSize.
 * - The secondary function is computed using the following formula:
 *       d(k) =   q -  k % q,  where q is the largest prime less than the maxSize
 * -	We do not support deletion (and hence do not store a "deleted" flag in the HashEntry).
 * - The put method returns a list of indices that it probed while searching for the available index
 * (it should include the index it eventually found).
 * - There is no rehashing , but you do not need to handle the case when the table is full.
 * You can probe at most maxSize*maxSize times.
 *
 * Fill in the code in methods containsKey and put.
 * The containsKey method returns true if the key is in the table, and false otherwise.
 * The put method inserts key, value into the table (using double hashing),
 * and returns a list of indices in the table that were "tried" ("probed")
 * while searching for an available index. Please refer to the pdf for the example.
 *
 * Your algorithm must use closed hashing with double hashing. Your code must be efficient.
 * The main method calls put and containsKey and explains what the expected result should look like.
 * Your code must be general.
 * You do not need to handle the case when the table is full and you do not need to perform rehashing.
 * */
public class HashTableClosedHashingDH {
    private HashEntry[] table; // hash table
    private int maxSize; // size of the array

    /** Constructor for class HashTableClosedHashingDH.
     *  Creates a hash table of the given size.
     * @param size maximum number of elements the hash table can store
     */
    public HashTableClosedHashingDH(int size) {
        maxSize = size;
        table = new HashEntry[maxSize];
    }

    /** Primary Hash function h(k)
     *
     * @param key key
     * @return the index in the table where this key hashes
     * Do not change this method.
     */
    public int h(int key) {
        return key % maxSize;
    }

    /**
     * Secondary hash function d(k)
     * @param key key
     * @return q - (key % q)
     * where q is the largest prime less than maxSize.
     * Do not change this method.
     */
    public int d(int key) {
        // generate a prime number less than maxSize
        int q = generatePrime(maxSize);
        return q - key % q;
    }

    /** Return true if the map contains a (key, value) pair associated with this key,
     *  otherwise return false.
     *
     * @param key  key
     * @return true if the key (and the corresponding value) is the in map
     */
    public boolean containsKey(int key) {
        // FILL IN CODE
        int hk = h(key); // primary hash function for this key
        int dk = d(key); // secondary hash function for this key
        // FILL IN CODE
        for (int i = 0; i < maxSize; i++) {
            int fk = (hk + i * dk) % maxSize;
            if (table[fk] == null)
                return false;
            if (table[fk].getKey() == key) {
                return true;
            }
        }



        return false; // if could not find it
    }

    /** Add (key, value) to the map.
     *  Return a list of indices we probed while searching for the available index.
     *  Probes at most maxSize*maxSize times.
     * @param key key
     * @param value associated value
     * @return a list of indices we tried
     */
    public List<Integer> put(int key, int value) {
        List<Integer> listOfIndices = new ArrayList<>();
        int hk = h(key);
        int dk = d(key);
        // FILL IN CODE
        for (int i = 0; i < maxSize; i++) {
            int fk = (hk + i * dk) % maxSize;
            listOfIndices.add(fk);
            if (table[fk] == null) {
                table[fk] = new HashEntry(key, value);
                return listOfIndices;
            }
            if (table[fk].getKey() == key) {
                table[fk].setValue(value);
                return listOfIndices;
            }
        }
        return listOfIndices;
    }

    /**
     * Return a string representing the table, showing the (key, value) stored at each index
     * @return string
     * Do not change this method.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxSize; i++ ) {
            if (table[i] != null) {
                sb.append(i + ": ");
                sb.append("(" + table[i].getKey() + ", "  + table[i].getValue() + ")");
                sb.append(System.lineSeparator());
            }
            else {
                sb.append(i + ": ");
                sb.append("null");
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /** Computes the largest prime < maxSize. Used in d(k).
     * Do not modify this method. */
    private static int generatePrime (int maxValue) {
        // generate the largest prime less than maxValue
        if (maxValue == 1)
            return 0;
        if (maxValue == 2)
            return 1;
        int prev = 0;
        int curr = 1;
        while (curr < maxValue) {
            if (curr == 2) {
                curr++;
                continue;
            }
            boolean flag = true;
            for (int i = 2; i < curr; i++) {
                if (curr % i == 0){ // divides  by some number
                    flag = false;
                    break;
                }
            }
            if (flag)
                prev = curr;
            curr++;

        }
        return prev;
    }

    public static void main(String[] args) {
        HashTableClosedHashingDH hashTable = new HashTableClosedHashingDH(7);

        System.out.println("Test the put method:");
        // To keep it simple, I made the keys and values the same in this example
        System.out.println(hashTable.put(5, 5)); // Should print: [5]
        System.out.println(hashTable.put(1, 1)); // Should print: [1]
        System.out.println(hashTable.put(12, 12)); // Should print: [5, 1, 4]
        System.out.println(hashTable.put(8, 8)); // Should print: [1, 3]
        System.out.println(hashTable.put(17, 17)); // Should print [3, 6]
        System.out.println(hashTable.put(25, 25)); // Should print [4, 2]

        System.out.println("Resulting table: ");
        System.out.println(hashTable);
        /* Should print:
        0: null
        1: (1, 1)
        2: (25, 25)
        3: (8, 8)
        4: (12, 12)
        5: (5, 5)
        6: (17, 17)
        */

        // Test containsKey
        System.out.println("Test containsKey:");
        System.out.println(hashTable.containsKey(12)); // should print true
        System.out.println(hashTable.containsKey(25)); // should print true
        System.out.println(hashTable.containsKey(0)); // should print false
        System.out.println(hashTable.containsKey(2)); // should print false
    }
}
