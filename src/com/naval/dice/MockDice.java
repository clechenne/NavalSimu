package com.naval.dice;

import java.util.Stack;

public class MockDice implements IRandom {
	private Stack<Integer> values = new Stack<Integer>();
	
	public void addDice(int val) {
		values.push(val);
	}
	
	public Roll d6() {
		return roll();
	}
	
	public Roll d10() {
		return roll();
	}

	public Roll d100() {
		return roll();
	}
	
	private Roll roll() {
		Roll roll = null;
		
		if (values.size() > 0)
			roll = new MockRoll(values.pop());
		else 
			roll = new MockRoll(1);
		return roll;
	}
}
