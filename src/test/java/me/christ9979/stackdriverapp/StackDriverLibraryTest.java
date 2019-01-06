package me.christ9979.stackdriverapp;

import com.google.api.Metric;
import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.*;
import com.google.cloud.monitoring.v3.MetricServiceClient;
import com.google.monitoring.v3.CreateTimeSeriesRequest;
import com.google.monitoring.v3.Point;
import com.google.monitoring.v3.ProjectName;
import com.google.monitoring.v3.TimeInterval;
import com.google.monitoring.v3.TimeSeries;
import com.google.monitoring.v3.TypedValue;
import com.google.protobuf.util.Timestamps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.*;

@RunWith(JUnit4.class)
public class StackDriverLibraryTest {

    @Test
    public void testLogging() throws InterruptedException {
        Logging logging = LoggingOptions.getDefaultInstance().getService();

        String logName = "test-log";

        String text = "Hello, world!";

        LogEntry entry = LogEntry.newBuilder(Payload.StringPayload.of(text))
                .setSeverity(Severity.INFO)
                .setLogName(logName)
                .addLabel("userId", "asdf1234")
                .setResource(MonitoredResource.newBuilder("global").build())
                .build();

        logging.write(Collections.singleton(entry));

        Thread.sleep(2000);
    }

    @Test
    public void testMetrics() throws IOException {
        // Your Google Cloud Platform project ID
        String projectId = "modular-asset-227707";

        // Instantiates a client
        MetricServiceClient metricServiceClient = MetricServiceClient.create();

        // Prepares an individual data point
        TimeInterval interval = TimeInterval.newBuilder()
                .setEndTime(Timestamps.fromMillis(System.currentTimeMillis()))
                .build();
        TypedValue value = TypedValue.newBuilder()
                .setDoubleValue(123.45)
                .build();
        Point point = Point.newBuilder()
                .setInterval(interval)
                .setValue(value)
                .build();

        List<Point> pointList = new ArrayList<>();
        pointList.add(point);

        ProjectName name = ProjectName.of(projectId);

        // Prepares the metric descriptor
        Map<String, String> metricLabels = new HashMap<String, String>();
        metricLabels.put("store_id", "Pittsburg");

        Metric metric = Metric.newBuilder()
                .setType("custom.googleapis.com/stores/daily_sales")
                .putAllLabels(metricLabels)
                .build();

        // Prepares the monitored resource descriptor
        Map<String, String> resourceLabels = new HashMap<String, String>();
        resourceLabels.put("project_id", projectId);
        com.google.api.MonitoredResource resource = com.google.api.MonitoredResource.newBuilder()
                .setType("global")
                .putAllLabels(resourceLabels)
                .build();

        // Prepares the time series request
        TimeSeries timeSeries = TimeSeries.newBuilder()
                .setMetric(metric)
                .setResource(resource)
                .addAllPoints(pointList)
                .build();
        List<TimeSeries> timeSeriesList = new ArrayList<>();
        timeSeriesList.add(timeSeries);

        CreateTimeSeriesRequest request = CreateTimeSeriesRequest.newBuilder()
                .setName(name.toString())
                .addAllTimeSeries(timeSeriesList)
                .build();

        // Writes time series data
        metricServiceClient.createTimeSeries(request);

        System.out.printf("Done writing time series data.%n");

        metricServiceClient.close();

    }
}
