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
package org.mafinlib.quotes;

import org.mafinlib.math.Constants;

/**
 * Simple Quote class. See ql/quotes/simplequote.hpp
 * 
 * @author Alessandro Gnoatto
 *
 */
public class SimpleQuote extends Quote{

	private double value;
	
	//Constructors
	public SimpleQuote(){
		this(Constants.NULL_REAL);
	}

	public SimpleQuote(final double value) {
		this.value = value;
	}
	
	public SimpleQuote(final SimpleQuote quote){
		this.value = quote.value;
	}
	
	/**
     * @return the difference between the new value and the old value
     */
	public double setValue(){
		return setValue(Constants.NULL_REAL);
	}
	
	/**
     * @return the difference between the new value and the old value
     */
	public double setValue(final double value){
		
		final double diff = this.value - value;
		
		if (diff != 0.0) {
            this.value = value;
            notifyObservers();
        }
        return diff;
	}
	
	public void reset() {
        setValue(Constants.NULL_REAL);
    }
	
	
	//Overrides Quote
	@Override
	double value() {
		return this.value;
	}

	@Override
	boolean isValid() {
		return !Double.isNaN(value);
	}

}
