package org.mafinlib.time.daycounters;

import org.mafinlib.time.Date;
import org.mafinlib.time.DayCounter;

/**
 * Actual/360 day count convention, also known as "Act/360", or "A/360".
 *
 * @author Alessandro Gnoatto
 *
 */
public class Actual360 extends DayCounter{
	
    public Actual360() {
        super.impl = new Impl();
    }

	
    final private class Impl extends DayCounter.Impl {

        @Override
        public final String name(){
            return "Actual/360";
        }

        @Override
        public final double yearFraction(
                final Date dateStart, final Date dateEnd,
                final Date refPeriodStart, final Date refPeriodEnd){
            return dayCount(dateStart, dateEnd) / 360.0;
        }

    }	
}
