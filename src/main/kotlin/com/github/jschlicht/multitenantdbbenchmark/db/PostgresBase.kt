package com.github.jschlicht.multitenantdbbenchmark.db

abstract class PostgresBase(key: String, containerName: String) : Database(key, containerName)