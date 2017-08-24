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
package org.mafinlib.patterns;

import java.util.List;

/**
 * This is a Java port of LazyObject from Quantlib. The logic of the class is best described in the book by L. Ballabio 
 * "Implementing Quantlib"  @see <a href="https://leanpub.com/implementingquantlib/">https://leanpub.com/implementingquantlib/</a>
 * 
 * This class provides an abstract framework for calculation on demand and result caching.
 * 
 * The implementation of any specific calculation is left to derived classes.
 * 
 * The idea is to use the <i>Template Method pattern</i> see "Design Patterns. Elements of Reusable Object-Oriented Software." by Gamma et al.
 * or alternatively  
 * @see <a href="https://sourcemaking.com/design_patterns/template_method/java/2">https://sourcemaking.com/design_patterns/template_method/java/2</a>
 * 
 * The boolean field <i><b>calculated</b></i> tells us if results have been calculated and are still valid.
 * 
 * The update method, coming from <i><b>ObserverInterface</b></i> is called upon notifications from observables and sets
 * <i><b>calculated</b></i> to false, which implies that previous calculations are invalidated.
 * 
 * The <i><b>calculate</b></i> method is where the <i>Template Method pattern</i> plays a role: the constant part of the algorithm is 
 * implemented in the base class. This corresponds to the management of the cached results in the present case.
 * 
 * The varying part of the algorithm performing the actual calculation is left abstract and implemented in derived classes: this corresponds to
 * the abstract method <i><b>performCalculations</b></i> that is called in the body of the method of the abstract base class.
 * 
 * In summary, if the results are no longer valid, derived classes will (re)perform the calculations and change the <i><b>calculated</b></i>
 * field to mark that the update has taken place.
 * 
 * @author Alessandro Gnoatto
 * 
 */
public abstract class LazyObject implements ObserverInterface, ObservableInterface{
	
	//fields
	protected boolean calculated;
	protected boolean frozen;
	
	
	//constructor
	public LazyObject(){
		this.calculated = false;
		this.frozen = false;
	}
	
	//methods
	/**
	 * This method is from <i><b>ObserverInterface</b></i>.
	 */
	@Override
	public void update(){
		/*
		 * observers don't expect notifications from frozen objects
		 * LazyObject forwards notifications only once until it has been recalculated
		 */
		if(!frozen && calculated){
			notifyObservers();
		}
		calculated = false;
	}
	
	/**
	 * This method forces the recalculation of any results which would otherwise be cached.
	 * 
	 * @note Explicit invocation of this method is <b>not</b> necessary if the object registered itself as observer with the
     * structures on which such results depend. It is strongly advised to follow this policy when possible.
	 */
	public final void recalculate() {
		boolean wasFrozen = frozen;
		calculated = frozen = false;
		try{
			calculate();
		}finally{
			frozen = wasFrozen;
			notifyObservers();
		}
		
	}
	
	/**
	 * This method constrains the object to return the presently 
	 * cached results on successive invocations, even if
	 * arguments upon which they depend should change.
	 */
	public final void freeze() {
		frozen = true;
	}
	
	/**
	 * This method reverts the effect of the <i><b>freeze</b></i>
	 * method, thus re-enabling recalculations.
	 */
	public final void unfreeze() {
		/*
		 * Send notifications, in case we lost any.
		 * This is performed only once, i.e. if it was frozen.
		 */
		if(frozen){
			frozen = false;
			notifyObservers();
		}
		
	}
	
	/**
	 * This method performs calculation by calling the method <i><b>performCalculations</b></i>.
	 * 
	 * Objects cache the results of the previous calculation. Such results will be returned upon later invocations of <i><b>calculate</b></i>.
	 * When the results depend on arguments which could change between invocations, the lazy object must register itself
	 * as observer of such objects for the calculations to be performed again when they change.
	 * 
	 * Should this method be redefined in derived classes, LazyObject::calculate() should be called in the overriding method.
	 */
	protected void calculate(){
		if(!calculated && !frozen){
			//prevent infinite recursion in case of bootstrapping
			calculated = true;
			try{
				performCalculations();
			}catch(final ArithmeticException e){
				calculated = false;
				throw e;
			}
		}
		
	}
	
	/**
	 * This method must implement any calculations which must be (re)done in order to calculate the desired results.
	 * @throws ArithmeticException
	 */
	protected abstract void performCalculations() throws ArithmeticException;
	
	
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