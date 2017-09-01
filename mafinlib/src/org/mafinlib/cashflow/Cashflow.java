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
package org.mafinlib.cashflow;

import org.mafinlib.patterns.PolymorphicVisitorInterface;
import org.mafinlib.patterns.VisitorInterface;
import org.mafinlib.time.Date;
import org.mafinlib.Settings;

/**
 * Base class for cash flows.
 * 
 * Return the date and the amount of a cashflow.
 * 
 * A convenience method tells us if the cashflow has already occured or not.
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Cashflow extends Event implements Comparable<Cashflow>{
	
	
	/**
	 * Returns the amount of the cashflow. 
	 * The amount is not discounted, i.e., it is the actual amount paid at the cash flow date.
	 * @return the amount of the cashflow
	 */
	public abstract double amount();
	
	/**
	 * Returns the date that the cash flow trades exCoupon
	 * @return returns the date that the cash flow trades exCoupon
	 */
	protected Date exCouponDate(){
		return new Date();
	};
	
	/**
	 * returns true if the cashflow is trading ex-coupon on the refDate.
	 * A bond that is ex coupon is sold or bought with the knowledge that 
	 * the investor will not receive the next coupon payment from the bond.
	 * @param refDate
	 * @return returns true if the cashflow is trading ex-coupon on the refDate
	 */
	boolean tradingExCoupon(final Date refDate){
		Date ecd = exCouponDate();
		if (ecd.eq(new Date())){
			return false;
		}
		
		Date ref = !(refDate.eq(new Date())) ? refDate : Settings.instance().evaluationDate();
		return ecd.le(ref);
	}
	
	/**
	 * Overloads Event::hasOccurred in order to take Settings::includeTodaysCashflows in account
	 */
	@Override
	public boolean hasOccurred(Date refDate, boolean includeRefDate){
		
		if(refDate.ne(new Date())){
			Date cf = date();
			if(refDate.lt(cf))
				return false;
			if(cf.lt(refDate))
				return true;
		}
		
		if(refDate.eq(new Date()) || refDate.eq(Settings.instance().evaluationDate())){
			//today's date; we override the bool with the one specified in the settings (if any)
			boolean includeToday = Settings.instance().includeTodaysCashFlows();
			
			if(includeToday)
				includeRefDate = includeToday;
		}
		
		return super.hasOccurred(refDate, includeRefDate);
		
	}
	

	@Override
	public void accept(final PolymorphicVisitorInterface pv) {
		//Visitor<Event>* v1 = dynamic_cast<Visitor<Event>*>(&v);
		final VisitorInterface<Event> v = (pv != null) ? pv.visitor(this.getClass()) : null;
		if(v != null){
			v.visit(this);
		}else{
			super.accept(pv);
		}
	}
	
	@Override
	public int compareTo(Cashflow c2) {
		if (date().lt(c2.date())) {
			return -1;
		}
		
		if (date().equals(c2.date())) {
			try{
				if(amount() < c2.amount()){
					return -1;
				}
			}catch(final Exception e){
				return -1;
			}
			
			return 0;
		}
		
		return 1;
	}


}
