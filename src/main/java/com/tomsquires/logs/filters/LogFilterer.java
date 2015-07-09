package com.tomsquires.logs.filters;

import com.tomsquires.logs.LogEntry;

import java.util.List;

public interface LogFilterer {
    List<LogEntry> filterLogEntries();
}
