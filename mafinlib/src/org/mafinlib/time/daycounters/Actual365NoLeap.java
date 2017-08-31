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
import org.mafinlib.time.Month;

/**
 * "Actual/365 (No Leap)" day count convention, also known as
 * "Act/365 (NL)", "NL/365", or "Actual/365 (JGB)".
 * 
 * @author Alessandro Gnoatto
 *
 */
public class Actual365NoLeap extends DayCounter{
	
	public Actual365NoLeap(){
		super.impl = new Impl();
	}

	final private class Impl extends DayCounter.Impl {

		
		@Override
		public final String name() {
			return "Actual/365 (NL)";
		}
		
		/**
		 * Returns the exact number of days between 2 dates, excluding leap days
		 * @param d1
		 * @param d2
		 * @return  the exact number of days between 2 dates, excluding leap days.
		 */
		public long dayCount(final Date d1, final Date d2){
			
			long[] MonthOffset = {0,  31,  59,  90, 120, 151,  // Jan - Jun 
					181, 212, 243, 273, 304, 334   // Jun - Dec 
					};
			
			long s1 = d1.dayOfMonth() + MonthOffset[d1.month().value() -1] + (d1.year() * 365);
			long s2 = d2.dayOfMonth() + MonthOffset[d2.month().value() -1] + (d2.year() * 365);
			
			if (d1.month() == Month.February && d1.dayOfMonth() == 29){
				--s1;
			}
			
			if (d2.month() == Month.February && d2.dayOfMonth() == 29){
				--s2;
			}
			
			return s2 - s1;

		}

		@Override
		public final double yearFraction(
				final Date dateStart, final Date dateEnd,
				final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */{
			return dayCount(dateStart, dateEnd)/365.0;
		}

	}
	
}
