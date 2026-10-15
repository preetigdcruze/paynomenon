package com.paynomenon

import com.paynomenon.common.PaynomenonProperties
import java.math.BigDecimal
import java.time.Duration

/** Builds a PaynomenonProperties instance for unit tests without starting Spring. */
object PropsFactory {
    fun props(
        simulationEnabled: Boolean = true,
        simulationSeed: Long = 42,
        environment: String = "development",
    ) = PaynomenonProperties(
        environment,
        false,
        PaynomenonProperties.Security(
            "test-secret-test-secret-test-secret-1234",
            60,
            "svc",
            "prom",
            "prom-pass",
            "cb-secret",
            true,
            "v",
            "i",
            "a",
            65536,
        ),
        PaynomenonProperties.Payments(
            listOf("EUR", "USD", "GBP", "JPY"),
            BigDecimal("1000000"),
            Duration.ofMinutes(2),
            Duration.ofSeconds(30),
            Duration.ofHours(24),
            Duration.ofSeconds(60),
        ),
        PaynomenonProperties.Processors(
            listOf("MOCK_PROCESSOR", "MOCK_PROCESSOR_EU"),
            Duration.ofSeconds(2),
            PaynomenonProperties.Retry(3, Duration.ofMillis(10), Duration.ofMillis(50), 2.0, Duration.ofSeconds(5)),
            PaynomenonProperties.CircuitBreaker(20, 10, 0.5, Duration.ofSeconds(15), 3),
            50,
        ),
        PaynomenonProperties.Simulation(simulationEnabled, false, simulationSeed),
        PaynomenonProperties.Events(
            "in-process",
            "paynomenon.events.v1",
            3,
            "paynomenon-event-handlers",
            PaynomenonProperties.Outbox(50, Duration.ofMillis(500), 5, Duration.ofMillis(10), Duration.ofMillis(100), Duration.ofDays(7)),
            PaynomenonProperties.ConsumerRetry(3, Duration.ofMillis(5), Duration.ofMillis(20), 2.0, Duration.ofSeconds(5)),
        ),
        PaynomenonProperties.UncertainResolution(5),
        PaynomenonProperties.Redis(false),
        PaynomenonProperties.RateLimit(20, 20),
        PaynomenonProperties.Analytics(Duration.ofDays(31), Duration.ofHours(24), 20, Duration.ofSeconds(10)),
        PaynomenonProperties.Ai("http://localhost:1", "tok", Duration.ofMillis(200), Duration.ofSeconds(1), Duration.ofDays(30)),
        PaynomenonProperties.Retention(Duration.ofDays(7), Duration.ofDays(14)),
        PaynomenonProperties.Seed(false),
        PaynomenonProperties.Runbooks(false),
    )
}
