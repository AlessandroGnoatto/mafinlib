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

/**
 * An observer will be in the present setting a financial product
 * whose value depends on market data (the observables)
 * 
 * @author Alessandro Gnoatto
 */
public interface ObserverInterface {
  
  public void update();

}
