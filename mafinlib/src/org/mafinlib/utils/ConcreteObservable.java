package org.mafinlib.utils;

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
 * @author Alessandro Gnoatto
 */
public class ConcreteObservable implements Observable {
  
  private final List<Observer> observers;
  
  public ConcreteObservable() {
    this.observers = new LinkedList<Observer>();
  }

  @Override
  public synchronized void addObserver(final Observer observer) {
    observers.add(observer);
  }

  @Override
  public int countObservers() {
    return observers.size();
  }

  @Override
  public List<Observer> getObservers() {
    return Collections.unmodifiableList(this.observers);
  }
  
  @Override
  public synchronized void deleteObserver(final Observer observer) {
    observers.remove(observer);
  }
  
  @Override
  public synchronized void deleteObservers() {
    observers.clear();
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
  public void notifyObservers() {
    List<Observer> copyOfObservers;
    
    synchronized(this) {
      copyOfObservers = new LinkedList<Observer>(observers);
    }
    
    Iterator<Observer> i = copyOfObservers.iterator();
    while(i.hasNext()) {
      i.next().update();
    }
    
  }
  

}
