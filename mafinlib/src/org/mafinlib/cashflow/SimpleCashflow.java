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

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public class SimpleCashflow extends Cashflow{
	
	private double amount;
	private Date paymentDate;
	
	public SimpleCashflow(final double amount,
						  final Date paymentDate){
		this.amount = amount;
		this.paymentDate = paymentDate;
	}
	
	@Override
	public double amount() {
		return amount;
	}

	@Override
	public Date date() {
		return paymentDate;
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
	

}
