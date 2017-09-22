package org.mafinlib.patterns;

import java.util.List;

public class ObservableValue<T> implements ObservableInterface{
	
	private T value;

	public ObservableValue(final T value){
		this.value = value;
	}
	
    public ObservableValue(final ObservableValue<T> observable) {
        this.value = observable.value;
    }
    
    public void assign(final T value) {
        this.value = value;
        myObservable.notifyObservers();
    }

    public void assign(final ObservableValue<T> observable) {
        this.value = observable.value;
        myObservable.notifyObservers();
    }

    public T value() {
        return value;
    }
    
	private final ObservableInterface myObservable = new Observable(this);
	
	@Override
	public synchronized void addObserver(final ObserverInterface observer) {
		myObservable.addObserver(observer);
	}
	
	@Override
	public int countObservers() {
		return myObservable.countObservers();
	}

	
	@Override
	public List<ObserverInterface> getObservers() {
		return myObservable.getObservers();
	}
	
	@Override
	public synchronized void deleteObserver(final ObserverInterface observer){
		myObservable.deleteObserver(observer);
	}
	
	@Override
	public synchronized void deleteObservers() {
		myObservable.deleteObservers();
	}
	
	@Override
	public void notifyObservers(){
		myObservable.notifyObservers();
	}
	
	@Override
	public void notifyObservers(final Object arg) {
		myObservable.notifyObservers(arg);
	}


}
