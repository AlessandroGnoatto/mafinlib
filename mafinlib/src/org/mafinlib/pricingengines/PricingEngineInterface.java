package org.mafinlib.pricingengines;

import org.mafinlib.patterns.ObservableInterface;

/**
 * This is a port of the abstract pricing engine class from quantlib, see ql/pricingengine.hpp and the book by L. Ballabio 
 * "Implementing Quantlib"  @see <a href="https://leanpub.com/implementingquantlib/">https://leanpub.com/implementingquantlib/</a>
 * 
 * A financial product might be priced by means of different methodologies, for example
 * 
 * <p><ul>
 * 	<li>Analytic formulas
 * 	<li>Monte Carlo method
 * 	<li>Analytic approximations when no analytic formulas are available
 *  <li>Lattice methods
 *  <li>Finite difference method
 *  <li>Finite element method
 * </ul><p>
 *
 * It must be possible to price a single instrument in different ways. Different implementations of
 * the method <i><b>performCalculations</b></i> would imply that e.g. a base class <i><b>EuropeanOption</b></i>
 * would then be extended by <i><b>FiniteDifferenceEuropeanOption</b></i> and <i><b>MonteCarloEuropeanOption</b></i>. 
 * The above choice is bad from a design point of view and prevents the possibility of switching the pricing engine at runtime. 
 * 
 * Proposed solution: The strategy pattern.
 * 
 * The instrument to be priced takes an object that encapsulates the concrete computation to be performed.
 * 
 * This encapsulated object is the pricing engine. An instrument can accept an arbitrary number of pricing 
 * engines corresponding to the given instrument, pass arguments to the pricing engine and fetch the results from it.
 * 
 * The expected behavior of <i><b>performCalculations</b></i> is of the form
 * 
 * void SomeInstrument::performCalculations() const{
 * 	NPV_ = engine ->calculate(arg1,....,argN);
 * }
 * 
 * where <i><b>calculate</b></i> is defined in the engine interface and implemented in concrete engines.
 * 
 * The problem in the above pseudocode is that the number of inputs is not constant between different classes.
 * 
 * The same holds for output information: besides the price the return value is typically a set of sentivities/figures which is variable. This would result
 * in different interfaces for different instruments.
 * 
 * Proposed solution: arguments and results are abstract structures. Concrete structures with different number of members will be stored in each
 * pricing engine and provide instrument-specific data members.
 * 
 * From a semantic point of view, the fact that Arguments and Results are nested in the body of PricingEngineInterface underlines the relation between these objects.
 * 
 * @author Alessandro Gnoatto
 *
 */
public interface PricingEngineInterface extends ObservableInterface{
	
	public PricingEngineInterface.Arguments getArguments();
	
	public PricingEngineInterface.Results getResults();

	public void reset();
	
	/**
	 * Method performing calculations to be implemented by concrete engines
	 */
	public void calculate();
	
	/**
	 * Abstract container for inputs to the pricing engine
	 * 
	 * @author Alessandro Gnoatto
	 *
	 */
	public interface Arguments {
		
		/**
		 *This methods checks that input are in a valid range
		 *after they have been updated.
		 */
		public void validate();
    }
	
	/**
	 * Abstract container for outputs of the pricing engine
	 * 
	 * @author Alessandro Gnoatto
	 *
	 */
	public interface Results {
		
		/**
		 * To be called before the engine starts so as to clean previous results.
		 */
	    public void reset();
	}
		
}
