package org.mafinlib.time;

public class DayCounter {
	
	
	
	
	/**
	 * Abstract base class for any day counter implementation
	 * @author Alessandro Gnoatto
	 *
	 */
	protected abstract class Impl{
		protected abstract String name();
		
		protected abstract double yearFraction(final Date dateStart,final Date dateEnd,
												final Date refPeriodStart, final Date refPeriodEnd);
		
		/**
		 * To be overloaded by more complex day counters
		 * @param dateStart
		 * @param dateEnd
		 * @return the period between two dates as a fraction of year
		 */
		protected long dayCount(final Date dateStart, final Date dateEnd){
			return dateEnd.sub(dateStart);
		}
	}

}
