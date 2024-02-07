package com.example.examplemod;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * All SDK management takes place here, away from the instrumentation code, which should only access
 * the OpenTelemetry APIs.
 */
public final class ExampleConfiguration {

  /**
   * The number of milliseconds between metric exports.
   */
  private static final long METRIC_EXPORT_INTERVAL_MS = 800L;

  /**
   * Initializes an OpenTelemetry SDK with a logging exporter and a SimpleSpanProcessor.
   *
   * @return A ready-to-use {@link OpenTelemetry} instance.
   */
  public static OpenTelemetry initOpenTelemetry() {
    // Export traces to Jaeger over OTLP
    OtlpGrpcSpanExporter jaegerOtlpExporter = getOtlpGrpcSpanExporter();

    // Create an instance of PeriodicMetricReader and configure it
    // to export via the logging exporter
    MetricReader periodicReader =
      PeriodicMetricReader.builder(LoggingMetricExporter.create())
        .setInterval(Duration.ofMillis(METRIC_EXPORT_INTERVAL_MS))
        .build();

    // This will be used to create instruments
    SdkMeterProvider meterProvider =
      SdkMeterProvider.builder().registerMetricReader(periodicReader).build();

    Resource serviceNameResource =
      Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "jeffercodianland-otel-thing"));

    // Tracer provider configured to export spans with SimpleSpanProcessor using
    // the logging exporter.
    SdkTracerProvider tracerProvider =
      SdkTracerProvider.builder()
        .addSpanProcessor(BatchSpanProcessor.builder(jaegerOtlpExporter).build())
        .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
        .setResource(Resource.getDefault().merge(serviceNameResource))
        .build();

    // it's always a good idea to shut down the SDK cleanly at JVM exit.
    Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));

    return OpenTelemetrySdk.builder()
      .setMeterProvider(meterProvider)
      .setTracerProvider(tracerProvider)
      .buildAndRegisterGlobal();
  }

  public static OtlpGrpcSpanExporter getOtlpGrpcSpanExporter() {
    return OtlpGrpcSpanExporter.builder()
      .setEndpoint(getJaegerEndpoint())
      .setTimeout(30, TimeUnit.SECONDS)
      .build();
  }

  public static int readOTelPort() {
    try {
      return Integer.parseInt(Optional.ofNullable(System.getenv("OTEL_PORT")).orElse("4317"));
    } catch (NumberFormatException e) {
      return 4317;
    }
  }

  public static String getJaegerEndpoint() {
    return Optional.ofNullable(System.getenv("OTEL_EXPORTER_JAEGER_ENDPOINT"))
      .orElse("http://localhost:" + readOTelPort());
  }
}
