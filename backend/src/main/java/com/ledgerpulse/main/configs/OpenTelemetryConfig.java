package com.ledgerpulse.main.configs;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {
    public Tracer tracer() {
        return GlobalOpenTelemetry.getTracer("com.LedgerPlus", "1.0.0");
    }
}
