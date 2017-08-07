package org.mafinlib;


import org.mafinlib.utils.Observable;
import org.mafinlib.utils.ObserverInterface;

/**
 * Class representing a shared handle to an observable.
 * 
 * In Quantlib there was the problem of implementing the concept of a shared pointer to a shared pointer of objects.
 * 
 * The solution was provided by the authors of Quantlib in the form of the Handle class template.
 * 
 * All copies of an instance of this class refer to the same observable by means
 * of a relinkable reference. When such reference is relinked to another
 * observable, the change will be propagated to all the copies.
 * 
 * @author Alessandro Gnoatto
 *
 * @param <T>
 */
public class Handle<T extends Observable> {
	
	/*
	 * A Handle stores a reference to a link. In C++ this is a smart pointer to a link.
	 */
	final protected Link link;
	
	//Constructors
	public Handle() {
		this(null,true);
	}
	
	public Handle(final T h){
		this(h,true);
	}
	
	public Handle(T h, boolean registerAsObserver){
		link = new Link(h, registerAsObserver);
	}
	
	/**
	 * Check if the stored link is empty.
	 * @return true if the stored link is empty
	 */
	final public boolean empty(){
		return link.empty();
	}
	
	/**
	 * Returns the current Link.
	 * 
	 * The original C++ implementation features the method currentLink and two operators of the form
	 * 
	 * const boost::shared_ptr<T>& currentLink() const;
	 * const boost::shared_ptr<T>& operator->() const;
	 * const boost::shared_ptr<T>& operator*() const
	 * 
	 * The method and the two operators are all performing the same task, namely
	 * 
	 * return link_->currentLink();
	 * 
	 * In Java this is not possible to overload operators and we are forced to explicitly call currentLink()
	 * 
	 * @return the current Link instance
	 */
	final public T currentLink(){
		return link.currentLink();
	}
	
	/**
	 * According to L. Ballabio: The Link class is both an observer and an observable:
	 * 
	 * It receives notifications from its pointee.
	 * 
	 * Broadcasts its own notification each time is made to point to a different pointee.
	 * 
	 * @author Alessandro Gnoatto
	 *
	 */
	protected class Link extends Observable implements ObserverInterface{
		
		private T h_;
		private boolean isObserver_;
		
		
		/**
		 * Construct a link. In Quanlib the body of the constructor is:
		 * 
		 *  : isObserver_(false) { linkTo(h,registerAsObserver); }
		 * 
		 * @param h
		 * @param registerAsObserver
		 */
		public Link(T h, boolean registerAsObserver){
			super(h);
			this.isObserver_ = false;
			linkTo(h, registerAsObserver);
		}
		
		/**
		 * Link to an observable
		 * 
		 * @param h
		 * @param registerAsObserver
		 */
		public void linkTo(T h, boolean registerAsObserver){
			if (!(this.h_.equals(h)) || (this.isObserver_!=(registerAsObserver))) {
	            if ( !(this.h_.equals(null)) && this.isObserver_) {
	                this.h_.deleteObserver(link);
	            }
	            this.h_ = h;
	            this.isObserver_ = registerAsObserver;
	            if (!(this.h_.equals(null)) && this.isObserver_) {
	                this.h_.addObserver(link);
	            }
	            if (!(this.h_.equals(null))) {
	                this.h_.notifyObservers();
	            }
	        }
		}
		
		/**
		 * Check if the link is empty
		 * @return true if the link is empty
		 */
		public boolean empty(){
			return h_.equals(null); 
		}
		
		/**
		 * Return the current Link	
		 * @return the current link
		 */
		public T currentLink(){ 
			return h_;
		}
		
		/**
		 * Overrides update() from Observable
		 */
		@Override
		public void update(){
			if(!(h_.equals(null))){
				super.notifyObservers();
			}
			
		}		
	}
	
}