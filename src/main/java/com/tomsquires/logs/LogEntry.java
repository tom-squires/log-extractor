package com.tomsquires.logs;

public class LogEntry {
    private final long requestTimestamp;
    private final String countryCode;
    private final long responseTime;

    public LogEntry(final String row) {
        final String[] fields = row.split(",");
        this.requestTimestamp = Long.valueOf(fields[0]);
        this.countryCode = fields[1];
        this.responseTime = Long.valueOf(fields[2]);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public long getResponseTime() {
        return responseTime;
    }
}
