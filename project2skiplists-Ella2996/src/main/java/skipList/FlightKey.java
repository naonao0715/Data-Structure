package skipList;

/**
 * Represents the key in the FlightNode. Stores origin, destination, date and
 * time. Implements Comparable<FlightKey>.
 */
public class FlightKey implements Comparable<FlightKey> {
	// Each key is a tuple: origin, destination, date, time
	private String origin;
	private String dest;
	private String date;
	private String time;


	/**
     *  FlightKey constructor
	 * @param or origin
	 * @param dest destination
	 * @param date date
	 * @param time time
	 */
    public FlightKey(String or, String dest, String date, String time) {
		// FILL IN CODE
		origin = or;
		this.dest = dest;
		this.date = date;
		this.time = time;
	}

	/**
     * FlightKey - copy constructor
	 * @param other the other FlightKey
	 */
	public FlightKey(FlightKey other) {
		origin = other.origin;
		dest = other.dest;
		date = other.date;
		time = other.time;
		// FILL IN CODE

	}

	// FILL IN CODE: Write getters for origin, destination, date and time

	/**
     * Compares a given flight key with the one given as a parameter.
	 * @param other
     * @return -1, if this key is < other, > -1 if the opposite, and 0 if equal.  </>
	 */
	public int compareTo(FlightKey other) {
		// FILL IN CODE
		if (origin.compareTo(other.origin) != 0)
			return origin.compareTo(other.origin);
		if (dest.compareTo(other.dest) != 0)
			return dest.compareTo(other.dest);
		if (date.compareTo(other.date) != 0)
			return date.compareTo(other.date);
		return time.compareTo(other.time);
		// return 0; // don't forget to change it
	}

	/**
     * Returns a string representation of the key
	 * @return String
	 */
	public String toString() {
		// FILL IN CODE
		String ret = "(" + origin + ", " + dest + ", " + date + ", " + time + ")";
		return ret; // don't forget to change it
	}

	/**
	 * getOrigin
	 * @return origin as a String
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * getDest
	 * @return destination as a String
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * getDate
	 * @return date as a String (format:  "01/03/2019")
	 */
	public String getDate() {
		return date;
	}

	/**
	 * getTime
	 * @return time as a String (format: "16:00")
	 */
	public String getTime() {
		return time;
	}
}
