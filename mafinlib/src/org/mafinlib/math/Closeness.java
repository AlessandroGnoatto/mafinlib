package org.mafinlib.math;

final public class Closeness {

	//
	// Static public final methods
	//

	static public final boolean isClose(final double x, final double y) {
	    return isClose(x, y, 42);
	}

	static public final boolean isClose(final double x, final double y,  final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * Constants.MFL_EPSILON;
        return diff <= tolerance*Math.abs(x) &&
               diff <= tolerance*Math.abs(y);
	}

	static public final boolean isCloseEnough(final double x, final double y) {
	    return isCloseEnough(x, y, 42);
	}

	static public final boolean isCloseEnough(final double x, final double y, final int n) {
        final double diff = Math.abs(x-y);
        final double tolerance = n * Constants.MFL_EPSILON;
        return diff <= tolerance*Math.abs(x) ||
               diff <= tolerance*Math.abs(y);
	}

}
