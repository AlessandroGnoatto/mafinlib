package org.mafinlib.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
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
