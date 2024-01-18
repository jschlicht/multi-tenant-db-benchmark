package com.github.jschlicht.multitenantdbbenchmark.util

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jooq.ExecuteContext
import org.jooq.ExecuteListener
import org.jooq.conf.ParamType
import java.io.PrintWriter

private val logger = KotlinLogging.logger {}

class SqlOutputExecutionListener(private val printWriter: PrintWriter) : ExecuteListener {
    override fun executeStart(ctx: ExecuteContext) {
        ctx.query().let {
            if (it != null) {
                printWriter.print(it.getSQL(ParamType.INLINED))
                printWriter.println(";")
                printWriter.println()
            } else {
                logger.warn { "Received an execution logger for a non-query" }
            }
        }
    }
}