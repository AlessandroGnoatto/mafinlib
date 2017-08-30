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

import java.util.Map;
import java.util.TreeMap;

import org.mafinlib.time.Date;

/**
 * Quantlib uses the Singleton pattern to instantiate a single instance of this class.
 * QlNet uses a static class. In Java only nested classes can be static classes.
 * 
 * @author Alessandro Gnoatto
 *
 */
public class Settings{
	
    /**
     * Changes the value of field evaluationDate.
     * <p>
     * Notice that a successful change of evaluationDate notifies all its listeners.
     */
    private static final String EVALUATION_DATE = "EVALUATION_DATE";
    
    /**
     * This flag specifies whether or not Events occurring on the reference
     * date should, by default, be taken into account as not happened yet.
     * It can be overridden locally when calling the Event::hasOccurred method.
     */
    private static final String INCLUDE_REFERENCE_DATE_EVENTS = "INCLUDE_REFERENCE_DATE_EVENTS";

    /**
     * Define this if payments occurring today should enter the NPV of an instrument.
     */
    private static final String INCLUDE_TODAY_CASHFLOWS = "INCLUDE_TODAY_CASHFLOWS";

    /**
     * ENFORCE_TODAYS_HISTORIC_FIXINGS
     */
    private static final String ENFORCES_TODAYS_HISTORIC_FIXINGS = "ENFORCES_TODAYS_HISTORIC_FIXINGS";

    
     
    public boolean referenceDateEvents() {
        final Object var = attrs.get().get(INCLUDE_REFERENCE_DATE_EVENTS);
        return var==null? false : (Boolean) var;
    }
    
    public boolean todaysCashflows() {
        final Object var = attrs.get().get(INCLUDE_TODAY_CASHFLOWS);
        return var==null? false : (Boolean) var;
    }


    public boolean enforcesTodaysHistoricFixings() {
        final Object var = attrs.get().get(ENFORCES_TODAYS_HISTORIC_FIXINGS);
        return var==null? false : (Boolean) var;
    }
    
    
    public void setReferenceDateEvents(final boolean referenceDateEvents){
    	 attrs.get().put(INCLUDE_REFERENCE_DATE_EVENTS, referenceDateEvents);
    }

    public void setTodaysCashflows(final boolean todaysPayments) {
        attrs.get().put(INCLUDE_TODAY_CASHFLOWS, todaysPayments);
    }


    public void setEnforcesTodaysHistoricFixings(final boolean enforceTodaysHistoricFixings) {
        attrs.get().put(ENFORCES_TODAYS_HISTORIC_FIXINGS, enforceTodaysHistoricFixings);
    }
	
	
    
    /**
     * @return the value of field evaluationDate
     */
    public Date evaluationDate() {
        return ((DateProxy) attrs.get().get(EVALUATION_DATE)).value();
    }
    
    /**
     * Changes the value of field evaluationDate.
     *
     * <p>
     * Notice that a successful change of evaluationDate notifies all its
     * listeners.
     */
    public Date setEvaluationDate(final Date evaluationDate) {
        final DateProxy proxy = (DateProxy) attrs.get().get(EVALUATION_DATE);
        proxy.assign(evaluationDate);
        return proxy;
    }
    

    /**
     * Call this to prevent the evaluation date to change at midnight (and, incidentally, to gain quite a bit of performance.)
     * If no evaluation date was previously set, it is equivalent to setting the evaluation date to Date::todaysDate(); if an evaluation date other than Date() was already set, it has no effect.
     */
    public void anchorEvaluationDate(){
    	setEvaluationDate( Date.todaysDate());
    }
    
    /**
     * Call this to reset the evaluation date to Date::todaysDate() and allow it to change at midnight.
     * It is equivalent to setting the evaluation date to Date(). This comes at the price of losing some performance, 
     * since the evaluation date is re-evaluated each time it is read. 
     */
    public void resetEvaluationDate(){
    	setEvaluationDate( new Date());
    }

    
    private static final ThreadAttributes attrs = new ThreadAttributes();
    
    //
    // Settings employs a ThreadLocal object in order to keep thread dependent data.
    // In spite <code>attrs</code> seems to be static and, for this reason, contain the same contents whatever
    // thread employs it, actually what happens is that ThreadLocal internally organized data using a thread id
    // or something like this as a key, in order to obtain thread dependent data.
    // So, what we do below is the initialization of this map, which means to say we are assigning default
    // values to these attributes. Every thread has freedom to change these attributes and can be sure that
    // no other thread will be affected by these changes.
    // [Richard Gomes]
    //
    private static class ThreadAttributes extends ThreadLocal<Map<String,Object>> {
        @Override
        public Map<String,Object> initialValue() {
            final Map<String, Object> map = new TreeMap<String, Object>();
            map.put(EVALUATION_DATE, new DateProxy());
            map.put(INCLUDE_REFERENCE_DATE_EVENTS, true);
            map.put(INCLUDE_TODAY_CASHFLOWS, true);
            map.put(ENFORCES_TODAYS_HISTORIC_FIXINGS, false);
                    
            return map;
        }
    }    
	
	private static class DateProxy extends Date {

		// outside world cannot instantiate
		private DateProxy() {
			super();
		}

		private DateProxy value() /* @ReadOnly */ {
			if (isNull()) {
				super.assign(todaysSerialNumber());
			}
			return this;
		}

		private Date assign(final Date date) {
			super.assign(date.serialNumber());
			super.notifyObservers();
			return this;
		}

	}	

}
