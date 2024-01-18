package com.github.jschlicht.multitenantdbbenchmark.table

interface Table

interface GlobalTable : Table
interface TenantTable : Table
