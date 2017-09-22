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

import java.util.Iterator;
import java.util.List;

import org.mafinlib.MFL;
import org.mafinlib.TimeSeries;
import org.mafinlib.library.iterators.Iterables;
import org.mafinlib.math.Closeness;
import org.mafinlib.math.Constants;
import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.ObserverInterface;
import org.mafinlib.time.Calendar;
import org.mafinlib.time.Date;

public abstract class Index implements ObservableInterface{

	/**
	 * Returns the name of the index.
	 * @return the name of the index.
	 */
	public abstract String name();

	/**
	 * Returns the calendar defining valid fixing dates.
	 * @return the calendar defining valid fixing dates.
	 */
	public abstract Calendar fixingCalendar();

	/**
	 * Returns TRUE if the fixing date is a valid one.
	 * @param fixingDate
	 * @return TRUE if the fixing date is a valid one.
	 */
	public abstract boolean isValidFixingDate(Date fixingDate);


	/**
	 * Returns the fixing at the given date.
	 * @param fixingDate
	 * @param forecastTodaysFixing
	 * @return the fixing at the given date.
	 */
	public abstract double fixing(Date fixingDate, 
			boolean forecastTodaysFixing);

	/**
	 * Returns the fixing TimeSeries.
	 * @return the fixing TimeSeries.
	 */
	public TimeSeries<Double> timeSeries(){
		return IndexManager.getInstance().getHistory(name());
	}


	/**
	 * Stores the historical fixing at the given date.
	 * @param fixingDate
	 * @param fixing
	 * @param forceOverwrite
	 */
	public void addFixing(Date date, double value) {
		addFixing(date, value, false);
	}


	/**
	 * Stores the historical fixing at the given date
	 * <p>
	 * The date passed as arguments must be the actual calendar date of the
	 * fixing; no settlement days must be used.
	 */
	public void addFixing(Date date, 
			double value,
			boolean forceOverwrite){
		String tag = name();
		boolean missingFixing;
		boolean validFixing;
		boolean noInvalidFixing = true;
		boolean noDuplicatedFixing = true;
		final TimeSeries<Double> h = IndexManager.getInstance().getHistory(tag);

		validFixing = isValidFixingDate(date);
		final Double currentValue = h.get(date);
		missingFixing = forceOverwrite || Closeness.isClose(currentValue, Constants.NULL_REAL);

		if (validFixing) {
			if (missingFixing) {
				h.put(date, value);
			} else if (Closeness.isClose(currentValue, value)) {
				// Do nothing
			} else {
				noDuplicatedFixing = false;
			}
		} else {
			noInvalidFixing = false;
		}
		
		IndexManager.getInstance().setHistory(tag, h);
		MFL.ensure(noInvalidFixing , "at least one invalid fixing provided");
		MFL.ensure(noDuplicatedFixing , "at least one duplicated fixing provided");
	}
	
	/**
	 * Stores historical fixings at the given dates
	 * <p>
	 * The dates passed as arguments must be the actual calendar dates of the
	 * fixings; no settlement days must be used.
	 */
	public final void addFixings(Iterator<Date> dates, 
			Iterator<Double> values, 
			boolean forceOverwrite) {
		final String tag = name();
		boolean missingFixing;
		boolean validFixing;
		boolean noInvalidFixing = true;
		boolean noDuplicatedFixing = true;
		final TimeSeries<Double> h = IndexManager.getInstance().getHistory(tag);

		for (final Date date : Iterables.unmodifiableIterable(dates)) {
            final double value = values.next();
            validFixing = isValidFixingDate(date);
            final double currentValue = h.get(date);
            missingFixing = forceOverwrite || Closeness.isClose(currentValue, Constants.NULL_REAL);
            if (validFixing) {
                if (missingFixing) {
                    h.put(date, value);
                } else if (Closeness.isClose(currentValue, value)) {
                    // Do nothing
                } else {
                    noDuplicatedFixing = false;
                }
            } else {
                noInvalidFixing = false;
            }
		}

		IndexManager.getInstance().setHistory(tag, h);

		MFL.ensure(noInvalidFixing , "at least one invalid fixing provided");  // TODO: message
		MFL.ensure(noDuplicatedFixing , "at least one duplicated fixing provided");  // TODO: message
	}
	
	public final void clearFixings() {
		IndexManager.getInstance().clearHistory(name());
	}

	public double fixing(Date fixingDate){
        return fixing(fixingDate, false);
    }

	private final ObservableInterface myObservable = new Observable(this);

	@Override
	public synchronized void addObserver(final ObserverInterface observer) {
		myObservable.addObserver(observer);
	}

	@Override
	public int countObservers() {
		return myObservable.countObservers();
	}


	@Override
	public List<ObserverInterface> getObservers() {
		return myObservable.getObservers();
	}

	@Override
	public synchronized void deleteObserver(final ObserverInterface observer){
		myObservable.deleteObserver(observer);
	}

	@Override
	public synchronized void deleteObservers() {
		myObservable.deleteObservers();
	}

	@Override
	public void notifyObservers(){
		myObservable.notifyObservers();
	}

	@Override
	public void notifyObservers(final Object arg) {
		myObservable.notifyObservers(arg);
	}


}
