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
import kotlin.io.path.Path
import kotlin.io.path.exists

class JooqGenerator {
    fun run() {
        BenchmarkContext(
            database = Postgres,
            strategy = TenantIdComposite,
            outputPath = null,
            verbose = false,
            hashPartitionCount = 0,
            tenantCount = 0
        ).use { ctx ->
            DefinitionGenerator(ctx).run(shopIds = listOf())
            GenerationTool.generate(configuration(ctx))
        }
    }

    private fun configuration(ctx: BenchmarkContext): Configuration {
        return Configuration().apply {
            onUnused = OnError.FAIL
            jdbc = jdbc(ctx)
            generator = generator()
        }
    }

    private fun jdbc(ctx: BenchmarkContext): Jdbc {
        return Jdbc().apply {
            driver = org.postgresql.Driver::class.qualifiedName
            url = ctx.container.jdbcUrl
            user = ctx.container.username
            password = ctx.container.password
        }
    }

    private fun generator(): Generator {
        return Generator().apply {
            name = KotlinGenerator::class.qualifiedName
            database = database()
            generate = generate()
            target = target()
        }
    }

    private fun database(): Database {
        return Database().apply {
            name = PostgresDatabase::class.qualifiedName
            inputSchema = "public"

            recordTimestampFields = "updated_at"

            excludes = listOf(
                "citext.*", "max", "min", "regexp.*", "replace", "split_part", "strpos",
                "textic.*", "translate"
            ).joinToString(separator = " | ")
        }
    }

    private fun generate(): Generate {
        return Generate().apply {
            isKotlinNotNullPojoAttributes = true
            isPojos = true
            isPojosEqualsAndHashCode = false
            isPojosToString = false
            isPojosAsKotlinDataClasses = true
            isRelations = false
        }
    }

    private fun target(): Target {
        /* Ensure files are outputted to the correct directory regardless of whether the current working directory is
         * the project root (running from IDEA) or the model module (running tests).
         */

        val currentDirectory = Path(".")

        val targetPath = if (Path(".", "gradlew").exists()) {
            Path("model")
        } else if (Path("..", "gradlew").exists()) {
            Path(".")
        } else {
            throw IllegalStateException("Unable to determine project root directory")
        }.resolve(Path("src", "main", "kotlin"))

        return Target().apply {
            packageName = "com.github.jschlicht.multitenantdbbenchmark.model.jooq"
            directory = targetPath.toString()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            JooqGenerator().run()
        }
    }
}
