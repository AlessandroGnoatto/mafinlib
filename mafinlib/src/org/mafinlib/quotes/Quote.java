package org.mafinlib.quotes;

import java.util.List;

import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.ObserverInterface;

/**
 * This is a purely virtual base class for market observables
 * It mimicks Quantlib/ql/quote.hpp
 * 
 * @author Alessandro Gnoatto
 */
public abstract class Quote implements ObservableInterface {
	
	/**
	 * Method to return the current value
	 * 
	 * @return Returns the current value;
	 */
	abstract double value();
	
	/**
	 * Returns true if the Quote holds a valid value
	 * @return 
	 */
	abstract boolean isValid();
	
	
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
