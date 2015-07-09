package com.tomsquires;

import com.tomsquires.logs.LogEntry;
import com.tomsquires.logs.filters.AverageResponseTimeLogFilterer;
import com.tomsquires.logs.filters.LogFilterer;
import com.tomsquires.logs.filters.PredicateBasedLogFilterer;

import java.io.Reader;
import java.util.Collection;
import java.util.function.Predicate;

public class DataFilterer {
    public static Collection<?> filterByCountry(Reader source, String country) {
        final Predicate<LogEntry> countryFilter = (LogEntry logEntry) -> country.equals(logEntry.getCountryCode());
        final LogFilterer predicateBasedLogFilterer = new PredicateBasedLogFilterer(source, countryFilter);
        return predicateBasedLogFilterer.filterLogEntries();
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        final Predicate<LogEntry> countryAndResponseTimeFilter = (LogEntry logEntry) -> country.equals(logEntry.getCountryCode()) && logEntry.getResponseTime() > limit;
        final LogFilterer predicateBasedLogFilterer = new PredicateBasedLogFilterer(source, countryAndResponseTimeFilter);
        return predicateBasedLogFilterer.filterLogEntries();
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
        final LogFilterer averageResponseTimeLogFilterer = new AverageResponseTimeLogFilterer(source);
        return averageResponseTimeLogFilterer.filterLogEntries();
    }
}