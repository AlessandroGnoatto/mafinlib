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
 * This interface works together with {@link PolymorphicVisitorInterface} in order to provide
 * the functionality of obtaining a specific {@link VisitorInterface}.
 * <p>
 * This functionality is needed every time a class acts as a Visitor of more
 * than one data structure or when a class acting as a Visitable requires a
 * certain kind of Visitor.
 * 
 * @note A class which implements {@link PolymorphicVisitableInterface} probably does not need
 * to implement {@link VisitableInterface}
 * 
 * @see VisitorInterface
 * @see VisitableInterface
 * @see PolymorphicVisitorInterface
 * @see <a href="http://www.exciton.cs.rice.edu/JavaResources/DesignPatterns/VisitorPattern.htm">The Visitor Design Pattern</a>
 *
 * @param <T> defines the data structure to be visited
 * 
 * @author Richard Gomes
 */
public interface PolymorphicVisitableInterface {

	/**
     * This method is intended to extend the semantics of method {@link VisitableInterface#accept(VisitorInterface)}
     * <p>
     * In a conventional Visitor design pattern, the <code>accept</code> method is called when access to visit a data structure is
     * requested. A {@link VisitorInterface} object is passed as argument in case permission is granted to that {@link VisitorInterface} to access the
     * data structure. Obviously, {@link VisitorInterface}s and {@link VisitableInterface}s work in pairs and the class which provides the data
     * structure to be visited also implements {@link VisitableInterface} in order to properly grant access when the expected {@link VisitorInterface}
     * is received.
     * <p>
     * In the case of a {@link PolymorphicVisitableInterface}, a {@link PolymorphicVisitorInterface} is passed instead of a {@link VisitorInterface}. A
     * {@link PolymorphicVisitorInterface} is in fact, a composition of {@link VisitorInterface}s and not only a single {@link VisitorInterface}. A
     * {@link PolymorphicVisitorInterface} is responsible for returning the correct {@link VisitorInterface} responsible for processing a certain data
     * structure.
     * <p>
     * The initial design of pairs made of &lt;{@link VisitorInterface},{@link VisitableInterface}&gt; is extended to a concept of a matrix made
     * of multiple {@link VisitorInterface}s against multiple {@link VisitableInterface}s. Every class which implements {@link PolymorphicVisitableInterface}
     * passes different data structures when querying {@link PolymorphicVisitorInterface}s.
     * 
     * @see PolymorphicVisitorInterface#getVisitor(Class)
     */
    public void accept(PolymorphicVisitorInterface polymorphicVisitor);

}
