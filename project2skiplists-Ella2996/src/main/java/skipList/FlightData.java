package skipList;

/**
 * Represents data in the FlightNode. Contains the flight number and the price
 */
public class FlightData {
	// FILL IN CODE: add private variables: flightNumber and price
	private String flightNumber;
	private double price;

	/**
     * Constructor for FlightData
	 * @param fnum flight number
	 * @param price price of the flight
	 */
	public FlightData(String fnum, double price) {
		// FILL IN CODE
		flightNumber = fnum;
		this.price = price;
	}

	/**
     * Returns the number of the flight
	 * @return flight number
	 */
	public String getFlightNumber() {
		// FILL IN CODE
		return flightNumber; // don't forget to change it
	}

	/**
	 * Returns the price of the flight
	 * @return price
	 */
	public double getPrice() {
		// FILL IN CODE
		return price; // don't forget to change it
	}
}
