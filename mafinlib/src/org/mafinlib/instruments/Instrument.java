package org.mafinlib.instruments;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.mafinlib.patterns.LazyObject;
import org.mafinlib.pricingengines.PricingEngineInterface;

/**
 * This is a port of Quantlib's Instrument class. The logic of the class is best described in the book by L. Ballabio 
 * "Implementing Quantlib"  @see <a href="https://leanpub.com/implementingquantlib/">https://leanpub.com/implementingquantlib/</a>
 * 
 * This class is abstract and defines the interface of concrete
 * instruments which will be derived from this one.
 * 
 * protected fields can be accessed by classes extending this abstract base instrument class.
 * 
 * Currently, we use LocalDate from java.time instead of a built in Date class as in Quantlib.
 * It is to be checked if this is a good idea.
 * 
 * @author Alessandro Gnoatto
 *
 */
public abstract class Instrument extends LazyObject{

	//data members
	/**
	 * The net present value of the instrument
	 */
	protected double NPV;

	/**
	 * Error estimate from a numerical method
	 */
	protected double errorEstimate;

	/**
	 * Additional results like sensitivities to be stored as a map.
	 */
	protected  Map<String, Object> additionalResults = new HashMap<String, Object>();

	/**
	 * The engine used to produce values.
	 */
	protected PricingEngineInterface engine;

	/**
	 * The date at which we compute the price.
	 */
	protected LocalDate valuationDate = null;

	/**
	 * If the instrument is expired we do not need to perform calculations.
	 * 
	 * @return true if the instrument is expired.
	 */
	public abstract boolean isExpired();


	//constructor
	protected Instrument(){
		this.NPV = Double.NaN;
		this.errorEstimate = 0.0;
	}


	//methods
	/**
	 * Calculate the net present value of the instrument. Implementation here
	 * jumps to the method calculate. The NPV we return might be the net present value we
	 * already computed previously or, if market data have been updated,
	 * a newly recalculated net present value.
	 * 
	 * @return The net present value of the instrument
	 */
	public final double NPV(){
		calculate();
		return NPV;
	}

	//protected methods
	/**
	 * Calculate checks if the instrument is expired or not
	 * and is responsible for eventually triggering the calculate method in LazyObject
	 */
	protected void calculate(){
		if(isExpired()){
			//set default values for an expired instrument
			setupExpired();
			//flag the instrument as calculated
			calculated = true;
		}else{
			/*
			 * triggers calculate from LazyObject
			 * LazyObject ensures that a calculation takes place only 
			 * if the cached result is no longer up to date.
			 */
			super.calculate();
		}
	}

	/**
	 * In case the instrument is expired we do not perform a calculation.
	 */
	protected void setupExpired(){
		NPV = 0.0;
		errorEstimate = 0.0;
	}


	/**
	 * In case a pricing engine is <b>not</b> used, this method must be overridden to perform the actual
	 * calculations and set any needed results. In case  a pricing engine is used, the default implementation
	 * below can be used.
	 * 
	 * This method provides a default implementation to <i><b>performCalculations</b></i> from <i><b>LazyObject</b></i>
	 */
	protected void performCalculations(){
		engine.reset();
		setupArguments(engine.getArguments());
		engine.calculate();
		fetchResults(engine.getResults());
	}


	/**
	 * After the calculation, we update all pricing information with the data
	 * returned from the pricing engine.
	 * 
	 * @param r
	 */
	protected void fetchResults(final PricingEngineInterface.Results r){
		final Instrument.Results results = (Instrument.Results)r;
		NPV = results.value;
		errorEstimate = results.errorEstimate;
		valuationDate = results.valuationDate;
		additionalResults = results.additionalResults;
	}

	/**
	 * Before the calculation takes place, the engine needs to be fed with data.
	 * The default abstract implementation returns an exception telling
	 * that the engine does not have enough information.
	 * @param a
	 */
	protected void setupArguments(final PricingEngineInterface.Arguments a){
		throw new IllegalArgumentException("Instrument.setupArguments() not implemented");
	}


	//public methods
	public final void setPricingEngine(final PricingEngineInterface engine) {
		if (this.engine != null)
			this.engine.deleteObserver(this);
		this.engine = engine;
		if (this.engine != null)
			this.engine.addObserver(this);
		update();
	}

	/**
	 * Returns a data structure containing additional results such as sensitivities (e.g. Greeks for a call option)
	 * @return the additional results
	 */
	public Map<String, Object> additionalResults(){
		return this.additionalResults;
	}
	
	/**
	 * This class provides an implementation to PricingEngineInterface.Results
	 * Data members from the instrument class are introduced and the reset method is provided
	 * with an implementation.
	 * 
	 * @author Alessandro Gnoatto
	 *
	 */
	class Results implements PricingEngineInterface.Results{

		public double value;
		public double errorEstimate;
		private final Map<String, Object> additionalResults = new HashMap<String, Object>();
		public LocalDate valuationDate;

		@Override
		public void reset() {
			value = errorEstimate = Double.NaN;
			valuationDate = null;
			additionalResults.clear();
		}

	}

}