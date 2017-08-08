package org.mafinlib;

import org.mafinlib.patterns.ObservableInterface;

/**
 * This is purely virtual base class for market observables
 * It mimicks Quantlib/ql/quote.hpp
 * 
 * @author Alessandro Gnoatto
 */
public abstract class Quote implements ObservableInterface {
	
	/**
	 * Method to return the current value
	 * 
	 * @return Returns the current value;
	 */
	abstract double value();
	
	/**
	 * Returns true if the Quote holds a valid value
	 * @return 
	 */
	abstract boolean isValid();

}
