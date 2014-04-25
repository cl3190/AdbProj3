package algorithm;

import java.util.*;

public class RulePair {
	private ArrayList<Integer> leftHand = new ArrayList<Integer>();
	private ArrayList<Integer> rightHand = new ArrayList<Integer>();
	public RulePair(ArrayList<Integer> leftHand, ArrayList<Integer> rightHand) {
		super();
		this.leftHand = leftHand;
		this.rightHand = rightHand;
	}
	public ArrayList<Integer> getLeftHand() {
		return leftHand;
	}
	public void setLeftHand(ArrayList<Integer> leftHand) {
		this.leftHand = leftHand;
	}
	public ArrayList<Integer> getRightHand() {
		return rightHand;
	}
	public void setRightHand(ArrayList<Integer> rightHand) {
		this.rightHand = rightHand;
	}
	
	
}
