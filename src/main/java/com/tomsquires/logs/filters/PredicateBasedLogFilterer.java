package com.tomsquires.logs.filters;

import com.tomsquires.logs.LogEntry;
import com.tomsquires.logs.LogExtractor;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateBasedLogFilterer implements LogFilterer {
    private final Predicate<LogEntry> tester;
    private final LogExtractor logExtractor;

    public PredicateBasedLogFilterer(Reader source, Predicate<LogEntry> tester) {
        this.tester = tester;
        this.logExtractor = new LogExtractor(source);
    }

    @Override
    public List<LogEntry> filterLogEntries() {
        final List<LogEntry> allLogEntries = logExtractor.extractLogEntries();
        final List<LogEntry> filteredLogEntries = new LinkedList<>();
        for (LogEntry logEntry : allLogEntries) {
            if (tester.test(logEntry)) {
                filteredLogEntries.add(logEntry);
            }
        }
        return filteredLogEntries;
    }
}
