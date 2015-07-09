package com.tomsquires;

import org.junit.Test;
import com.tomsquires.logs.LogEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void filterByCountryShouldReturnCountryFilteredEntriesFromMultiLineFile() throws FileNotFoundException {
        // When
        final Collection<?> logEntries = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US");

        // Then
        assertEquals("should be 3 log entries", 3, logEntries.size());

        // And
        for (Object logEntry : logEntries) {
            assertTrue("expected a LogEntry", logEntry instanceof LogEntry);
            assertEquals("country code should be 'US'", "US", ((LogEntry) logEntry).getCountryCode());
        }
    }

    @Test
    public void filterByCountryShouldReturnCountryFilteredEntryFromSingleLineFile() throws FileNotFoundException {
        // When
        final Collection<?> logEntries = DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB");

        // Then
        assertEquals("should be 1 log entry", 1, logEntries.size());

        // And
        final LogEntry logEntry = (LogEntry) ((List) logEntries).get(0);
        assertEquals("country code should be 'GB'", "GB", logEntry.getCountryCode());
    }

    @Test
    public void filterByCountryShouldReturnNoEntriesWhenCountryNotPresent() throws FileNotFoundException {
        // When
        final Collection<?> logEntries = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "AU");

        // Then
        assertTrue("list should be empty", logEntries.isEmpty());
    }

    @Test
    public void shouldReturnEntriesFromCorrectCountryAndWithResponseTimeAboveSpecifiedLimit() throws FileNotFoundException {
        // When
        final long limit = 700L;
        final Collection<?> logEntries = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", limit);

        // Then
        assertEquals("should be 2 log entries", 2, logEntries.size());

        // And
        for (Object logEntry : logEntries) {
            assertTrue("expected a LogEntry", logEntry instanceof LogEntry);
            assertEquals("country code should be 'US'", "US", ((LogEntry) logEntry).getCountryCode());
            assertTrue("response time should be over '700'", ((LogEntry) logEntry).getResponseTime() > limit);
        }
    }

    @Test
    public void filterByResponseTimeAboveAverageShouldReturnEntriesWhereResponseTimeAboveAverage() throws FileNotFoundException {
        // Given
        final Set<Long> aboveAverageResponseTimes = new HashSet<>(Arrays.asList(539L, 789L, 850L));

        // When
        final Collection<?> logEntries = DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"));

        // Then
        assertEquals("should be 3 log entries", 3, logEntries.size());

        // And
        for (Object logEntry : logEntries) {
            assertTrue("expected a LogEntry", logEntry instanceof LogEntry);
            assertTrue("response time should be in expected Set", aboveAverageResponseTimes.contains(((LogEntry) logEntry).getResponseTime()));
        }
    }

    @Test
    public void whenEmptyFilterByCountryReturnsEmptyList() throws FileNotFoundException {
        assertTrue("list should be empty", DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }

    @Test
    public void whenEmptyFilterByCountryWithResponseTimeAboveLimitReturnsEmptyList() throws FileNotFoundException {
        assertTrue("list should be empty", DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 0L).isEmpty());
    }

    @Test
    public void whenEmptyfilterByResponseTimeAboveAverageReturnsEmptyList() throws FileNotFoundException {
        assertTrue("list should be empty", DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
