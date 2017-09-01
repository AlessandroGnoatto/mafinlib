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

import org.mafinlib.MFL;

/**
 * In case of the ActualActual::ISMA convention the year fraction depends on the reference period also.
 * 
 * This is why the most general implementation of yearFraction requires 4 input dates. In most cases
 * the last two inputs will be null.
 * 
 * @author Alessandro Gnoatto
 *
 */
public class DayCounter {
	
	private static final String NO_IMPLEMENTATION_PROVIDED = "no implementation provided";
	
	protected Impl impl;
	
	public DayCounter(){
		
	}
	
	/**
	 * @return whether or not the day counter is initialized
	 */
	public boolean empty(){
		return impl == null;
	}
	
	/**
	 * @return the name of the day counter.
	 */
	public String name(){
		MFL.require(impl != null, NO_IMPLEMENTATION_PROVIDED);
		return impl.name();
	}
	
	/**
	 * Returns the number of days between two dates.
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return the number of days between two dates.
	 */
	public long dayCount(final Date dateStart, final Date dateEnd){
		MFL.require(impl != null, NO_IMPLEMENTATION_PROVIDED);
		return impl.dayCount(dateStart, dateEnd);
	}
	
	/**
	 * Returns  the period between two dates as a fraction of year.
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return the period between two dates as a fraction of year.
	 */
	public double yearFraction(final Date dateStart, final Date dateEnd){
		return yearFraction(dateStart, dateEnd,null,null);
	}
	
	/**
	 * Returns the period between two dates as a fraction of year, considering referencing dates for both.
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @param refPeriodStart
	 * @param refPeriodEnd
	 * @return  the period between two dates as a fraction of year, considering referencing dates for both.
	 */
	public double yearFraction(final Date dateStart, final Date dateEnd, final Date refPeriodStart, final Date refPeriodEnd) /* @ReadOnly */ {
        MFL.require(impl != null, NO_IMPLEMENTATION_PROVIDED);
        return impl.yearFraction(dateStart, dateEnd, refPeriodStart, refPeriodEnd);
	}
	
	//The two following mimick operator overloading in Quantlib "==" and "!="
    /**
     * Returns <tt>true</tt> if <code>this</code> and <code>other</code> belong to the same derived class.
     */
    public boolean eq(final DayCounter another) {
        return equals(another);
    }

    /**
     * @return the negation of {@link DayCounter#eq(DayCounter)}
     */
    public boolean ne(final DayCounter another) {
        return !equals(another);
    }
	
		
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
