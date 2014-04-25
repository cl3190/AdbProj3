package algorithm;

import java.util.*;
import java.io.*;

public class RuleGen {

	public static ArrayList<ItemCountPair> genSupportForAll(
			ArrayList<ArrayList<Integer>> records, List<String> itemTable,
			double support) {
		/*
		 * this one contains the ItemCount Pair for all combinations of items
		 * with min_sup
		 */
		ArrayList<ItemCountPair> ret = new ArrayList<ItemCountPair>();

		ArrayList<ItemCountPair> prevRound = new ArrayList<ItemCountPair>();
		ArrayList<ItemCountPair> nextRound = null;

		while (nextRound == null || nextRound.size() != 0) {
			nextRound = apriori_gen(prevRound, itemTable.size());

			for (ArrayList<Integer> transaction : records) {
				for (ItemCountPair itemCountPair : nextRound) {
					/*
					 * if the transaction contains this itemSet, let its support
					 * count +1
					 */
					if (transaction.containsAll(itemCountPair.getItemSet())) {
						itemCountPair.setCount(itemCountPair.getCount() + 1);
					}
				}
			}
			// drop all the itemSet that do not reach the minimum support
			int targetSupportCount = (int) Math.ceil(records.size() * support);
			for (ItemCountPair itemCountPair : nextRound) {
				if (itemCountPair.getCount() < targetSupportCount) {
					nextRound.remove(itemCountPair);

				}
			}
			ret.addAll(nextRound);
		}

		return ret;
	}

	private static ArrayList<ItemCountPair> apriori_gen(
			ArrayList<ItemCountPair> prevRound, int itemSize) {
		ArrayList<ItemCountPair> thisRound = new ArrayList<ItemCountPair>();
		if (prevRound.size() == 0) {
			// previous is the first round
			for (int i = 0; i < itemSize; i++) {
				ArrayList<Integer> itemSet = new ArrayList<Integer>();
				itemSet.add(i);
				thisRound.add(new ItemCountPair(itemSet, 0));
			}
		} else {
			for (int i = 0; i < prevRound.size(); i++) {
				for (int j = i + 1; j < prevRound.size(); j++) {
					/*
					 * because the itemSet in the same round has the same
					 * length, we can get their length by fetching the first one
					 */
					int itemLength = prevRound.get(0).getItemSet().size();
					for (int k = 0; k < itemLength; k++) {
						if (k != itemLength - 1) {
							// when the current item is not the last one, must
							// be the same
							if (prevRound.get(i).getItemSet().get(k) != prevRound
									.get(j).getItemSet().get(k)) {
								break;
							}
						} else {
							// the last one, then i's k must be smaller than j's
							// k to be insert to nextround
							if (prevRound.get(i).getItemSet().get(k) < prevRound
									.get(j).getItemSet().get(k)) {
								ArrayList<Integer> itemSet = new ArrayList<Integer>();
								for (Integer tmp : prevRound.get(i)
										.getItemSet()) {
									itemSet.add(tmp);
								}
								// adding the new item
								itemSet.add(prevRound.get(j).getItemSet()
										.get(k));
								Collections.sort(itemSet);
								thisRound.add(new ItemCountPair(itemSet, 0));
							}
						}
					}
				}
			}

			// below is the pruning parse
			for (ItemCountPair itemCountPair : thisRound) {
				ArrayList<Integer> itemSet_k = itemCountPair.getItemSet();

				boolean hasMatch = false;
				for (int k = 0; k < itemSet_k.size(); k++) {

					/*
					 * if we have item_k contains all the elements of item_k-1
					 * and item_k-1 don't have the current "k-th" element of
					 * item_k then item_k-1 is the same with item_k without this
					 * "k-th" element and so we achieve the pruning phrase
					 */
					for (ItemCountPair prevItemPair : prevRound) {
						ArrayList<Integer> itemSet_k_minus_1 = prevItemPair
								.getItemSet();

						if (itemSet_k.containsAll(itemSet_k_minus_1)
								&& !itemSet_k_minus_1
										.contains(itemSet_k.get(k))) {
							hasMatch = true;
							break;
						}
					}
				}
				if (!hasMatch) {
					thisRound.remove(itemCountPair);
				}
			}
		}

		return thisRound;

	}

	public static void main(String[] args) {
		double support = 0.3;
		double confidence = 0.3;
		File file = new File("INTEGRATED-DATASET");
		try {
			Records.genRecordList(file);
			ArrayList<ItemCountPair> allSetsSupport = RuleGen.genSupportForAll(
					Records.getRecords(), Records.getItemTable(), support);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
