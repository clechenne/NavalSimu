package com.naval.dice;

public class MockRoll extends Roll {
	private int value;
	
	MockRoll(int value) {
		super(0,1);
		this.value = value;
	}
	
	@Override
	public int getIntValue() {
		return value;
	}

	
	@Override
	public String getReport() {
		return null;
	}

	
	@Override
	public String toString() {
		return "[" + value + "]";
	}

}
