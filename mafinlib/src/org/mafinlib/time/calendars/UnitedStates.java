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
package org.mafinlib.time.calendars;

import org.mafinlib.time.Month;
import org.mafinlib.time.Weekday;
import org.mafinlib.library.exceptions.LibraryException;
import org.mafinlib.time.Calendar;
import org.mafinlib.time.Date;


/**
 * United States calendars <br>
 * Public holidays (see: http://www.opm.gov/fedhol/):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday if actually on
 * Sunday, or to Friday if on Saturday)</li>
 * <li>Martin Luther King's birthday, third Monday in JANUARY</li>
 * <li>Presidents' Day (a.k.a. Washington's birthday), third Monday in February</li>
 * <li>Memorial Day, last Monday in May</li>
 * <li>Independence Day, July 4th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Labor Day, first Monday in September</li>
 * <li>Columbus Day, second Monday in October</li>
 * <li>Veterans' Day, November 11th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * </ul>
 *
 * Holidays for the stock exchange (data from http://www.nyse.com):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday if actually on
 * Sunday)</li>
 * <li>Martin Luther King's birthday, third Monday in JANUARY (since 1998)</li>
 * <li>Presidents' Day (a.k.a. Washington's birthday), third Monday in February</li>
 * <li>Good Friday</li>
 * <li>Memorial Day, last Monday in May</li>
 * <li>Independence Day, July 4th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Labor Day, first Monday in September</li>
 * <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Presidential election day, first Tuesday in November of election years
 * (until 1980)</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Special historic closings (see http://www.nyse.com/pdfs/closings.pdf)</li>
 * </ul>
 *
 * Holidays for the government bond market (data from
 * http://www.bondmarkets.com):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday if actually on
 * Sunday)</li>
 * <li>Martin Luther King's birthday, third Monday in JANUARY</li>
 * <li>Presidents' Day (a.k.a. Washington's birthday), third Monday in February</li>
 * <li>Good Friday</li>
 * <li>Memorial Day, last Monday in May</li>
 * <li>Independence Day, July 4th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Labor Day, first Monday in September</li>
 * <li>Columbus Day, second Monday in October</li>
 * <li>Veterans' Day, November 11th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday or Friday if
 * Saturday)</li>
 * </ul>
 *
 * Holidays for the North American Energy Reliability Council (data from
 * http://www.nerc.com/~oc/offpeaks.html):
 * <ul>
 * <li>Saturdays</li>
 * <li>Sundays</li>
 * <li>New Year's Day, JANUARY 1st (possibly moved to Monday if actually on
 * Sunday)</li>
 * <li>Memorial Day, last Monday in May</li>
 * <li>Independence Day, July 4th (moved to Monday if Sunday)</li>
 * <li>Labor Day, first Monday in September</li>
 * <li>Thanksgiving Day, fourth Thursday in November</li>
 * <li>Christmas, December 25th (moved to Monday if Sunday)</li>
 * </ul>
 *
 * @category calendars
 *
 * @author Alessandro Gnoatto
 *
 */
public class UnitedStates extends Calendar{
	
    /**
     * US calendars
     */
    public static enum Market {
        SETTLEMENT,     // generic settlement calendar
        NYSE,           // New York stock exchange calendar
        GOVERNMENTBOND, // government-bond calendar
        NERC            // off-peak days for NERC
    }
    
    public UnitedStates(final Market market){
        switch (market) {
        case SETTLEMENT:
            impl = new SettlementImpl();
            break;
        case NYSE:
            impl = new NyseImpl();
            break;
        case GOVERNMENTBOND:
            impl = new GovernmentBondImpl();
            break;
        case NERC:
            impl = new NercImpl();
            break;
        default:
            throw new LibraryException(UNKNOWN_MARKET);
        }
    }
    
    private boolean isWashingtonBirthday(int d, Month m, int y, Weekday w) { 
    	if (y >= 1971) {
    		// third Monday in February 
    		return (d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.February;
    	}else{
    		// February 22nd, possily adjusted 
    		return (d == 22 || (d == 23 && w == Weekday.Monday) || (d == 21 && w == Weekday.Friday)) && m == Month.February; 
    	}
    }
    
    private boolean isMemorialDay(int d, Month m, int y, Weekday w) {
    	if (y >= 1971) {
    		// last Monday in May
    		return d >= 25 && w == Weekday.Monday && m == Month.May;
    	}else{
    		// May 30th, possibly adjusted 
    		return (d == 30 || (d == 31 && w == Weekday.Monday) || (d == 29 && w == Weekday.Friday)) && m == Month.May;
    	}
    }
    
    private boolean isLaborDay(int d, Month m, int y, Weekday w) {
    	// first Monday in September 
    	return d <= 7 && w == Weekday.Monday && m == Month.September; 
    }
    
    private boolean isColumbusDay(int d, Month m, int y, Weekday w){
    	// second Monday in October
    	return (d >= 8 && d <= 14) && w == Weekday.Monday && m == Month.October  && y >= 1971; 
    }
    
    private boolean isVeteransDay(int d, Month m, int y, Weekday w){
    	if (y <= 1970 || y >= 1978) {
    		// November 11th, adjusted 
    		return (d == 11 || (d == 12 && w == Weekday.Monday) || (d == 10 && w == Weekday.Friday)) && m == Month.November;
    	}else{
    		// fourth Monday in October
    		return (d >= 22 && d <= 28) && w == Weekday.Monday && m == Month.October;
    	}
    }
    
    private final class SettlementImpl extends WesternImpl {

        @Override
        public String name() { return "US settlement"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();
            if (isWeekend(w)
                // New Year's Day (possibly moved to Monday if on Sunday)
                || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                // (or to Friday if on Saturday)
                || (d == 31 && w == Weekday.Friday && m == Month.December)
                // Martin Luther King's birthday (third Monday in January)
                || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January && y >= 1983)
                // Washington's birthday (third Monday in February)
                || isWashingtonBirthday(d, m, y, w)
                // Memorial Day (last Monday in May)
                || isMemorialDay(d, m, y, w)
                // Independence Day (Monday if Sunday or Friday if Saturday)
                || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                     (d == 3 && w == Weekday.Friday)) && m == Month.July)
                // Labor Day (first Monday in September)
                || isLaborDay(d, m, y, w)
                // Columbus Day (second Monday in October)
                || isColumbusDay(d, m, y, w)
                // Veteran's Day (Monday if Sunday or Friday if Saturday)
                || isVeteransDay(d, m, y, w)
                // Thanksgiving Day (fourth Thursday in November)
                || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                // Christmas (Monday if Sunday or Friday if Saturday)
                || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                     (d == 24 && w == Weekday.Friday)) && m == Month.December))
                return false;
            return true;
        }
    }

    private final class NyseImpl extends WesternImpl {

        @Override
        public String name() { return "New York stock exchange"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                // New Year's Day (possibly moved to Monday if on Sunday)
                || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                // Washington's birthday (third Monday in Month.FEBRUARY)
                || isWashingtonBirthday(d, m, y, w)
                // Good Weekday.FRIDAY
                || (dd == em-3)
                // Memorial Day (last Weekday.MONDAY in Month.MAY)
                || isMemorialDay(d, m, y, w)
                // Independence Day (Weekday.MONDAY if Sunday or Weekday.FRIDAY if Saturday)
                || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                     (d == 3 && w == Weekday.Friday)) && m == Month.July)
                // Labor Day (first Weekday.MONDAY in Month.SEPTEMBER)
                || isLaborDay(d, m, y, w)
                // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                // Christmas (Weekday.MONDAY if Sunday or Weekday.FRIDAY if Saturday)
                || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                     (d == 24 && w == Weekday.Friday)) && m == Month.December)
                )
                return false;

            if (y >= 1998) {
                if (// Martin Luther King's birthday (third Weekday.MONDAY in JANUARY)
                    ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January)
                    // President Reagan's funeral
                    || (y == 2004 && m == Month.June && d == 11)
                    // Month.SEPTEMBER 11, 2001
                    || (y == 2001 && m == Month.September && (11 <= d && d <= 14))
                    // President Ford's funeral
                    || (y == 2007 && m == Month.January && d == 2)
                    )
                    return false;
            } else if (y <= 1980) {
                if (// Presidential election days
                    ((y % 4 == 0) && m == Month.November && d <= 7 && w == Weekday.Tuesday)
                    // 1977 Blackout
                    || (y == 1977 && m == Month.July && d == 14)
                    // Funeral of former President Lyndon B. Johnson.
                    || (y == 1973 && m == Month.January && d == 25)
                    // Funeral of former President Harry S. Truman
                    || (y == 1972 && m == Month.December && d == 28)
                    // National Day of Participation for the lunar exploration.
                    || (y == 1969 && m == Month.July && d == 21)
                    // Funeral of former President Eisenhower.
                    || (y == 1969 && m == Month.March && d == 31)
                    // Closed all day - heavy snow.
                    || (y == 1969 && m == Month.February && d == 10)
                    // Day after Independence Day.
                    || (y == 1968 && m == Month.July && d == 5)
                    // Month.JUNE 12-Dec. 31, 1968
                    // Four day week (closed on Wednesdays) - Paperwork Crisis
                    || (y == 1968 && dd >= 163 && w == Weekday.Wednesday)
                    )
                    return false;
            } else if (// Nixon's funeral
                (y == 1994 && m == Month.April && d == 27)
                )
                return false;
            return true;
        }
    }

    private final class GovernmentBondImpl extends WesternImpl {

        @Override
        public String name() { return "US government bond market"; }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth(), dd = date.dayOfYear();
            final Month m = date.month();
            final int y = date.year();
            final int em = easterMonday(y);
            if (isWeekend(w)
                // New Year's Day (possibly moved to Weekday.MONDAY if on Sunday)
                || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                // Martin Luther King's birthday (third Weekday.MONDAY in Month.JANUARY)
                || ((d >= 15 && d <= 21) && w == Weekday.Monday && m == Month.January)
                // Washington's birthday (third Weekday.MONDAY in Month.FEBRUARY)
                || isWashingtonBirthday(d, m, y, w)
                // Good Weekday.FRIDAY
                || (dd == em-3)
                // Memorial Day (last Monday in Month.MAY)
                || isMemorialDay(d, m, y, w)
                // Independence Day (Monday if Sunday or Weekday.FRIDAY if Saturday)
                || ((d == 4 || (d == 5 && w == Weekday.Monday) ||
                     (d == 3 && w == Weekday.Friday)) && m == Month.July)
                // Labor Day (first Monday in Month.SEPTEMBER)
                || isLaborDay(d, m, y, w)
                // Columbus Day (second Monday in October)
                || isColumbusDay(d, m, y, w)
                // Veteran's Day (Monday if Sunday or Weekday.FRIDAY if Saturday)
                || isVeteransDay(d, m, y, w)
                // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                // Christmas (Monday if Sunday or Weekday.FRIDAY if Saturday)
                || ((d == 25 || (d == 26 && w == Weekday.Monday) ||
                     (d == 24 && w == Weekday.Friday)) && m == Month.December))
                return false;
            return true;
        }
    }

    private final class NercImpl extends WesternImpl {

        @Override
        public String name(){ return "North American Energy Reliability Council";  }

        @Override
        public boolean isBusinessDay(final Date date) {
            final Weekday w = date.weekday();
            final int d = date.dayOfMonth();
            final Month m = date.month();
            final int y = date.year();
            if (isWeekend(w)
                // New Year's Day (possibly moved to Monday if on Sunday)
                || ((d == 1 || (d == 2 && w == Weekday.Monday)) && m == Month.January)
                // Memorial Day (last Monday in Month.MAY)
                || isMemorialDay(d, m, y, w)
                // Independence Day (Monday if Sunday)
                || ((d == 4 || (d == 5 && w == Weekday.Monday)) && m == Month.July)
                // Labor Day (first Monday in Month.SEPTEMBER)
                || isLaborDay(d, m, y, w)
                // Thanksgiving Day (fourth Weekday.THURSDAY in Month.NOVEMBER)
                || ((d >= 22 && d <= 28) && w == Weekday.Thursday && m == Month.November)
                // Christmas (Monday if Sunday)
                || ((d == 25 || (d == 26 && w == Weekday.Monday)) && m == Month.December))
                return false;
            return true;
        }
     }
}
