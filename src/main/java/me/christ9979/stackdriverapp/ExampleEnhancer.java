package me.christ9979.stackdriverapp;

import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.LoggingEnhancer;
import com.google.logging.v2.LogMetric;

// Add / update additional fields to the log entry
public class ExampleEnhancer implements LoggingEnhancer {

    @Override
    public void enhanceLogEntry(LogEntry.Builder logEntry) {
        // add additional labels
        logEntry.addLabel("test-label-1", "test-value-1");
    }
}
