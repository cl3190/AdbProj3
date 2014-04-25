package algorithm;

import java.util.*;
import java.io.*;

public class RuleGen {

	/*
	 * this hash map is to map some itemSet in String, for fast look up in
	 * genRules method
	 */
	private static HashMap<String, Integer> itemSupportMap = new HashMap<String, Integer>();

	public static ArrayList<ItemCountPair> genSupportForAll(
			ArrayList<ArrayList<Integer>> records, ArrayList<String> itemTable,
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
			Iterator<ItemCountPair> iter = nextRound.iterator();
			while (iter.hasNext()) {
				ItemCountPair itemCountPair = iter.next();
				if (itemCountPair.getCount() < targetSupportCount) {
					iter.remove();

				} else {
					/* union the Large-k itemset */
					ret.add(itemCountPair);
					/* add to the itemSet map */
					StringBuffer hashCodeSb = new StringBuffer();
					for (Integer ind : itemCountPair.getItemSet()) {
						hashCodeSb.append(itemTable.get(ind) + ",");
					}
					itemSupportMap.put(hashCodeSb.toString(),
							itemCountPair.getCount());
				}
			}

			prevRound = nextRound;

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
			Iterator<ItemCountPair> iter = thisRound.iterator();
			while (iter.hasNext()) {
				ItemCountPair itemCountPair = iter.next();
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
					iter.remove();
				}
			}
		}

		return thisRound;

	}

	public static ArrayList<RulePair> genRules(
			ArrayList<ItemCountPair> supportTable, ArrayList<String> itemTable,
			double confidence) {

		ArrayList<RulePair> ret = new ArrayList<RulePair>();

		for (int i = 0; i < supportTable.size(); i++) {
			for (int j = i + 1; j < supportTable.size(); j++) {
				Integer i_union_j_sup = null;
				StringBuffer i_hashcode_sb = new StringBuffer();
				StringBuffer j_hashcode_sb = new StringBuffer();

				for (Integer ind : supportTable.get(i).getItemSet()) {
					i_hashcode_sb.append(itemTable.get(ind) + ",");
				}
				for (Integer ind : supportTable.get(j).getItemSet()) {
					j_hashcode_sb.append(itemTable.get(ind) + ",");
				}

				if (itemSupportMap.get(i_hashcode_sb.toString()
						+ j_hashcode_sb.toString()) != null) {
					if (itemSupportMap.get(i_hashcode_sb.toString()
							+ j_hashcode_sb.toString())
							/ itemSupportMap.get(i_hashcode_sb.toString()) >= confidence) {
						ret.add(new RulePair(i_hashcode_sb.toString(),
								j_hashcode_sb.toString()));
					}
					if (itemSupportMap.get(i_hashcode_sb.toString()
							+ j_hashcode_sb.toString())
							/ itemSupportMap.get(j_hashcode_sb.toString()) >= confidence) {
						ret.add(new RulePair(j_hashcode_sb.toString(),
								i_hashcode_sb.toString()));

					}
				}
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		double support = 0.004;
		double confidence = 0.5;
		File file = new File("INTEGRATED-DATASET");
		try {
			Records.genRecordList(file);
			ArrayList<ItemCountPair> allSetsSupport = RuleGen.genSupportForAll(
					Records.getRecords(), Records.getItemTable(), support);

			ArrayList<RulePair> rules = RuleGen.genRules(allSetsSupport,
					Records.getItemTable(), confidence);
			for (RulePair rule : rules) {
				System.out.println(rule.getLefthand() + " => "
						+ rule.getRighthand());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
