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
 * This interface provides the functionality of obtaining a specific {@link VisitorInterface}
 * <p>
 * This functionality is needed every time a class acts as a {@link VisitorInterface} of more than one data structure or when a class acting
 * as a {@link Visitable} requires a specific kind of {@link VisitorInterface}.
 * <p>
 * A class which implements {@link PolymorphicVisitorInterface} is potentially able to provide multiple implementations of {@link VisitorInterface}.
 * 
 * @see VisitorInterface
 * @see VisitableInterface
 * @see PolymorphicVisitableInterface
 * @see <a href="http://www.exciton.cs.rice.edu/JavaResources/DesignPatterns/VisitorPattern.htm">The Visitor Design Pattern</a>
 * 
 * @author Richard Gomes
 */
public interface PolymorphicVisitorInterface {

	/**
	 * This method returns a {@link VisitorInterface} responsible for visiting a specific data structure
	 * 
	 * @param <T> needed for wildcard capture trick which allows type inference at compile time. More information at
	 * <a href="http://download.oracle.com/javase/tutorial/extra/generics/morefun.html">More Fun with Wildcards</a>
	 * 
	 * @param class specifies the data structure type
	 * @return a Visitor responsible for visiting a certain data structure type
	 * 
	 * @see PolymorphicVisitablInterfacee#accept(PolymorphicVisitorInterface)
	 */
	public <T> VisitorInterface<T> visitor(Class<? extends T> element);
	
}
