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

import java.util.List;

import org.mafinlib.exception.LibraryException;
import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.ObserverInterface;
import org.mafinlib.patterns.PolymorphicVisitableInterface;
import org.mafinlib.patterns.PolymorphicVisitorInterface;
import org.mafinlib.patterns.VisitorInterface;
import org.mafinlib.time.Date;
/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Event implements ObservableInterface, PolymorphicVisitableInterface{
	
    protected Event() {
   
    }
	
    /**
     * @return Returns the date at which the event occurs
     */
    public abstract Date date();
	

	/**
	 * If includeRefDate is true, then an event has not occurred if its
	 * date is the same as the refDate, i.e. this method returns false if
	 * the event date is the same as the refDate.
	 * 
	 * @param date
	 * @param includeRefDate
	 * @return true if the event has already occured.
	 */
	public boolean hasOccurred(final Date date, final boolean includeRefDate){
		if(includeRefDate){
			return date().compareTo(date) < 0;
		}else{
			return date().compareTo(date) <= 0;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void accept(final PolymorphicVisitorInterface pv) {
		//Visitor<Event>* v1 = dynamic_cast<Visitor<Event>*>(&v);
		final VisitorInterface<Event> v = (pv != null) ? pv.visitor(this.getClass()) : null;
		if(v != null){
			v.visit(this);
		}else{
			throw new LibraryException("not an event visitor");
		}
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