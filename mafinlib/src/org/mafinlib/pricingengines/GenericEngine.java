package org.mafinlib.pricingengines;

/**
 * This class provides a partial implementation of Generic Engine.
 * The implementation of calculate from <i><b>PricingEngineInterface</b></i> is left abstract
 * 
 * @author Alessandro Gnoatto
 *
 * @param <A> Arguments
 * @param <R> Results
 */
public abstract class GenericEngine <A extends PricingEngineInterface.Arguments,
									 R extends PricingEngineInterface.Results>
										implements PricingEngineInterface{
	
	//Fields
	protected A arguments;
	protected R results;
	
	protected GenericEngine(final A arguments, final R results){
		this.arguments = arguments;
		this.results = results;
	}
	
	//(Partial) implementation of PricingEngineInterface
	/**
	 * Getter for arguments.
	 */
	@Override
	public final A getArguments(){
		return this.arguments;
	}
	
	/**
	 * Getter for results.
	 */
	@Override
	public final R getResults(){
		return this.results;
	}
	
	/**
	 * Implements 
	 */
	@Override
	public void reset(){
		results.reset();
	}
	
	/**
	 * The implementation of calculate is delegated to concrete classes.
	 */
	@Override
	public abstract void calculate();
	
}
