package problem6;

/**
 * An entry in the hash table. Stores a key and a corresponding value.
 * Do not change this class.
 */
public class HashEntry {
    private int key;
    private int value;

    /**
     * Constructor for class HashEntry
     *
     * @param key   key
     * @param value corresponding value
     */
    public HashEntry(int key, int value) {
        this.key = key;
        this.value = value;
    }


    /**
     * Returns the key stored in the entry
     *
     * @return key
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns the value stored in the entry
     *
     * @return value
     */
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
