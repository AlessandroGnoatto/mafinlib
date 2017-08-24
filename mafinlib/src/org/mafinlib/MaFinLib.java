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
package org.mafinlib;
import java.util.logging.LogManager;

//import org.slf4j.Logger;

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public class MaFinLib {
	
	static LogManager logger;
	
    public final static void setLogger(final LogManager logger) {
        MaFinLib.logger = logger;
    }
}
