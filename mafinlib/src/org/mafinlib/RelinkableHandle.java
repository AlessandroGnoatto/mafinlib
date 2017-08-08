package org.mafinlib;

import org.mafinlib.patterns.Observable;

/**
 * An instance of this class can be relinked so that it points to another observable.
 * The change will be propagated to all handles that were created as copies of such instance.
 * 
 * @author Alessandro Gnoatto
 *
 * @param <T>
 */
public class RelinkableHandle<T extends Observable> extends Handle<T> {
	
	//Constructors
	public RelinkableHandle(){
		super();
	}
	
	public RelinkableHandle(final T h){
		this(h,true);
	}
	
	public RelinkableHandle(final T h, final boolean isObserver){
		super(h,isObserver);
	}
	
	/**
	 * Link to an observable
	 * 
	 * @param h
	 */
	public final void linkTo(final T h){
		linkTo(h, true);
	}
	
	/**
	 * Link to an observable
	 * 
	 * @param h
	 * @param registerAsObserver
	 */
	public void linkTo(final T h, final boolean registerAsObserver){
		super.link.linkTo(h, registerAsObserver); 
	}

}
