package com.paynomenon.admin

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

/**
 * `java -jar paynomenon-backend.jar --paynomenon.migrate-only=true` applies the Flyway migrations (which happens while the
 * context starts) and exits with status 0. Used by `make migrate` and for one-off migration jobs.
 */
@Component
@ConditionalOnProperty("paynomenon.migrate-only", havingValue = "true")
class MigrateOnly(
    private val context: ConfigurableApplicationContext,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        LoggerFactory.getLogger(javaClass).info("Database migrations are up to date; exiting (migrate-only mode)")
        System.exit(SpringApplication.exit(context, { 0 }))
    }
}
