package algorithm;

import java.util.*;

public class RulePair {
	private String lefthand;
	private String righthand;
	public RulePair(String lefthand, String righthand) {
		super();
		this.lefthand = lefthand.substring(0, lefthand.length()-1);
		this.righthand = righthand.substring(0, righthand.length()-1);
	}
	public String getLefthand() {
		return lefthand;
	}
	public void setLefthand(String lefthand) {
		this.lefthand = lefthand;
	}
	public String getRighthand() {
		return righthand;
	}
	public void setRighthand(String righthand) {
		this.righthand = righthand;
	}
	
	
	
	
}
