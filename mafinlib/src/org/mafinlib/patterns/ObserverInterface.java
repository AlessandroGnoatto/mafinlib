package org.mafinlib.patterns;

/**
 * An observer will be in the present setting a financial product
 * whose value depends on market data (the observables)
 * 
 * @author Alessandro Gnoatto
 */
public interface ObserverInterface {
  
  public void update();

}
