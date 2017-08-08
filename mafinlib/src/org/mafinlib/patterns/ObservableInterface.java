package org.mafinlib.patterns;

import java.util.List;

/**
 * @author Alessandro Gnoatto
 */
public interface ObservableInterface {

  /**
   * Adds an observer to the Observable.
   * 
   * @param observer
   */
  public void addObserver(final ObserverInterface observer);


  /**
   * Counts how many Observers were attached to this class.
   * 
   * @return the number of Observers
   * @see ObserverInterface
   */
  public int countObservers();


  /**
   * Returns list of observers registered with the Observable. List returned is
   * unmodifiable list. 
   * 
   * @return list of observers
   */
  public List<ObserverInterface> getObservers();


  /**
   * Detaches a previously attached observer to the observable.
   * 
   * @param observer
   */
  public void deleteObserver(final ObserverInterface observer);


  /**
   * Detaches all previously attached observer to the observable.
   */
  public void deleteObservers();


  /**
   * Notifies all attached observers about changes in the observable.
   */
  public void notifyObservers();
  
  /**
   * Notifies all attached observers about changes in the observable.
   */
  public void notifyObservers(Object arg);
}