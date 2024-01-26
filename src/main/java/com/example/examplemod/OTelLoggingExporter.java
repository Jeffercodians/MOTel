package com.example.examplemod;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.trace.Tracer;

public class OTelLoggingExporter {
    private static final String INSTRUMENTATION_NAME = OTelLoggingExporter.class.getName();

    public final Tracer tracer;
    public final LongCounter counter;

    public OTelLoggingExporter() {
        OpenTelemetry oTel = ExampleConfiguration.initOpenTelemetry();
        this.tracer = oTel.getTracer(INSTRUMENTATION_NAME);
        this.counter = oTel.getMeter(INSTRUMENTATION_NAME).counterBuilder("work_done").build();
    }
}
