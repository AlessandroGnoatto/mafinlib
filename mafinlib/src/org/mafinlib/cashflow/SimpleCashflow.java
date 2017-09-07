package org.mafinlib.cashflow;

import org.mafinlib.patterns.PolymorphicVisitorInterface;
import org.mafinlib.patterns.VisitorInterface;
import org.mafinlib.time.Date;

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
