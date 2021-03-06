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
package org.mafinlib.math;

/**
 * Just a list of Mathematical constants. 
 * See ql/mathconstants.hpp 
 * 
 * 
 * @author Alessandro Gnoatto
 *
 */
final public class Constants {
	
    public final static int    MFL_MIN_INTEGER       =   Integer.MIN_VALUE;
    public final static int    MFL_MAX_INTEGER       =   Integer.MAX_VALUE;
    public final static double MFL_MIN_REAL          =  -Double.MAX_VALUE;
    public final static double MFL_MAX_REAL          =   Double.MAX_VALUE;
    public final static double MFL_MIN_POSITIVE_REAL =   Double.MIN_VALUE;
    public final static double MFL_EPSILON           =   Math.ulp(1.0);

    public static final int    NULL_INTEGER         =   Integer.MAX_VALUE;
    public static final int    NULL_NATURAL         =   NULL_INTEGER;
    public static final double NULL_REAL            =   Double.MAX_VALUE; 
    public static final double NULL_RATE            =   NULL_REAL;
    public static final double NULL_TIME            =   NULL_REAL;

    public static final double DBL_MIN              =   Double.MIN_VALUE;
    public static final double DBL_MAX              =   Double.MAX_VALUE;

}
