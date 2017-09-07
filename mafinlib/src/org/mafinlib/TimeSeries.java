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
package org.mafinlib;

import org.mafinlib.time.Date;

public class TimeSeries<V> extends Series<Date,V> { 
		
	public TimeSeries(final Class<V> classV) {
		super(Date.class, classV);
	}
		
}
	
