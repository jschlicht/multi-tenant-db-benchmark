package com.github.jschlicht.multitenantdbbenchmark.util

import java.util.*

/*
 * A simplified adaptation of Guava's Closer class that operates on AutoCloseable objects.
 * See: https://github.com/google/guava/blob/6be1208afff8dbd66bf44a448e8062c7b074f31c/guava/src/com/google/common/io/Closer.java#L93
 */
class AutoCloser : AutoCloseable {
    private val stack = ArrayDeque<AutoCloseable>(DEFAULT_STACK_SIZE)
    private var exceptionResult: Exception? = null

    fun <C : AutoCloseable> register(autoCloseable: C): C {
        stack.addFirst(autoCloseable)
        return autoCloseable
    }

    fun save(ex: Exception): Exception {
        return exceptionResult.let {
            if (it == null) {
                exceptionResult = ex
                ex
            } else {
                it.addSuppressed(ex)
                it
            }
        }
    }

    override fun close() {
        while (!stack.isEmpty()) {
            processCloseable(stack.removeFirst())
        }

        exceptionResult?.let {
            throw it
        }
    }

    private fun processCloseable(closeable: AutoCloseable) {
        try {
            closeable.close()
        } catch (ex: Exception) {
            save(ex)
        }
    }

    companion object {
        private const val DEFAULT_STACK_SIZE = 4
    }
}
