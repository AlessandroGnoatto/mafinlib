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
package org.mafinlib.math;

import org.mafinlib.library.exceptions.LibraryException;

public class Rounding {
	
    private int precision;
    public Type type_;
    private int digit_;
    
    /**
     * The rounding methods follow the OMG specification available at ftp://ftp.omg.org/pub/docs/formal/00-06-29.pdf
     *<p>
     * Warning the names of the Floor and Ceiling methods might be misleading. Check the provided reference.
     */
    public enum Type {
        /**
         * Do not round: return the number unmodified
         */
        None,

        /**
         * The first decimal place past the precision will be rounded up. This differs from the OMG rule which rounds up only
         * if the decimal to be rounded is greater than or equal to the rounding digit
         */
        Up,

        /**
         *  All decimal places past the precision will be truncated
         */
        Down,

        /**
         * The first decimal place past the precision will be rounded up if greater than or equal to the rounding digit;
         * this corresponds to the OMG round-up rule. When the rounding digit is 5, the result will be the one closest to
         * the original number, hence the name.
         */
        Closest,

        /**
         * Positive numbers will be rounded up and negative numbers will be rounded down using the OMG round up and round
         * down rules
         */
        Floor,

        /**
         * Positive numbers will be rounded down and negative numbers will be rounded up using the OMG round up and round down
         * rules
         */
        Ceiling
    };
    
    
    /**
     *  Instances built through this constructor don't perform any rounding.
     */
    public Rounding() {
        this.type_ = Type.None;
    }

    public Rounding(final int precision) {
        this(precision, Type.Closest, 5);
    }

    public Rounding(final int precision, final Type type /* = Closest */, final int digit /* = 5 */) {
        this.precision = (precision);
        this.type_ = (type);
        this.digit_ = (digit);
    }
    
    public int precision() {
        return precision;
    }

    public Type type() {
        return type_;
    }

    public int roundingDigit() {
        return digit_;
    }
    
    final public double operator(double value)  {

        if (type_ == Rounding.Type.None)
            return value;

        final double mult = Math.pow(10.0,precision);
        final boolean neg = (value < 0.0);
        double lvalue = Math.abs(value)*mult;
        final double modVal = (lvalue-(int)lvalue );
        lvalue -= modVal;
        switch (type_) {
          case Down:
            break;
          case Up:
            lvalue += 1.0;
            break;
          case Closest:
            if (modVal >= (digit_/10.0)){
                lvalue += 1.0;
            }
            break;
          case Floor:
            if (!neg) {
                if (modVal >= (digit_/10.0)){
                    lvalue += 1.0;
                }
            }
            break;
          case Ceiling:
            if (neg) {
                if (modVal >= (digit_/10.0)) {
                    lvalue += 1.0;
                }
            }
            break;
          default:
            throw new LibraryException("unknown rounding method");
        }
        return (neg) ? -(lvalue/mult) : lvalue/mult;
    }
    
    // Up-rounding.
    public static class UpRounding extends Rounding {
        public UpRounding(final int precision) {
            this(precision, 5);
        }

        public UpRounding(final int precision, final int digit) {
            super(precision, Type.Up, digit);
        }
    };

    // Down-rounding.
    public static class DownRounding extends Rounding {
        public DownRounding(final int precision) {
            this(precision, 5);
        }

        public DownRounding(final int precision, final int digit) {
            super(precision, Type.Down, digit);
        }
    };

    // Closest rounding.
    public static class ClosestRounding extends Rounding {
        public ClosestRounding(final int precision) {
            this(precision, 5);
        }

        public ClosestRounding(final int precision, final int digit) {
            super(precision, Type.Closest, digit);
        }
    };

    // Ceiling truncation.
    public static class CeilingTruncation extends Rounding {
        public CeilingTruncation(final int precision) {
            this(precision, 5);
        }

        public CeilingTruncation(final int precision, final int digit) {
            super(precision, Type.Ceiling, digit);
        }
    };


    // Floor truncation.
    public static class FloorTruncation extends Rounding {
        public FloorTruncation(final int precision) {
            this(precision, 5);
        }

        public FloorTruncation(final int precision, final int digit) {
            super(precision, Type.Floor, digit);
        }
    };
}
