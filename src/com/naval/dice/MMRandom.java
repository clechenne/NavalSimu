/*
 * MegaMek - Copyright (C) 2000-2002 Ben Mazur (bmazur@sev.org)
 * 
 *  This program is free software; you can redistribute it and/or modify it 
 *  under the terms of the GNU General Public License as published by the Free 
 *  Software Foundation; either version 2 of the License, or (at your option) 
 *  any later version.
 * 
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 *  for more details.
 */

/*
 * MMRandom.java
 *
 * Created on April 27, 2003, 11:29 PM
 */

package com.naval.dice;

import java.util.Random;

/**
 * Used by Compute to generate random numbers, usually dice rolls.
 *
 * The base class is abstract, having a number of concrete subclasses that it
 * will give using the generate() method.
 *
 * @author  Ben
 */
public abstract class MMRandom implements IRandom {
    
    public static final int R_DEFAULT   = 1;

    public static final int R_SUN       = 0;
    public static final int R_CRYPTO    = 1;

    /**
     * Gives you the type asked for, defaulting to SunRandom if there are
     * any errors.
     */
    public static MMRandom generate(int type) {
        System.err.println("MMRandom: generating RNG type #" + type);
        try {
            switch (type) {
                case R_CRYPTO :
                    return new MMRandom.CryptoRandom();
                case R_SUN :
                default :
                    return new MMRandom.SunRandom();
            }
        }
        catch (Exception ex) {
            System.err.println("MMRandom: could not create desired RNG #" + type);
            System.err.println("MMRandom: using SunRandom (#0) instead");
            
            return new MMRandom.SunRandom();
        }
    }
    
    
    /**
     * Simulates six-sided die rolls.
     *
     * @param   nDice - the <code>int</code> number of dice to roll.
     *          If this value is less than or equal to zero, an
     *          <code>IllegalArgumentException</code> will be thrown.
     * @return  a <code>Roll</code> object containing the roll results.
     * @throws  <code>IllegalArgumentException</code> will be thrown
     *          if the input is <= 0.
     */
    public Roll d6(int nDice) {
        if (0 >= nDice) {
            throw new IllegalArgumentException
                ("Must ask for a positive number of rolls, not " + nDice);
        }

        // Use the Roll object to record the rolls.
        MMRoll roll = new MMRoll (this, 6, 1);
        for (int i = 1; i < nDice; i++) {
            roll.addRoll (this);
        }
        return roll;
    }

    /**
     * A single die
     */
    public Roll d6() {
        return d6(1);
    }
     
    public Roll d10() { 
    	return new MMRoll (this, 10, 1);
    }

    public Roll d100() { 
    	return new MMRoll (this, 100, 1);
    }
    
    /**
     * Returns a random <code>int</code> in the range from 0 to one
     * less than the supplied max value.
     *
     * @param   maxValue - the smallest <code>int</code> value which
     *          will exceed any random number returned by this method.
     * @return  a random <code>int</code> from the value set [0, maxValue).
     */
    public abstract int randomInt( int maxValue );
    
    
    /**
     * Uses com.sun.java.util.collections.Random
     */
    static class SunRandom extends MMRandom {
        Random random = new Random();

        
		@Override
		public int randomInt(int maxValue) {
            return random.nextInt(maxValue);
        }
    }
    
    /**
     * Uses java.security.SecureRandom
     */
    static class CryptoRandom extends MMRandom {
        java.security.SecureRandom random;
        
        /**
         * Contruct, making a new thread to init the RNG
         */
        public CryptoRandom() throws ClassNotFoundException, NoSuchMethodException {
            // hack: just check to see if there's java.util.Random@nextInt(int)
            new java.util.Random().getClass().getMethod("nextInt", new Class[] {Integer.TYPE});
            
            // all clear, get on with the normal init
            random = new java.security.SecureRandom();
            
            Thread initRNG = new Thread( new Runnable() {
                    public void run() { 
                        random.nextInt(); 
                    }
                }, "Random Number Init (CryptoRandom)" );
            initRNG.start();
        }

        
		@Override
		public int randomInt(int maxValue) {
            return random.nextInt(maxValue);
        }
    } 
}
