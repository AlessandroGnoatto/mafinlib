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
package org.mafinlib.library.iterators;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class contains a static factory methods intended to create {@link Iterable} objects
 * backed by different data structures.
 * <p>
 * The documentation for the methods contained in this class includes briefs description of the implementations. Such descriptions
 * should be regarded as implementation notes, rather than parts of the specification. Implementors should feel free to substitute
 * other algorithms, so long as the specification itself is adhered to. (For example, the algorithm used by a certain method
 * can be changed, but must satisfy the requirements stated in the documentation.)
 *
 * @author Richard Gomes
 */
public class Iterables {

    /**
     * Returns an unmodifiable view of the specified {@link Enumeration}.
     * <p>
     * This method allows modules to provide users with "read-only" access to internal enumerations.
     * Query operations on the returned list "read through" to the specified Enumeration, and attempts to modify the
     * returned Enumeration, whether direct or via its iterator, result in an UnsupportedOperationException.
     *
     * @param <T> is the type of elements in the specified Enumeration
     * @param en is an Enumeration<T>
     * @return an Iterable<T>
     */
    public static <T> Iterable<T> unmodifiableIterable(final Enumeration<T> en) {
        return new IterableEnumeration<T>(en);

    }

    /**
     * Returns an unmodifiable view of the specified {@link Iterator}.
     * <p>
     * This method allows modules to provide users with "read-only" access to internal iterators.
     * Query operations on the returned list "read through" to the specified Iterator, and attempts to modify the
     * returned Iterator, result in an UnsupportedOperationException.
     *
     * @param <T> is the type of elements in the specified Iterator
     * @param en is an Iterator<T>
     * @return an Iterable<T>
     */
    public static <T> Iterable<T> unmodifiableIterable(final Iterator<T> it) {
        return new IterableIterator<T>(it);
    }

    /**
     * Returns an unmodifiable view of the specified {@link List}.
     * <p>
     * This method allows modules to provide users with "read-only" access to internal enumerations.
     * Query operations on the returned list "read through" to the specified List, and attempts to modify the
     * returned List, whether direct or via its iterator, result in an UnsupportedOperationException.
     *
     * @param <T> is the type of elements in the specified List
     * @param en is an List<T>
     * @return an Iterable<T>
     */
    public static <T> Iterable<T> unmodifiableIterable(final List<T> list) {
        return new IterableIterator<T>(list.iterator());
    }

    /**
     * Returns an unmodifiable view of the specified {@link Set}.
     * <p>
     * This method allows modules to provide users with "read-only" access to internal enumerations.
     * Query operations on the returned list "read through" to the specified Set, and attempts to modify the
     * returned Set, whether direct or via its iterator, result in an UnsupportedOperationException.
     *
     * @param <T> is the type of elements in the specified Set
     * @param en is an Set<T>
     * @return an Iterable<T>
     */
    public static <T> Iterable<T> unmodifiableIterable(final Set<T> set) {
        return new IterableIterator<T>(set.iterator());
    }


    //
    // private static inner classes
    //

    private static class IterableEnumeration<T> implements Iterable<T> {

        private final Enumeration<T> en;

        private IterableEnumeration(final Enumeration<T> en) {
            this.en = en;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                public boolean hasNext() {
                    return en.hasMoreElements();
                }

                public T next() {
                    return en.nextElement();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }


    private static class IterableIterator<T> implements Iterable<T> {

        private final Iterator<T> it;

        private IterableIterator(final Iterator<T> it) {
            this.it = it;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                public T next() {
                    return it.next();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

}