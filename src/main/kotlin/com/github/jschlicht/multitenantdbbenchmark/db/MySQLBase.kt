package com.github.jschlicht.multitenantdbbenchmark.db

abstract class MySQLBase(key: String, containerName: String) : Database(key, containerName)