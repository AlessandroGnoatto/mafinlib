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
package org.mafinlib.pricingengines;

import java.util.List;

import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;
import org.mafinlib.patterns.ObserverInterface;

/**
 * This class provides a partial implementation of Generic Engine.
 * The implementation of calculate from <i><b>PricingEngineInterface</b></i> is left abstract
 * 
 * @author Alessandro Gnoatto
 *
 * @param <A> Arguments
 * @param <R> Results
 */
public abstract class GenericEngine <A extends PricingEngineInterface.Arguments,
									 R extends PricingEngineInterface.Results>
										implements PricingEngineInterface{
	
	//Fields
	protected A arguments;
	protected R results;
	
	protected GenericEngine(final A arguments, final R results){
		this.arguments = arguments;
		this.results = results;
	}
	
	//(Partial) implementation of PricingEngineInterface
	/**
	 * Getter for arguments.
	 */
	@Override
	public final A getArguments(){
		return this.arguments;
	}
	
	/**
	 * Getter for results.
	 */
	@Override
	public final R getResults(){
		return this.results;
	}
	
	/**
	 * Implements 
	 */
	@Override
	public void reset(){
		results.reset();
	}
	
	/**
	 * The implementation of calculate is delegated to concrete classes.
	 */
	@Override
	public abstract void calculate();
	
	
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
