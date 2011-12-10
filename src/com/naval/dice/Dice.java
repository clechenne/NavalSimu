package com.naval.dice;

public class Dice {
	private static IRandom random = MMRandom.generate(MMRandom.R_DEFAULT); 
	
	private static IRandom randomForUnregister=null;
	
	public static void register(IRandom aRandomImpl) {
		randomForUnregister = random;
		random = aRandomImpl;
	}
	
	public static Roll roll() {
		return random.d6();
	}

	public static Roll rollD10() {
		return random.d10();
	}

	public static Roll rollD100() {
		return random.d100();
	}
	
	public static void unregister() {
		if (randomForUnregister != null) {
			random = randomForUnregister;
		}
		
	}
}
