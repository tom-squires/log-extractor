package com.tomsquires.logs.filters;

import com.tomsquires.logs.LogEntry;
import com.tomsquires.logs.LogExtractor;

import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AverageResponseTimeLogFilterer implements LogFilterer {
    private final LogExtractor logExtractor;

    public AverageResponseTimeLogFilterer(Reader source) {
        this.logExtractor = new LogExtractor(source);
    }

    @Override
    public List<LogEntry> filterLogEntries() {
        final List<LogEntry> allLogEntries = logExtractor.extractLogEntries();

        if (allLogEntries.isEmpty()) {
            return Collections.emptyList();
        }

        return filterLogExtracts(allLogEntries);
    }

    /**
     * Filters log extracts so that only those with a response time > average response time are included.
     *
     * @param allLogEntries
     * @return
     */
    private List<LogEntry> filterLogExtracts(List<LogEntry> allLogEntries) {
        final long totalResponseTimes = calculateTotalResponseTimes(allLogEntries);
        final double averageResponseTime = (double)totalResponseTimes / allLogEntries.size();

        final List<LogEntry> filteredLogEntries = new LinkedList<>();
        for (LogEntry logEntry : allLogEntries) {
            if (logEntry.getResponseTime() > averageResponseTime) {
                filteredLogEntries.add(logEntry);
            }
        }
        return filteredLogEntries;
    }

    private long calculateTotalResponseTimes(List<LogEntry> allLogEntries) {
        long totalResponseTime = 0L;

        for (LogEntry logEntry : allLogEntries) {
            totalResponseTime += logEntry.getResponseTime();
        }
        return totalResponseTime;
    }
}
