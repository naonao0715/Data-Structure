package hashTable.closedHashing;

import hashTable.HashEntry;
import hashTable.Map;
import hashTable.openHashing.Node;

import java.math.BigInteger;

/** The class that implements the Map interface using closed hashing;
 *  uses linear probing to resolve collisions */
public class HashTableClosedHashingLP implements Map {
    private HashEntry[] table; // hash table
    private int maxSize; //
    private int size; // the number of elements currently in the hash table


    /** Constructor for class HashTableClosedHashingLP.
     *  Creates a hash table of the given size.
     * @param maxSize maximum number of elements the hash table can store
     */
    public HashTableClosedHashingLP(int maxSize) {
        // FILL IN CODE
        this.maxSize = maxSize;
        table = new HashEntry[maxSize];
        size = 0;

    }

    /** Return true if the map contains a (key, value) pair associated with this key,
     *  otherwise return false.
     *
     * @param key  key
     * @return true if the key (and the corresponding value) is the in map
     */
    @Override
    public boolean containsKey(String key) {
        // FILL IN CODE

        return (get(key) != null);
    }

    /** Add (key, value) to the map.
     * Will replace previous value that this key was mapped to.
     * If key is null, throw IllegalArgumentException.
     *
     * @param key
     * @param value associated value
     */
    @Override
    public void put(String key, Object value) {
        if (containsKey(key)) {
            int keyInt = hashFunc(key);
            for (int i = 0; i < maxSize; i++) {
                int idx = (i + keyInt) % maxSize;
                if (table[idx] == null)
                    return ;
                if (table[idx].isDeleted())
                    continue;
                if (table[idx].getKey().equals(key)) {
                    table[idx].setValue(value);
                    return;
                }
            }
        }

        if ((float)(size) / maxSize > 0.6) {
            // need to find the smallest prime number;
            int oriMaxSize = maxSize;
            maxSize = findNextPrimeNumber(2 * maxSize + 1);
            HashTableClosedHashingLP newTable = new HashTableClosedHashingLP(maxSize);
            for (int i = 0; i < oriMaxSize; i++) {
                HashEntry cur = table[i];
                if (cur == null)
                    continue;
                if (cur.isDeleted())
                    continue;
                newTable.put(cur.getKey(), cur.getValue());
            }
            table = newTable.table;
        }
        // FILL IN CODE
        size++;
        int keyInt = hashFunc(key);
        for (int i = 0; i < maxSize; i++) {
            int idx = (i + keyInt) % maxSize;
            if (table[idx] == null || table[idx].isDeleted()) {
                HashEntry he = new HashEntry(key, value);
                table[idx] = he;
                return;
            }
        }

    }

    /** Return the value associated with the given key or null, if the map does not contain the key.
     * If the key is null, throw IllegalArgumentException.
     *
     * @param key key
     * @return value associated value
     */
    @Override
    public Object get(String key) {
        // FILL IN CODE
        int keyInt = hashFunc(key);
        for (int i = 0; i < maxSize; i++) {
            int idx = (i + keyInt) % maxSize;
            if (table[idx] == null)
                return null;
            if (table[idx].isDeleted())
                continue;
            if (table[idx].getKey().equals(key))
                return table[idx].getValue();
        }
        return null;
    }

    /** Remove a (key, value) entry if it exists.
     * Return the previous value associated with the given key, otherwise return null
     * @param key key
     * @return previous value
     */
    @Override
    public Object remove(String key) {
        // FILL IN CODE
        if (containsKey(key) == false)
            return null;
        size--;
        int keyInt = hashFunc(key);
        for (int i = 0; i < maxSize; i++) {
            int idx = (i + keyInt) % maxSize;
            if (table[idx] == null)
                return null;
            if (table[idx].isDeleted())
                continue;
            if (table[idx].getKey().equals(key)) {
                Object ret = table[idx].getValue();
                table[idx].setDeleted(true);
                return ret;
            }
        }
        return null;
    }

    /** Return the actual number of elements in the map.
     *
     * @return number of elements currently in the map.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * toString
     * @return a string representing a hash table
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // FILL IN CODE
        for (int i = 0; i < maxSize; i++) {
            sb.append(i + ": ");
            HashEntry ele = table[i];
            if (ele == null) {
                sb.append("null\n");
                continue;
            }
            sb.append(ele);
            sb.append("\n");
        }
        return sb.toString();
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

    // Add may implement other helper methods as needed

}
