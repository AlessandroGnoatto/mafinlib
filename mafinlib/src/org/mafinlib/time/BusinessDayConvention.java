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
package org.mafinlib.time;

public enum BusinessDayConvention {
	// ISDA
	/**
	 * Choose the first business day after the given holiday.
	 */
	Following,

	/**
	 * Choose the first business day after
	 * the given holiday unless it belongs
	 * to a different month, in which case
	 * choose the first business day before
	 * the holiday.
	 */
	ModifiedFollowing,

	/**
	 * Choose the first business day before
	 * the given holiday.
	 */
	Preceding,

	// NON ISDA
	/**
	 * Choose the first business day before
	 * the given holiday unless it belongs
	 * to a different month, in which case
	 * choose the first business day after
	 * the holiday.
	 */
	ModifiedPreceding,

	/**
	 * Do not adjust.
	 */
	Unadjusted;
}

