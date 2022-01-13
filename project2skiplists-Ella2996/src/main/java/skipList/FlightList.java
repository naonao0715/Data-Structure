package skipList;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import static java.lang.Math.*;
/** The class that represents the flight database using a skip list */
public class FlightList {

	// FILL IN CODE: needs to store the head, the tail and the height of the skip
	// list
	private FlightNode head;
	private FlightNode tail;
	private int height = 0;
	private static FlightKey INFINITE_SMALL = new FlightKey("AAA", "AAA", "","");
	private static FlightKey INFINITE_LARGE = new FlightKey("ZZZ", "ZZZ", "", "");
	Random rand = new Random();

	/** Default constructor */
	public FlightList() {
		// FILL IN CODE
		rand.setSeed(0);
	}

	/**
	 * Constructor.
	 * Reads flight data from the file and inserts it into this skip list.
	 * @param filename the name of he file
	 */
	public FlightList(String filename) {
		rand.setSeed(1);

		try  (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String s;
			while ((s = br.readLine()) != null) {
				String[] arr = s.split(" ");
				if (arr.length != 6)
					throw new IndexOutOfBoundsException();
				FlightKey key = new FlightKey(arr[0], arr[1], arr[2], arr[3]);
				FlightData data = new FlightData(arr[4], Double.parseDouble(arr[5]));
				insert(key, data);
				// System.out.println(this);
			}
		}
		catch (IOException e) {
			Assert.fail("IOException occured while reading from the file: " + e);
		}
	}

	/**
	 * Returns true if the node with the given key exists in the skip list,
	 * false otherwise. This method needs to be efficient.
	 * 
	 * @param key flight key
	 * @return true if the key is in the skip list, false otherwise
	 */
	public boolean find(FlightKey key) {
		// FILL IN CODE
		FlightNode cur = head;
		while (cur != null && cur.getNext() != null) {
			int comp = cur.getNext().getKey().compareTo(key);
			if (comp == 0)
				return true;
			if (comp < 0) {
				cur = cur.getNext();
				continue;
			}
			cur = cur.getDown();
		}
		return false; // don't forget to change it
	}

	/**
	 * Insert a (key, value) pair to the skip list. Returns true if it was able
	 * to insert.
	 *
	 * @param key flight key
	 * @param data associated flight data
	 * @return true if insertion was successful
	 */
	public boolean insert(FlightKey key, FlightData data) {
		if (find(key)) {
			return false;
		}
		int cnt = 0;
		while (true) {
			int randomValue = rand.nextInt() % 2;
			cnt++;
			if (randomValue == 1)
				break;
		}
		FlightNode newHead = new FlightNode(key, data);
		FlightNode newCur = newHead;
		for (int i = 1; i < cnt + 1; i++) {
			FlightNode tmp = new FlightNode(key, data);
			tmp.setUp(newCur);
			newCur.setDown(tmp);
			newCur = tmp;
		}
		int diff = Math.max(height - cnt - 1, 0);
		if (height < cnt + 1) {
			for (int i = 0; i < cnt + 1 - height; i++) {
				FlightNode tmpHead = new FlightNode(INFINITE_SMALL, null);
				FlightNode tmpTail = new FlightNode(INFINITE_LARGE, null);
				tmpHead.setNext(tmpTail);
				tmpTail.setPrev(tmpHead);
				tmpHead.setDown(head);
				tmpTail.setDown(tail);
				if (head != null) {
					head.setUp(tmpHead);
					tail.setUp(tmpTail);
				}
				head = tmpHead;
				tail = tmpTail;
			}
			height = cnt + 1;
		}
		newCur = newHead;
		FlightNode cur = head;
		while (cur != null && cur.getNext() != null) {
			int comp = cur.getNext().getKey().compareTo(key);
			if (comp < 0) {
				cur = cur.getNext();
				continue;
			}
			if (diff <= 0) {
				cur.getNext().setPrev(newCur);
				newCur.setPrev(cur);
				newCur.setNext(cur.getNext());
				cur.setNext(newCur);
				newCur = newCur.getDown();
			}
			diff--;
			cur = cur.getDown();
		}
		// FILL IN CODE
		return true; // don't forget to change it
	}
	/**
	 * Check if two keys are similar.
	 *
	 * @param key1 flight key 1
	 * @param key2 flight key 2
	 * @return Return -1 if any of origin, dest or date not equal. Return 0 if exactly the same. Return 1 otherwise.
	 */
	public int isSimilarKey(FlightKey key1, FlightKey key2) {
		if (!key1.getOrigin().equals(key2.getOrigin()))
			return -1;
		if (!key1.getDest().equals(key2.getDest()))
			return -1;
		if (!key1.getDate().equals(key2.getDate()))
			return -1;
		if (key1.getTime().equals(key2.getTime()))
			return 0;
		return 1;
	}
	/**
	 * Find the first key in the list which is larger or equal than current key in the S0 layer
	 *
	 * @param key flight key
	 * @return Return first key in the list which is larger or equal than current key in the S0 layer
	 */
	public FlightNode findLargeEqualKey(FlightKey key) {
		if (head == null)
			return null;
		FlightNode cur = head;
		while (cur != null && cur.getNext() != null) {
			int comp = cur.getNext().getKey().compareTo(key);
			if (comp < 0) {
				cur = cur.getNext();
				continue;
			}
			if (comp == 0 || cur.getDown() == null) {
				cur = cur.getNext();
				break;
			}
			cur = cur.getDown();
		}
		while (cur.getDown() != null) {
			cur = cur.getDown();
		}
		return cur;
	}
	/**
	 * Returns the list of nodes that are successors of a given key. Refer to
	 * the project pdf for a detailed description of the method.
	 * 
	 * @param key flight key
	 * @return successors of the given key
	 */
	public List<FlightNode> successors(FlightKey key) {
		List<FlightNode> arr = new ArrayList<FlightNode>();
		FlightNode cur = findLargeEqualKey(key);
		while (cur != null && cur.getNext() != null) {
			int comp = isSimilarKey(key, cur.getKey());
			if (comp == -1)
				break;
			if (comp == 1) {
				arr.add(cur);
			}
			cur = cur.getNext();
		}
		// FILL IN CODE

		return arr;
	}

	/**
	 * Returns the list of nodes that are predecessors of a given key
	 * (that corresponds to flights that are earlier than the given flight).
	 *  Refer to the project pdf for a detailed description of the method.
	 * 
	 * @param key flight key
	 * @return predecessors of the given key
	 */
	public List<FlightNode> predecessors(FlightKey key, int timeFrame) {
		List<FlightNode> arr = new ArrayList<FlightNode>();
		FlightNode cur = findLargeEqualKey(key);
		cur = cur.getPrev();
		while (cur != null && cur.getPrev() != null) {
			int comp = isSimilarKey(key, cur.getKey());
			if (comp == -1)
				break;
			if (comp == 1) {
				arr.add(cur);
			}
			cur = cur.getPrev();
		}
		Collections.reverse(arr);
		// FILL IN CODE
		return arr;

	}

	/**
	 * Returns the string representing the SkipList (contains the elements on all levels starting at the
	 * top. Each level should be on a separate line, for instance:
	 * (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30), (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30),   (SFO, JFK, 03/15, 7:15), (SFO, JFK, 03/20, 5:00), (SFO, PVD, 03/14, 09:15)
	 */
	public String toString() {
		// FILL IN CODE
		String ret = "";
		FlightNode curStartHead = head;
		while (curStartHead != null) {
			FlightNode cur = curStartHead.getNext();
			while (cur.getNext() != null) {
				ret += cur.getKey() + "， ";
				cur = cur.getNext();
			}
			ret += "\n";
			curStartHead = curStartHead.getDown();
		}


		return ret; // don't forget to change it
	}

	/**
	 * Outputs the SkipList to a file
	 * (prints the elements on all levels starting at the top.
	 * Each level should be printed on a separate line.
	 * @param filename the name of the file
	 */
	public void print(String filename) {
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
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times within the given time
	 * frame of the departure time of the key.
	 *
	 * @param key flight key
	 * @param timeFrame interval of time
	 * @return list of flight nodes that have the same origin, destination and date
	 * as the key, and whose departure time is within a given timeframe
	 */
	public List<FlightNode> findFlights(FlightKey key, int timeFrame) {
		List<FlightNode> resFlights = new ArrayList<FlightNode>();
		FlightNode cur = findLargeEqualKey(key);
		if (cur == null)
			return resFlights;
		FlightNode lessFlight = cur;
		lessFlight = lessFlight.getPrev();
		int keyTime = Integer.parseInt(key.getTime().substring(0, 2));

		while (lessFlight.getPrev() != null) {
			int comp = isSimilarKey(key, lessFlight.getKey());
			if (comp == -1)
				break;
			int curTime = Integer.parseInt(lessFlight.getKey().getTime().substring(0, 2));
			if (Math.abs(curTime - keyTime) > timeFrame)
				break;
			resFlights.add(lessFlight);
			lessFlight = lessFlight.getPrev();
		}
		Collections.reverse(resFlights);
		while (cur.getNext() != null) {
			int comp = isSimilarKey(key, cur.getKey());
			if (comp == -1)
				break;
			int curTime = Integer.parseInt(cur.getKey().getTime().substring(0, 2));
			if (Math.abs(curTime - keyTime) > timeFrame)
				break;
			resFlights.add(cur);
			cur = cur.getNext();
		}

		// FILL IN CODE

		return resFlights;
	}

	public static void main(String[] args) {
		FlightList fl = new FlightList("flights");
		//System.out.println(fl);
		FlightKey key = new FlightKey("FRA", "JFK", "01/03/2021", "16:00");
		fl.successors(key);
		fl.print("flights2");
	}

}
