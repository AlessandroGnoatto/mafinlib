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
package org.mafinlib.time.daycounters;

import org.mafinlib.time.Date;
import org.mafinlib.time.DayCounter;

/**
 * "Actual/365 (Fixed)" day count convention, also know as
 * "Act/365 (Fixed)", "A/365 (Fixed)", or "A/365F".
 *
 * @note According to ISDA, "Actual/365" (without "Fixed") is
 * an alias for "Actual/Actual (ISDA)"DayCounter (see
 * ActualActual.)  If Actual/365 is not explicitly
 * specified as fixed in an instrument specification,
 * you might want to double-check its meaning.
 * 
 * @author Alessandro Gnoatto
 *
 */
public class Actual365Fixed extends DayCounter{

	public Actual365Fixed() {
		super.impl = new Impl();
	}

	final private class Impl extends DayCounter.Impl {

		
		@Override
		public final String name() {
			return "Actual/365 (fixed)";
		}

		@Override
		public final double yearFraction(
				final Date dateStart, final Date dateEnd,
				final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
			return dayCount(dateStart, dateEnd)/365.0;
		}

	}

}
