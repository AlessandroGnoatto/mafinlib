package org.mafinlib.patterns;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * This class provides a thread-safe implementation of the observer pattern.
 * 
 * When a synchronized method is called, the calling thread attempts to acquire an
 * exclusive lock on the object. If any other thread holds that lock, then the
 * calling thread stalls until the lock is released.
 * 
 * The code is thread-safe under the assumption that the methods that change
 * the state of an object are only called from one thread.
 * 
 * Main source for this class is the article "The problem with Threads" by Edward A. Lee
 * 
 * @author Alessandro Gnoatto
 */
public class Observable implements ObservableInterface {

	private final List<ObserverInterface> observers;
	private final ObservableInterface observable;
	
	public Observable(final ObservableInterface observable) {
		this.observers = new LinkedList<ObserverInterface>();
		this.observable = observable;
	}

	@Override
	public synchronized void addObserver(final ObserverInterface observer) {
		observers.add(observer);
	}

	@Override
	public int countObservers() {
		return observers.size();
	}

	@Override
	public List<ObserverInterface> getObservers() {
		return Collections.unmodifiableList(this.observers);
	}

	@Override
	public synchronized void deleteObserver(final ObserverInterface observer) {
		observers.remove(observer);
	}

	@Override
	public synchronized void deleteObservers() {
		observers.clear();
	}

	@Override
	public void notifyObservers(){
		notifyObservers(null);
	}


	/**
	 * Notification to the observers.
	 * 
	 * The thread that calls notifyObservers obtains a lock but it does not notify
	 * the observers when holding the lock. Instead, it copies the set of observers
	 * to a local LinkedList and releases a lock, and only then proceeds to iteration
	 * over the local list and observers notification. A code of the form
	 * 
	 * synchronized(this){
	 * 	for(Observer myOberserver : observers){
	 * 		observer.update();
	 * 	}
	 * }
	 * 
	 * would be problematic if another thread attempted to register/unregister observers
	 * while "this" thread notifies observers. 
	 */
	@Override
	public void notifyObservers(final Object arg) {
		List<ObserverInterface> copyOfObservers;

		synchronized(this) {
			copyOfObservers = new LinkedList<ObserverInterface>(observers);
		}

		Iterator<ObserverInterface> i = copyOfObservers.iterator();
		while(i.hasNext()) {
			wrappedNotify(i.next(),observable,arg);
		}

	}
	
	/**
	 * Classes extending Observable can implement their own version of this method.
	 * 
	 * @param observer
	 * @param observable
	 * @param arg
	 */
	protected void wrappedNotify(final ObserverInterface observer, final ObservableInterface observable, final Object arg){
		observer.update();
	}
}
