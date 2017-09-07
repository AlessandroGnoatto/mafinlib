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

import org.mafinlib.library.exceptions.LibraryException;
import org.mafinlib.time.Date;
import org.mafinlib.time.DayCounter;
import org.mafinlib.time.Frequency;

/**
 * 
 * Concrete interest rate class
 * 
 * This class encapsulate the interest rate compounding algebra.
 * It manages day-counting conventions, compounding conventions,
 * conversion between different conventions, discount/compound factor
 * calculations, and implied/equivalent rate calculations
 * 
 * @author Alessandro Gnoatto
 *
 */
public class InterestRate {

	private double rate;
	private DayCounter dc;
	private Compounding comp;
	private boolean freqMakesSense;
	private int freq;

	/**
	 * Default constructor returning a null interest rate.
	 *
	 */
	public InterestRate() {
		this.rate = 0.0;
	}


	/**
	 * Standard constructor.
	 *
	 * @param r represents the rate
	 * @param dc is a {@link DayCounter}
	 * @param comp is a {@link Compounding}
	 * @param freq represents a {@link Frequency}
	 *
	 * @category constructors
	 */
	public InterestRate(final double r, 
			final DayCounter dc, 
			final Compounding comp, 
			final Frequency freq) {
		this.rate = r;
		this.dc = dc;
		this.comp = comp;
		this.freqMakesSense = false;

		if (this.comp == Compounding.Compounded || this.comp == Compounding.SimpleThenCompounded) {
			freqMakesSense = true;
			MFL.require(freq != Frequency.Once && freq != Frequency.NoFrequency ,
					"frequency not allowed for this interest rate");
			this.freq = freq.toInteger();
		}
	}
	
	public final double rate(){
		return this.rate;
	}
	
	public final DayCounter dayCounter(){
		return dc;
	}
	
	public final Compounding compounding(){
		return comp;
	}
	
	public final Frequency frequency(){
		return freqMakesSense ? Frequency.valueOf(this.freq) : Frequency.NoFrequency;
	}
	
	
	/**
	 * Discount factor implied by the rate compounded at time t.
	 * Time must be measured using InterestRate's own day counter.
	 * @param t
	 * @return the discount factor up to time t.
	 */
	public double discountFactor(double t){
		return 1.0/compoundFactor(t);
	}
	
	/**
	 * Return the discount factor implied by the rate compounded between two dates
	 * @param d1
	 * @param d2
	 * @return the discount factor implied by the rate compounded between two dates
	 */
	public double discountFactor(final Date d1,
						  final Date d2){
		return discountFactor(d1, d2, new Date(), new Date());
	}
	
	
	/**
	 * Return the discount factor implied by the rate compounded between two dates
	 * @param d1
	 * @param d2
	 * @param refStart
	 * @param refEnd
	 * @return the discount factor implied by the rate compounded between two dates
	 */
	public double discountFactor(final Date d1,
						  final Date d2,
						  final Date refStart,
						  final Date refEnd){
		MFL.require(d2.ge(d1), d1 + "later than " +d2);
		
		return dc.yearFraction(d1, d2, refStart, refEnd);
	}

	/**
	 * @return the compound (a.k.a capitalization) factor implied by the rate compounded at time t.
	 *
	 * @note Time must be measured using InterestRate's own day counter.
	 *
	 * @category inspectors
	 */
	public final double compoundFactor(final double t) {

		MFL.require(t >= 0.0 , "negative time not allowed"); 
		MFL.require(!Double.isNaN(rate) , "null interest rate");

		final double r = rate;
		
		switch(comp){
			case Simple:			
				return 1.0 + r * t;
				
			case Compounded:				
				return Math.pow((1 + r / freq), (freq * t));
			
			case Continuous:				
				return Math.exp((r * t));
				
			case SimpleThenCompounded:				
				if (t <= (1 / (double) freq)){
					return 1.0 + r * t;
				}else{
					return Math.pow((1 + r / freq), (freq * t));
				}
			case CompoundedThenSimple:
				if(t > 1.0 / (double) freq){
					return  1.0 + r * t;
				}else{
					return Math.pow((1 + r / freq), (freq * t));
				}
			default:
				throw new LibraryException("unknown compounding convention");			
		
		}

	}
	
	/**
	 * Compound factor implied by the rate compounded between two dates.
	 * Returns the compound (a.k.a capitalization) factor implied by the rate between two dates.
	 * @param d1
	 * @param d2
	 * @return the compound factor implied by the rate compounded between two dates
	 */
	public final double compoundFactor(final Date d1,
									   final Date d2){
		return compoundFactor(d1, d2, new Date(), new Date());
		
	}
	
	/**
	 * Compound factor implied by the rate compounded between two dates.
	 * Returns the compound (a.k.a capitalization) factor implied by the rate between two dates.
	 * @param d1
	 * @param d2
	 * @param refStart
	 * @param refEnd
	 * @return the compound factor implied by the rate compounded between two dates
	 */
	public final double compoundFactor(final Date d1,
									   final Date d2,
									   final Date refStart,
									   final Date refEnd){
		
		MFL.require(d2.ge(d1), d1 + "later than " +d2);
		
		double t = dc.yearFraction(d1, d2, refStart, refEnd);
		
		return compoundFactor(t);
		
	}
	
	/**
	 * 
	 * @param c
	 * @param time
	 * @param resultDC
	 * @param comp
	 * @param freq
	 * @return
	 */
	static public InterestRate impliedRate(double compound,
										   final DayCounter resultDC,
										   Compounding comp,
										   Frequency freq,
										   final Date d1,
										   final Date d2,
										   final Date refStart,
										   final Date refEnd) {
		
		MFL.require(d2.ge(d1), d1 + "later than " +d2);
		
		double t = resultDC.yearFraction(d1, d2, refStart, refEnd);
		
		return impliedRate(compound, resultDC, comp, freq , t);
	}

	/**
	 * 
	 * @param c
	 * @param time
	 * @param resultDC
	 * @param comp
	 * @param freq
	 * @return
	 */
	static public InterestRate impliedRate(double compound,
										   final DayCounter resultDC,
										   Compounding comp,
										   Frequency freq,
										   final Date d1,
										   final Date d2) {
		
		MFL.require(d2.ge(d1), d1 + "later than " +d2);
		
		double t = resultDC.yearFraction(d1, d2, new Date(), new Date());
		
		return impliedRate(compound, resultDC, comp, freq , t);
	}

	static public InterestRate impliedRate(double compound,
										   final DayCounter resultDC,
										   Compounding comp,
										   Frequency freq,
										   double t){
		
		final double f = freq.toInteger();
		MFL.require(compound > 0, "positive compound factor required");
		
		double r;
		
		if(compound == 1.0){
			MFL.require(t >= 0, "non negative time t required");
			r = 0.0;
		}else{
			MFL.require(t > 0.0, "positive time required");
			
			switch(comp){
				case Simple:
					r = (compound - 1.0)/t;
					break;
				case Compounded:
					r = (Math.pow(compound, 1 / (f * t) - 1.0) - 1)*(f);
					break;
				case Continuous:
					r = Math.log(compound) / t;
					break;
				case SimpleThenCompounded:
					if(t <= 1.0 / f){
						r = (compound - 1.0)/t;
					}else{
						r = (Math.pow(compound, 1 / (f * t) - 1.0) - 1)*(f);
					}
					break;
				case CompoundedThenSimple:
					if(t > 1.0 / f){
						r = (compound - 1.0)/t;
					}else{
						r = (Math.pow(compound, 1 / (f * t) - 1.0) - 1)*(f);
					}
					break;
				default:
					throw new LibraryException("unknown compounding convention");
				
			}
			
		}
				
		return new InterestRate(r, resultDC,comp,freq);
	}
	
	
	public final InterestRate equivalentRate(Compounding comp,
											 Frequency freq,
											 double t){
		
		return impliedRate(compoundFactor(t),this.dc,comp,freq,t);
	}
	
	public final InterestRate equivalentRate(final DayCounter resultDC,
											 Compounding compound,
											 Frequency freq,
											 Date d1,
											 Date d2,
											 final Date refStart,
											 final Date refEnd){
		
		MFL.require(d2.ge(d1), d1 + "later than " +d2);
		
		double t1 = dc.yearFraction(d1,  d2, refStart, refEnd);
		double t2 = resultDC.yearFraction(d1, d2, refStart, refEnd);
		
		return impliedRate(compoundFactor(t1), resultDC, comp, freq, t2);
	}
	
	public final InterestRate equivalentRate(final DayCounter resultDC,
											 Compounding compound,
											 Frequency freq,
											 Date d1,
											 Date d2){

		MFL.require(d2.ge(d1), d1 + "later than " +d2);

		double t1 = dc.yearFraction(d1,  d2, new Date(), new Date());
		double t2 = resultDC.yearFraction(d1, d2, new Date(), new Date());

		return impliedRate(compoundFactor(t1), resultDC, comp, freq, t2);
	}	


	/**
	 * 
	 * @author Alessandro Gnoatto
	 *
	 */
	public enum Compounding {
		/**
		 * {@latex$ 1+rt }
		 */
		Simple,

		/**
		 * {@latex$ (1+r)^t }
		 */
		Compounded,

		/**
		 * {@latex$ e^{rt} }
		 */
		Continuous,

		/**
		 * Simple up to the first period then Compounded
		 */
		SimpleThenCompounded,
		
		/**
		 * 
		 */
		CompoundedThenSimple;
	}

}
