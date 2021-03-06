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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.mafinlib.library.exceptions.LibraryException;

/**
 * 
 * @author Alessandro Gnoatto
 *
 */
public class MFL {
    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final String format,
            final Object...objects) throws RuntimeException {
        if (!condition)
            throw new LibraryException(String.format(format, objects));
    }

    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final String message) throws RuntimeException {
        if (!condition)
            throw new LibraryException(message);
    }

    /**
     * Throws an error if a <b>pre-condition</b> is not verified
     * <p>
     * @param condition is a condition to be verified
     * @param klass is a Class which extends RuntimeException
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void require(
            final boolean condition,
            final Class<? extends RuntimeException> klass,
            final String message) throws RuntimeException {
        if (!condition) {
            try {
                final Constructor<? extends RuntimeException> c = klass.getConstructor(String.class);
                throw c.newInstance(message);
            } catch (final SecurityException e) {
                e.printStackTrace();
            } catch (final NoSuchMethodException e) {
                e.printStackTrace();
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
            } catch (final InstantiationException e) {
                e.printStackTrace();
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }



    
    public static void ensure(
            final boolean condition,
            final String format,
            final Object...objects) throws RuntimeException {
        if (!condition)
            throw new LibraryException(String.format(format, objects));
    }

    
    /**
     * Throws an error if a <b>post-condition</b> is not verified
     * <p>
     * @note  this method should <b>never</b> be removed from bytecode by AspectJ.
     *        If you do so, you must be plenty sure of effects and risks of this decision.
     * <p>
     * @param condition is a condition to be verified
     * @param message is a message emitted.
     * @throws a LibraryException if the condition is not met
     */
    public static void ensure(
            final boolean condition,
            final String message) throws RuntimeException {
        if (!condition)
            throw new LibraryException(message);
    }


    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.error(message);
        } else {
            System.err.printf("ERROR: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final String message, final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.error(message, t);
        } else {
            System.err.printf("ERROR: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void error(final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.error(t.getMessage(), t);
        } else {
            System.err.printf("ERROR: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final String message) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.warn(message);
        } else {
            System.err.printf("WARN: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final String message, final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.warn(message, t);
        } else {
            System.err.printf("WARN: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void warn(final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.warn(t.getMessage(), t);
        } else {
            System.err.printf("WARN: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final String message) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.info(message);
        } else {
            System.err.printf("INFO: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final String message, final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.info(message, t);
        } else {
            System.err.printf("INFO: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void info(final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.info(t.getMessage(), t);
        } else {
            System.err.printf("INFO: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }




    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final String message) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.debug(message);
        } else {
            System.err.printf("DEBUG: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final String message, final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.debug(message, t);
        } else {
            System.err.printf("DEBUG: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void debug(final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.debug(t.getMessage(), t);
        } else {
            System.err.printf("DEBUG: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }



    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final String message) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.trace(message);
        } else {
            System.err.printf("TRACE: %s\n", message);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final String message, final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.trace(message, t);
        } else {
            System.err.printf("TRACE: %s : %s\n", message, t.getMessage());
            t.printStackTrace(System.err);
        }
    }

    /**
     * This method unconditionally emits a message to the logging system but does not throw any exception.
     *
     * @param message is a message to be emitted
     */
    public static void trace(final Throwable t) {
        if (MaFinLib.logger!=null) {
            MaFinLib.logger.trace(t.getMessage(), t);
        } else {
            System.err.printf("TRACE: %s\n", t.getMessage());
            System.err.println(t.getMessage());
            t.printStackTrace(System.err);
        }
    }



    /**
     * This method to validate whether code is being run in
     * experimental mode or not
     */
    public static void validateExperimentalMode() {
        if (System.getProperty("EXPERIMENTAL") == null)
            throw new UnsupportedOperationException("Work in progress");
    }

}
