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
package org.mafinlib.indexes;

import org.mafinlib.currencies.Currency;
import org.mafinlib.patterns.ObserverInterface;
import org.mafinlib.time.Calendar;
import org.mafinlib.time.Date;
import org.mafinlib.time.DayCounter;
import org.mafinlib.time.Period;

public class InterestRateIndex extends Index implements ObserverInterface{
	
	protected String familyName;
	protected Period tenor;
	protected int fixingDays;
	protected Currency currency;
	protected DayCounter dayCounter;
	protected String name;
	private Calendar fixingCalendar;
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar fixingCalendar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidFixingDate(Date fixingDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double fixing(Date fixingDate, boolean forecastTodaysFixing) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		notifyObservers();
	}

}
