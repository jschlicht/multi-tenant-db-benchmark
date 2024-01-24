/*
 * This file is generated by jOOQ.
 */
package com.github.jschlicht.multitenantdbbenchmark.model.jooq.keys


import com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.Shop
import com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.records.ShopRecord

import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val SHOP_PKEY: UniqueKey<ShopRecord> = Internal.createUniqueKey(Shop.SHOP, DSL.name("shop_pkey"), arrayOf(Shop.SHOP.ID), true)