package algorithm;

import java.util.*;

/**
 * this class is just the pair of a item set and its corresponding count(the support)
 * @author kevin
 *
 */
public class ItemCountPair {
	private ArrayList<Integer> itemSet;
	private Integer count;
	
	
	
	public ItemCountPair(ArrayList<Integer> itemSet, Integer count) {
		super();
		this.itemSet = itemSet;
		this.count = count;
	}
	public ArrayList<Integer> getItemSet() {
		return itemSet;
	}
	public void setItemSet(ArrayList<Integer> itemSet) {
		this.itemSet = itemSet;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
