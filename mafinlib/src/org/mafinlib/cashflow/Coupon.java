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
import org.mafinlib.time.DayCounter;

/**
 * See ql/cashflows/coupon.hpp
 * 
 * Coupon accruing over a fixed period.
 * 
 * In case of the ActualActual::ISMA convention the year fraction depends on the reference period also.
 * 
 * This is why the most general implementation of Coupon requires 4 input dates. In most cases
 * the last two inputs will be null dates.
 * 
 * This class implements part of the CashFlow interface but it is
 * still abstract and provides derived classes with methods for
 * accrual period calculations.
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Coupon extends Cashflow{
	
	protected Date paymentDate;
	protected double nominal;
	protected Date accrualStartDate;
	protected Date accrualEndDate;
	protected Date refPeriodStart;
	protected Date refPeriodEnd;
	protected Date exCouponDate;
	protected double accrualPeriod;
	
	//Abstract methods
	
	//accrued rate
	public abstract double rate();
	
	// day counter for accrual calculation
	public abstract DayCounter dayCounter();
	
	//accrued amount at the given date
	public abstract double accruedAmount(final Date date);
	
	
	//Constructors
	
	/**
	 * 
	 * @param paymentDate
	 * @param nominal
	 * @param accrualStartDate
	 * @param accrualEndDate
	 */
	public Coupon(final Date paymentDate,
				  final double nominal,
				  final Date accrualStartDate,
				  final Date accrualEndDate){
		
		this(paymentDate,nominal,accrualStartDate,accrualEndDate, new Date(), new Date(), new Date());
	}
	
	/**
	 * 
	 * @param paymentDate
	 * @param nominal
	 * @param accrualStartDate
	 * @param accrualEndDate
	 * @param refPeriodStart
	 * @param refPeriodEnd
	 * @param exCouponDate
	 */
	public Coupon(final Date paymentDate,
				  final double nominal,  
				  final Date accrualStartDate, 
				  final Date accrualEndDate, 
				  final Date refPeriodStart,
				  final Date refPeriodEnd,
				  final Date exCouponDate) {
		
		this.paymentDate = paymentDate;
		this.nominal = nominal;
		this.accrualStartDate = accrualStartDate;
		this.accrualEndDate = accrualEndDate;
		this.refPeriodStart = refPeriodStart;
		this.refPeriodEnd = refPeriodEnd;
		this.exCouponDate = exCouponDate;
		this.accrualPeriod = Double.NaN;
		
		if(refPeriodStart.eq(new Date()))
			this.refPeriodStart =this.accrualStartDate;
		
		if(refPeriodEnd.eq(new Date()))
			this.refPeriodEnd = this.accrualEndDate;
	}
	
	//Getters
	
	/**
	 * Returns the nominal amount.
	 * @return the nominal amount.
	 */
	public double nominal(){
		return this.nominal;
	}
	
	/**
	 * Returns the start of the accrual period
	 * @return the start of the accrual period
	 */
	public Date accrualStartDate(){
		return this.accrualStartDate;
	}
	
	/**
	 * Returns the end of the accrual period
	 * @return the end of the accrual period
	 */
	public Date accrualEndDate(){
		return this.accrualEndDate;
	}
	
	/**
	 * Returns the start date of the reference period
	 * @return the start date of the reference period
	 */
	public Date referencePeriodStart(){
		return this.referencePeriodStart();
	}
	
	/**
	 * Returns the end date of the reference period
	 * @return the end date of the reference period
	 */
	public Date referencePeriodEnd(){
		return this.referencePeriodEnd();
	}
	
	/**
	 * Returns the accrual period as fraction of year
	 * @return the accrual period as fraction of year
	 */
	public double accrualPeriod(){
		if(this.accrualPeriod == Double.NaN)
			this.accrualPeriod = dayCounter().yearFraction(accrualStartDate, accrualEndDate,refPeriodStart,refPeriodEnd);
		
		return this.accrualPeriod;
	}
	
	/**
	 * Returns the accrual period in days
	 * @return the accrual period in days
	 */
	public double accrualDays(){
		return dayCounter().dayCount(accrualStartDate, accrualEndDate);
	}
	
	
	public double accruedPeriod(final Date d){
		if(d.le(accrualStartDate) || d.gt(paymentDate)){
			return 0;
		}else{
			Date dd = d.lt(accrualEndDate) ? d : accrualEndDate;
			return dayCounter().yearFraction(accrualStartDate, dd, refPeriodStart,refPeriodEnd);
		}
	}
	
	public long accruedDays(final Date d){
		if(d.le(accrualStartDate) || d.gt(paymentDate)){
			return 0;
		}else{
			Date dd = d.lt(accrualEndDate) ? d : accrualEndDate;
			return dayCounter().dayCount(accrualStartDate, dd);
		}
	}
	
	
	//Implements Event
	@Override
	public Date date(){
		return this.paymentDate;
	}
	
	//Implements PolymorphicVisitable
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

}