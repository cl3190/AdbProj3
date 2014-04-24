package algorithm;

import java.util.*;
import java.io.*;

/*
 * this class encapsulates all the necessary data structures used to represent the CSV file 
 * 
 * itemMap is essentially giving each item an unique Integer to represent them
 * 
 * records is the Integer representation of each transaction, and sorted
 */
public class Records {

	private static Map<String, Integer> itemMap = new HashMap<String, Integer>();
	private static List<ArrayList<Integer>> records = new ArrayList<ArrayList<Integer>>();

	public Map<String, Integer> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, Integer> itemMap) {
		this.itemMap = itemMap;
	}

	public static List<ArrayList<Integer>> getRecords() {
		return records;
	}

	public static void setRecords(List<ArrayList<Integer>> records) {
		Records.records = records;
	}

	public static void genRecordList(File file) throws IOException {
		Scanner scanner = new Scanner(file);
		Integer i = 0; // counter

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] transaction = line.split(",");

			/*
			 * add the item to the map if not already there, having the effect
			 * of assigning an Integer to each item
			 */
			for (String item : transaction) {
				if (!itemMap.containsKey(item)) {
					itemMap.put(item, ++i);
				}
			}

			/*
			 * generate the integer representation of each transaction, and have
			 * them sorted
			 */
			ArrayList<Integer> t = new ArrayList<Integer>();
			for (String item : transaction) {
				t.add(itemMap.get(item));
			}
			Collections.sort(t);
			records.add(t);
 
		}
	}

}
