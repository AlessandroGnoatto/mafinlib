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
package org.mafinlib.patterns;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Singleton pattern. 
 * @author Alessandro Gnoatto
 *
 */
public class Singleton {
	
	//A basis non generic thread-safe implementation
	/*private static Singleton instance = null;
	
	private static Object mutex = new Object();
	
	private Singleton(){
		
	}
	
	public static Singleton instance(){
		if(instance == null){
			synchronized (mutex){
				if(instance==null){
					instance = new Singleton();
				}
			}
		}
		return instance;
	}*/
	
	private static final Singleton instance = new Singleton();
	
	@SuppressWarnings( "rawtypes")
	private Map<Class,Object> mapHolder = new HashMap<Class,Object>();
	
	private Singleton(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T instance(Class<T> classOf) throws InstantiationException, IllegalAccessException {
		
        synchronized(instance){

            if(!instance.mapHolder.containsKey(classOf)){

                T obj = classOf.newInstance();

                instance.mapHolder.put(classOf, obj);
            }

            return (T)instance.mapHolder.get(classOf);

        }

    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
