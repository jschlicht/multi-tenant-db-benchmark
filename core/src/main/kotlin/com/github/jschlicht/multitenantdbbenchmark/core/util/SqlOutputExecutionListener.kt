package com.github.jschlicht.multitenantdbbenchmark.core.util

import io.github.oshai.kotlinlogging.KotlinLogging
import org.jooq.ExecuteContext
import org.jooq.ExecuteListener
import org.jooq.conf.ParamType
import java.io.PrintWriter

private val logger = KotlinLogging.logger {}

class SqlOutputExecutionListener(
    private val printWriter: PrintWriter?,
    private val verbose: Boolean
) : ExecuteListener {
    override fun executeStart(ctx: ExecuteContext) {
        if (printWriter == null && !verbose) {
            return
        }

        ctx.query().let {
            if (it != null) {
                val inlineSql = it.getSQL(ParamType.INLINED)
                if (printWriter != null) {
                    printWriter.print(inlineSql)
                    printWriter.println(";")
                    printWriter.println()
                }
                if (verbose) {
                    logger.info { inlineSql }
                }
            } else {
                logger.warn { "Received an execution logger for a non-query" }
            }
        }
    }
}