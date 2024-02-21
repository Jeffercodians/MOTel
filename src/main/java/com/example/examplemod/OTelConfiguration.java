package com.example.examplemod;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.opentelemetry.semconv.ResourceAttributes.SERVICE_NAME;

/**
 * All SDK management takes place here, away from the instrumentation code, which should only access
 * the OpenTelemetry APIs.
 */
public final class OTelConfiguration {

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
    Resource resource =
            Resource.getDefault()
                    .merge(Resource.builder().put(SERVICE_NAME, "MOTel").build());

    OpenTelemetrySdk openTelemetrySdk =
            OpenTelemetrySdk.builder()
                    .setTracerProvider(
                            SdkTracerProvider.builder()
                                    .setResource(resource)
                                    .addSpanProcessor(
                                            BatchSpanProcessor.builder(
                                                            OtlpGrpcSpanExporter.builder()
                                                                    .setTimeout(2, TimeUnit.SECONDS)
                                                                    .build())
                                                    .setScheduleDelay(100, TimeUnit.MILLISECONDS)
                                                    .build())
                                    .build())
                    .setMeterProvider(
                            SdkMeterProvider.builder()
                                    .setResource(resource)
                                    .registerMetricReader(
                                            PeriodicMetricReader.builder(OtlpGrpcMetricExporter.getDefault())
                                                    .setInterval(Duration.ofMillis(1000))
                                                    .build())
                                    .build())
                    .buildAndRegisterGlobal();

    Runtime.getRuntime().addShutdownHook(new Thread(openTelemetrySdk::close));

    return openTelemetrySdk;
  }
}
