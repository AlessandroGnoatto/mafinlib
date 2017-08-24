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
package org.mafinlib.cashflow;

import java.util.Date;

import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.PolymorphicVisitableInterface;

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Event implements ObservableInterface, PolymorphicVisitableInterface{
	
    protected Event() {
   
    }
	
	public abstract Date date();

}
