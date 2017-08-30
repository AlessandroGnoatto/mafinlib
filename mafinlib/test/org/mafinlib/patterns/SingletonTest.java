/* Copyright (C) 2017 Alessandro Gnoatto
 * 
 * MaFinLib extends/is based on/combines features/designs/code excerpts from
 * 
 * Quantlib https://github.com/lballabio/QuantLib
 * JQuantlib a port of Quantlib to Java: https://github.com/frgomes/jquantlib
 * Finmath https://github.com/finmath/finmath-lib
 * 
 * When applicable, the original copyright notice of the libraries above follows this notice.
 * 
 */
package org.mafinlib.patterns;

public class SingletonTest {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{
		
		System.out.println("Testing the Singleton pattern");
		
		System.out.println("Naive initialization of String instances");
		String one = new String();
		String oneagain = new String();
		
		boolean isEqual = one.equals(oneagain);
		System.out.println("Object contains the same information");
		System.out.println(isEqual);
		
		isEqual = one == oneagain;
		System.out.println("The memory address is the same");
		System.out.println(isEqual);
		
		System.out.println("Initialization of String using the Singleton pattern");
		String firstInstance = Singleton.instance(String.class);
		String secondInstance = Singleton.instance(String.class);
		
		isEqual = firstInstance.equals(secondInstance);
		System.out.println("Object contains the same information");
		System.out.println(isEqual);
		
		
		isEqual = firstInstance == secondInstance;
		System.out.println("The memory address is the same");
		System.out.println(isEqual);
	}
	
	
}
