package com.paynomenon.processors

import com.paynomenon.common.FieldIssue
import com.paynomenon.common.PaynomenonProperties
import com.paynomenon.common.ValidationException
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class ProcessorRegistry(
    props: PaynomenonProperties,
    control: SimulationControl,
    ledger: MockProcessorLedger,
    callbacks: ProcessorCallbackSink,
    clock: Clock,
) {
    private val processors: Map<String, PaymentProcessor> =
        props.processors.names.associateWith { MockPaymentProcessor(it, control, ledger, callbacks, clock) }

    val names: List<String> get() = processors.keys.toList()

    fun find(name: String): PaymentProcessor? = processors[name]

    fun require(name: String?): PaymentProcessor =
        processors[name]
            ?: throw ValidationException(
                "Unknown processor",
                listOf(FieldIssue("processor", "unknown processor '$name'; available: $names")),
            )
}
