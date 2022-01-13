package hashTable.openHashing;
import hashTable.HashEntry;
import hashTable.Map;

import java.math.BigInteger;

public class HashTableOpenHashing implements Map {
    private Node[] table ;
    private int maxSize;
    private int size;

    /** Constructor for class HashTableOpenHashing
     *
     * @param maxSize the size of the table
     */
    public HashTableOpenHashing(int maxSize) {
        // FILL IN CODE
        this.maxSize = maxSize;
        this.size = 0;
        table = new Node[maxSize];
    }

    /** Return true if the map contains a (key, value) pair associated with this key,
     *  otherwise return false.
     *
     * @param key  key
     * @return true if the key (and the corresponding value) is the in map
     */
    public boolean containsKey(String key) {
        return (get(key) != null);
    }


    /** Add (key, value) to the map.
     * Will replace previous value that this key was mapped to.
     * If key is null, throw IllegalArgumentException.
     *
     * @param key
     * @param value associated value
     */
    public void put(String key, Object value) {
        if (key == null)
            throw new IllegalArgumentException();

        // replace previous key if exists
        if (containsKey(key)) {
            int keyInt = hashFunc(key);
            Node cur = table[keyInt];
            while (cur != null) {
                if (cur.entry().getKey().equals(key)) {
                    cur.entry().setValue(value);
                    return;
                }
                cur = cur.next();
            }
        }

        if ((float)(size) / maxSize > 0.6) {
            // need to find the smallest prime number;
            int oriMaxSize = maxSize;
            maxSize = findNextPrimeNumber(2 * maxSize + 1);
            HashTableOpenHashing newTable = new HashTableOpenHashing(maxSize);
            for (int i = 0; i < oriMaxSize; i++) {
                Node cur = table[i];
                while (cur != null) {
                    newTable.put(cur.entry().getKey(), cur.entry().getValue());
                    cur = cur.next();
                }
            }
            table = newTable.table;
            // need to rehashing
        }
            // FILL IN CODE
        size++;
        int keyHash = hashFunc(key);
        HashEntry newEntry = new HashEntry(key, value);
        Node newNode = new Node(newEntry);
        newNode.setNext(table[keyHash]);
        table[keyHash] = newNode;

    }

    /** Return the value associated with the given key or null, if the map does not contain the key.
     * If the key is null, throw IllegalArgumentException.
     *
     * @param key key
     * @return value associated value
     */
    public Object get(String key) {
        if (key == null) return null;
        int keyInt = hashFunc(key);
        Node cur = table[keyInt];
        while (cur != null) {
            if (cur.entry().getKey().equals(key)) {
                return cur.entry().getValue();
            }
            cur = cur.next();
        }

        return null;
    }

    /** Remove a (key, value) entry if it exists.
     * Return the previous value associated with the given key, otherwise return null
     * @param key key
     * @return previous value
     */
    public Object remove(String key) {
        if (containsKey(key) == false)
            return null;
        size--;
        int keyHash = hashFunc(key);
        Node dummy = new Node(null);
        dummy.setNext(table[keyHash]);
        Node cur = dummy;
        while (cur.next() != null) {
            if (cur.next().entry().getKey().equals(key)) {
                Object ret = cur.next().entry().getValue();
                cur.setNext(cur.next().next());
                table[keyHash] = dummy.next();
                return ret;
            }
            cur = cur.next();
        }
        return null; // change
    }

    /** Return the actual number of elements in the map.
     *
     * @return number of elements currently in the map.
     */
    public int size() {
        return this.size;
    }

    /**
     * toString
     * @return a string representing a hash table
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxSize; i++) {
            sb.append(i + ": ");
            Node cur = table[i];
            if (cur == null) {
                sb.append("null\n");
                continue;
            }
            while (cur != null) {
                sb.append(cur.entry());
                cur = cur.next();
                if (cur != null) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        // FILL IN CODE

        return sb.toString();
    }

    /** find next prime number >= provided value
     * @param cur current key
     * @return next prime number
     */
    private int findNextPrimeNumber(int cur) {
        while (true) {
            boolean isPrime = true;
            for (int i = 2; i <= cur / 2; i++) {
                if (cur % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime)
                return cur;
            cur++;
        }
    }

    /** compute the hash for the key
     * @param key key
     * @return hash of the key
     */
    private int hashFunc(String key) {
        BigInteger a = BigInteger.valueOf(33);
        int numC = key.length();
        BigInteger keyInt = BigInteger.ZERO;
        for (int i = 0; i < numC; i++) {
            int c = (int) key.charAt(i);
            BigInteger bint = BigInteger.valueOf(c);
            keyInt = keyInt.multiply(a);
            keyInt = keyInt.add(bint);
        }
        keyInt = keyInt.mod(BigInteger.valueOf(maxSize));
        return keyInt.intValue();
    }



    public static void main(String[] args) {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        System.out.println(map);
        boolean b1 = map.containsKey("one");
        System.out.println(b1);
        boolean b2 = map.containsKey("three");
        System.out.println(b2);
        boolean b3 = map.containsKey("five");
        System.out.println(b3);
        boolean b4 = map.containsKey("seven");
        System.out.println(b4);

    }
    // Add may implement other helper methods as needed


}
