package org.mafinlib;

import org.mafinlib.time.Date;

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public class Settings {
	
	private DateProxy evaluationDate;
	private boolean includeReferenceDateEvents;
	private boolean includeTodaysCashFlows;
	private boolean enforcesTodaysHistoricFixings;
	
	
	 /**
     * @return the value of field evaluationDate
     */
    public Date evaluationDate() {
    	return null;
    }
    
    /**
     * Changes the value of field evaluationDate.
     *
     * <p>
     * Notice that a successful change of evaluationDate notifies all its
     * listeners.
     */
    public Date setEvaluationDate(final Date evaluationDate) {
        return null;
    }
    

    /**
     * Call this to prevent the evaluation date to change at midnight (and, incidentally, to gain quite a bit of performance.)
     * If no evaluation date was previously set, it is equivalent to setting the evaluation date to Date::todaysDate(); if an evaluation date other than Date() was already set, it has no effect.
     */
    public void anchorEvaluationDate(){
    	// set to today's date if not already set.
    	if((evaluationDate.value()).eq(new Date())){
    		evaluationDate = (DateProxy) Date.todaysDate();
    	}
    	// If set, no-op since the date is already anchored.
    }
    
    /**
     * Call this to reset the evaluation date to Date::todaysDate() and allow it to change at midnight.
     * It is equivalent to setting the evaluation date to Date(). This comes at the price of losing some performance, 
     * since the evaluation date is re-evaluated each time it is read. 
     */
    public void resetEvaluationDate(){
    	evaluationDate = (DateProxy) new Date();
    }
    
	/**
	 * This flag specifies whether or not Events occurring on the reference
	 * date should, by default, be taken into account as not happened yet.
	 * It can be overridden locally when calling the Event::hasOccurred method.
	 * This mimicks  bool includeReferenceDateEvents() const;
	 * 
	 * @return true if events occuring on the reference date should be included.
	 */
    public boolean includeReferenceDateEvents(){
    	return this.includeReferenceDateEvents;
    }
    
	/**
	 * This flag specifies whether or not Events occurring on the reference
	 * date should, by default, be taken into account as not happened yet.
	 * It can be overridden locally when calling the Event::hasOccurred method.
	 * Thius mimicks  bool& includeReferenceDateEvents(); which returns an lvalue that can be assigned.
	 * 
	 * @param the input value of includeReferenceDateEvents
	 * @return the input value of includeReferenceDateEvents
	 */
    public boolean setIncludeReferenceDateEvents(final boolean includeReferenceDateEvents){
    	this.includeReferenceDateEvents = includeReferenceDateEvents;
    	return includeReferenceDateEvents;
    }
    
    /**
     * If set, this flag specifies whether or not CashFlows
     * occurring on today's date should enter the NPV.  When the
     * NPV date (i.e., the date at which the cash flows are
     * discounted) equals today's date, this flag overrides the
     * behavior chosen for includeReferenceDate. It cannot be overridden
     * locally when calling the CashFlow::hasOccurred method.
     * 
     * @return true is today's cashflow enter the NPV
     */
    public boolean includeTodaysCashFlows(){
    	return this.includeTodaysCashFlows;
    }
    
    /**
     * If set, this flag specifies whether or not CashFlows
     * occurring on today's date should enter the NPV.  When the
     * NPV date (i.e., the date at which the cash flows are
     * discounted) equals today's date, this flag overrides the
     * behavior chosen for includeReferenceDate. It cannot be overridden
     * locally when calling the CashFlow::hasOccurred method.
     *  
     * @param includeTodaysCashFlows
     * @return true is today's cashflow enter the NPV
     */
    public boolean setIncludeTodaysCashFlows(final boolean includeTodaysCashFlows){
    	this.includeTodaysCashFlows = includeTodaysCashFlows;
    	return includeTodaysCashFlows;
    }
    
    /**
     * This flag is used e.g. for Xibor coupons.
     * if (fixingDate_<today || Settings::instance().enforcesTodaysHistoricFixings()) 
     * 		Rate result = index_->pastFixing(fixingDate_);
     * 
     * @return
     */
    public boolean enforcesTodaysHistoricFixings(){
    	return this.enforcesTodaysHistoricFixings;
    }
    
    public boolean setEnforcesTodaysHistoricFixings(final boolean enforcesTodaysHistoricFixings){
    	this.enforcesTodaysHistoricFixings = enforcesTodaysHistoricFixings;
    	return enforcesTodaysHistoricFixings;
    }
    
    
	//Begin thread safe Singleton pattern implementation
	private static Settings instance = null;

    
	private Settings(){
		this.includeReferenceDateEvents = false;
		this.enforcesTodaysHistoricFixings = false;
	}
	
	public static synchronized Settings instance(){
		if(instance == null){
			instance = new Settings();
		}
		return instance;
	}
	//End thread safe Singleton pattern implementation
	
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

	}	

}
