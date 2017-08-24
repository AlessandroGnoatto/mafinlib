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
package org.mafinlib.time;

public enum TimeUnit {
    Days, Weeks, Months, Years;

    /**
     * Returns the name of time unit in long format (e.g. "week")
     * 
     * @return the name of time unit in long format (e.g. "week")
     */
    public String getLongFormat() {
        return getLongFormatString();
    }

    /**
     * Returns the name of time unit in short format (e.g. "w")
     * 
     * @return the name of time unit in short format (e.g. "w")
     */
    public String getShortFormat() {
        return getShortFormatString();
    }

    /**
     * Output time units in long format (e.g. "week")
     * 
     * @note message in singular form
     */
    private String getLongFormatString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString().toLowerCase());
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Output time units in short format (e.g. "W")
     */
    private String getShortFormatString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString().charAt(0));
        return sb.toString();
    }

}