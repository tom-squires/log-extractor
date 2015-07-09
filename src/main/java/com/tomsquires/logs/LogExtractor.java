package com.tomsquires.logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class LogExtractor {
    private static final String REQUEST_TIMESTAMP_HEADER = "REQUEST_TIMESTAMP";
    private final BufferedReader bufferedReader;

    public LogExtractor(Reader source) {
        bufferedReader = new BufferedReader(source);
    }

    public List<LogEntry> extractLogEntries() {
        try {
            final List<LogEntry> logEntries = new LinkedList<>();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (!line.startsWith(REQUEST_TIMESTAMP_HEADER)) {
                    logEntries.add(new LogEntry(line));
                }
            }
            return logEntries;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
