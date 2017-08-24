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

/**
 * Visitor pattern implementation.
 * 
 * The aim of the visitor pattern is to add new function to existing class hierarchies without affecting those hierarchies.
 * 
 * This interface defines the {@link Visitable} side of the Visitor design pattern 
 * 
 * 
 * @author Alessandro Gnoatto
 *
 * @param <T> defines the data structure to be visited
 */
public interface VisitableInterface<T> {
	
	/**
	 * This method is responsible for determining if a Visitor passed as argument is eligible for handling the data structures
	 * kept by <code>this</code> class. In the affirmative case, <code>accept</code> is responsible for passing <code>this</code>
	 * data structures to the Visitor. 
	 * 
	 * @param visitor
	 */
	public void accept(VisitorInterface<T> visitor);

}
