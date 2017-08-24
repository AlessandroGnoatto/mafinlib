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
