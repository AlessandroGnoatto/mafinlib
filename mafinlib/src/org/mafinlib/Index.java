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

import java.util.Iterator;
import java.util.List;

import org.mafinlib.indexes.IndexManager;
import org.mafinlib.library.iterators.Iterables;
import org.mafinlib.math.Closeness;
import org.mafinlib.math.Constants;
import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.ObserverInterface;
import org.mafinlib.time.Calendar;
import org.mafinlib.time.Date;

/**
 * Virtual base class for indexes.
 * 
 * 
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Index implements ObservableInterface{

	public abstract String name();

	/**
	 * @return the calendar defining valid fixing dates
	 */
	public abstract Calendar fixingCalendar();

	/**
	 *  @return TRUE if the fixing date is a valid one
	 */
	public abstract boolean isValidFixingDate(Date fixingDate);

	/**
	 * @return the fixing at the given date. The date passed as arguments must be the actual calendar date of the
	 * fixing; no settlement days must be used.
	 */
	public abstract double fixing(Date fixingDate, boolean forecastTodaysFixing);
	
	/**
	 * @return the fixing TimeSeries
	 */
	public TimeSeries<Double> timeSeries() {
		return IndexManager.getInstance().getHistory(name());
	}
	
	/**
	 * Check if index allows for native fixings
	 * If this returns false, calls to addFixing and similar
	 * methods will raise an exception.
	 * @return
	 */
	protected boolean allowsNativeFixings(){
		return true;
	}
	
	/**
	 * Stores the historical fixing at the given date
	 * <p>
	 * The date passed as arguments must be the actual calendar date of the
	 * fixing; no settlement days must be used.
	 */
	public void addFixing(final Date date, final double value) {
		addFixing(date, value, false);
	}
	
	/**
	 * Stores the historical fixing at the given date
	 * <p>
	 * The date passed as arguments must be the actual calendar date of the
	 * fixing; no settlement days must be used.
	 */
	public void addFixing(final Date date,
						  final double value,
						  final boolean forceOverwrite) {
		checkNativeFixingsAllowed();
		final String tag = name();
		final TimeSeries<Double> h = IndexManager.getInstance().getHistory(tag);
		boolean noInvalidFixing = true, noDuplicateFixing = true;
		
	
		

	}
	
	public void addFixing(final Iterator<Date> dates,
						  final Iterator<Double> values,
						  final boolean forceOverwrite){
		checkNativeFixingsAllowed();
		final String tag = name();
		final TimeSeries<Double> h = IndexManager.getInstance().getHistory(tag);
		boolean noInvalidFixing = true, noDuplicatedFixing = true;
		Date invalidDate, duplicatedDate;
		double nullValue;
		double invalidValue;
		double duplicatedValue;
		
		for (final Date date : Iterables.unmodifiableIterable(dates)){
			double value = values.next();
			boolean validFixing = isValidFixingDate(date);
			double currentValue = h.get(date);
			boolean  missingFixing = forceOverwrite || Closeness.isClose(currentValue, Constants.NULL_REAL);
			
			if(validFixing){
				if(missingFixing){
					h.put(date, value);
				}else if(Closeness.isClose(currentValue, value)){
					//do nothing
				}else{
					noDuplicatedFixing = false;
					duplicatedDate = date;
					duplicatedValue = value;
				}
				
			}else{
				noInvalidFixing = false;
				invalidDate = date;
				invalidValue = value;
			}
		}
		
		IndexManager.getInstance().setHistory(tag, h);
		
		MFL.ensure(noInvalidFixing , "at least one invalid fixing provided"); 
		MFL.ensure(noDuplicatedFixing , "at least one duplicated fixing provided");


	}
	
	/**
	 * Clear the fixings stored for the index
	 */
	public final void clearFixings() {
		checkNativeFixingsAllowed();
		IndexManager.getInstance().clearHistory(name());
	}
	
	private void checkNativeFixingsAllowed(){
		MFL.require(allowsNativeFixings(), "native fixings not allowed for "+ this.name() + " refer to underlying indices instead");
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
