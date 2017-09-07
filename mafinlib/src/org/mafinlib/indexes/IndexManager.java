package org.mafinlib.indexes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mafinlib.TimeSeries;
import org.mafinlib.patterns.Observable;
import org.mafinlib.patterns.ObservableInterface;

public class IndexManager {
	
	private static Map<String, TimeSeries<Double>> data;
    private static volatile IndexManager instance;

    private IndexManager(){
    	this.data = new ConcurrentHashMap<String, TimeSeries<Double>>();
    }
    
    public static IndexManager getInstance() {
		if (instance == null) {
			synchronized (IndexManager.class) {
				if (instance == null) {
					instance = new IndexManager();
				}
			}
		}
		return instance;
	}
    
    public boolean hasHistory(final String name){
    	return (data.get(name).firstKey()).eq(data.get(name).lastKey());
    }
    
	public TimeSeries<Double> getHistory(final String name) {
		return data.get(name);
	}

	public void setHistory(final String name, final TimeSeries<Double> history) {
		data.put(name, history);
	}
	
	public ObservableInterface notifier(final String name) {
	    TimeSeries<Double> value = data.get(name);
		if (value == null){
			value = new TimeSeries<Double>(Double.class);
			data.put(name, value);
		}
		return new ObservableValue<TimeSeries<Double>>(value);
	}
	
	public void clearHistory(final String name) {
		data.remove(name);
	}

	public void clearHistories() {
		data.clear();
	}

}
