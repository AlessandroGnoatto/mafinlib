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

import org.mafinlib.InterestRate;
import org.mafinlib.InterestRate.Compounding;
import org.mafinlib.patterns.PolymorphicVisitorInterface;
import org.mafinlib.patterns.VisitorInterface;
import org.mafinlib.time.Date;
import org.mafinlib.time.DayCounter;
import org.mafinlib.time.Frequency;

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public class FixedRateCoupon extends Coupon{
	
	private final InterestRate rate;
	
	public FixedRateCoupon(final Date paymentDate,
						   double nominal,
						   double rate,
						   final DayCounter dayCounter,
						   final Date accrualStartDate,
						   final Date accrualEndDate,
						   final Date refPeriodStart,
						   final Date refPeriodEnd,
						   final Date exCouponDate){
		
		super(paymentDate,nominal,accrualStartDate,accrualEndDate,refPeriodStart,refPeriodEnd,exCouponDate);
		
		this.rate = new InterestRate(rate,dayCounter,Compounding.Simple,Frequency.Annual);
	}
	
	public FixedRateCoupon(final Date paymentDate,
			   			   double nominal,
			   			   final InterestRate interestRate,
			   			   final DayCounter dayCounter,
			   			   final Date accrualStartDate,
			   			   final Date accrualEndDate,
			   			   final Date refPeriodStart,
			   			   final Date refPeriodEnd,
			   			   final Date exCouponDate){
		
		super(paymentDate,nominal,accrualStartDate, accrualEndDate,refPeriodStart,refPeriodEnd,exCouponDate);
		
		this.rate = interestRate;
		
	}
	
	@Override
	public double amount() {	
		return this.nominal()*(this.rate.compoundFactor(this.accrualStartDate,
														this.accrualEndDate,
														this.refPeriodEnd,
														this.refPeriodEnd) - 1.0);
	}
	
	@Override
	public double accruedAmount(Date date) {
		
		if(date.le(this.accrualStartDate) || date.gt(this.paymentDate)){
			return 0.0;
		}else if(tradingExCoupon(date)){
			return this.nominal()*(this.rate.compoundFactor(date,
															this.accrualEndDate,
															this.refPeriodEnd,
															this.refPeriodEnd) - 1.0);
		}else{
			return this.nominal()*(this.rate.compoundFactor(this.accrualStartDate,
															Date.min(date,this.accrualEndDate),
															this.refPeriodEnd,
															this.refPeriodEnd) - 1.0);
			
		}
	
	}
	
	public InterestRate interestRate(){
		return this.rate;
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
	public double rate() {
		return this.rate.rate();
	}


	@Override
	public DayCounter dayCounter() {
		return rate.dayCounter();
	}

}