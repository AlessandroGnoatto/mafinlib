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
 * @author Alessandro Gnoatto
 *
 * @param <T>
 */
public interface VisitorInterface<T> {
	
	/**
	 * This method is responsible for processing a data structure.
	 * 
	 * @param element
	 */
	public void visit(T element);
}
