package algorithm;

import java.util.*;

public class RulePair {
	private String lefthand;
	private String righthand;
	private double support;
	private double confidence;
	
	public RulePair(String lefthand, String righthand, double support,
			double confidence) {
		super();
		this.lefthand = lefthand.substring(0,lefthand.length()-1);
		this.righthand = righthand.substring(0,righthand.length()-1);
		this.support = support;
		this.confidence = confidence;
	}
	

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
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
