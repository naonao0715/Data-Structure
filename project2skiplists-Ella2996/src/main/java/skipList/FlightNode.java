package skipList;

/**
 * The class that represents a node in a flight skip list. 
 * Contains the key of type FlightKey and the data of type FlightData. 
 * Also stores the following pointers to FlightNode(s): next, down, prev and up.
 */
public class FlightNode {

	// FILL IN CODE, declare instance variables (make them private)
	private FlightKey fk;
	private FlightData fd;
	private FlightNode next;
	private FlightNode down;
	private FlightNode prev;
	private FlightNode up;

	/**
     * Copy constructor for FlightNode
	 * @param node FlightNode
	 */
	public FlightNode(FlightNode node) {
		// FILL IN CODE
		fk = node.fk;
		fd = node.fd;
	}

	/**
     * FlightNode Constructor
	 * @param key flight key
	 * @param data fight daya
	 */
	public FlightNode(FlightKey key, FlightData data) {
		// FILL IN CODE
		fk = key;
		fd = data;
	}

	// FILL IN CODE: write getters and setters for all private variables

	/**
     * A getter for the key
	 * @return key
	 */
	public FlightKey getKey() {
		return fk; // don't forget to change it
	}
	/**
	 * A getter for the data
	 * @return data
	 */
	public FlightData getData() {
		return fd;
	}
	/**
	 * A getter for the down
	 * @return down
	 */
	public FlightNode getDown() {
		return down;
	}
	/**
	 * A getter for the next
	 * @return next
	 */
	public FlightNode getNext() {
		return next;
	}
	/**
	 * A getter for the prev
	 * @return prev
	 */
	public FlightNode getPrev() {
		return prev;
	}
	/**
	 * A getter for the up
	 * @return up
	 */
	public FlightNode getUp() {
		return up;
	}

	/**
	 * A setter for the down
	 */
	public void setDown(FlightNode down) {
		this.down = down;
	}

	/**
	 * A setter for the data
	 */
	public void setData(FlightData fd) {
		this.fd = fd;
	}

	/**
	 * A setter for the key
	 */
	public void setKey(FlightKey fk) {
		this.fk = fk;
	}

	/**
	 * A setter for the next
	 */
	public void setNext(FlightNode next) {
		this.next = next;
	}

	/**
	 * A setter for the prev
	 */
	public void setPrev(FlightNode prev) {
		this.prev = prev;
	}

	/**
	 * A setter for the up
	 */
	public void setUp(FlightNode up) {
		this.up = up;
	}
}
