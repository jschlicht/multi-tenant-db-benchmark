package com.github.jschlicht.multitenantdbbenchmark.model

import com.github.jschlicht.multitenantdbbenchmark.core.BenchmarkContext
import com.github.jschlicht.multitenantdbbenchmark.core.db.Postgres
import com.github.jschlicht.multitenantdbbenchmark.core.strategy.TenantIdComposite
import com.github.jschlicht.multitenantdbbenchmark.definition.DefinitionGenerator
import org.jooq.codegen.GenerationTool
import org.jooq.codegen.KotlinGenerator
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target
import org.jooq.meta.postgres.PostgresDatabase

class JooqGenerator {
    fun run() {
        BenchmarkContext(
            database = Postgres,
            strategy = TenantIdComposite,
            outputPath = null, verbose = false,
            hashPartitionCount = 0,
            tenantCount = 0
        ).use {
            DefinitionGenerator(it).run(shopIds = listOf())

            Configuration().apply {
                onUnused = OnError.FAIL

                jdbc = Jdbc().apply {
                    driver = org.postgresql.Driver::class.qualifiedName
                    url = it.container.jdbcUrl
                    user = it.container.username
                    password = it.container.password
                }

                generator = Generator().apply {
                    name = KotlinGenerator::class.qualifiedName

                    database = Database().apply {
                        name = PostgresDatabase::class.qualifiedName
                        inputSchema = "public"

                        recordTimestampFields = "updated_at"

                        excludes = listOf("citext.*","max", "min", "regexp.*", "replace", "split_part", "strpos",
                            "textic.*", "translate"
                        ).joinToString(separator = " | ")
                    }

                    generate = Generate().apply {
                        isKotlinNotNullPojoAttributes = true
                        isPojos = true
                        isPojosEqualsAndHashCode = false
                        isPojosToString = false
                        isPojosAsKotlinDataClasses = true
                        isRelations = false
                    }

                    target = Target().apply {
                        packageName = "com.github.jschlicht.multitenantdbbenchmark.model.jooq"
                        directory = "model/src/main/kotlin"
                    }
                }

                GenerationTool.generate(this)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            JooqGenerator().run()
        }
    }
}
