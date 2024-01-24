/*
 * This file is generated by jOOQ.
 */
package com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.records;


import com.github.jschlicht.multitenantdbbenchmark.model.jooq.tables.Shops;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ShopsRecord extends UpdatableRecordImpl<ShopsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.shops.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.shops.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.shops.address1</code>.
     */
    public void setAddress1(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.shops.address1</code>.
     */
    public String getAddress1() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.shops.address2</code>.
     */
    public void setAddress2(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.shops.address2</code>.
     */
    public String getAddress2() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.shops.city</code>.
     */
    public void setCity(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.shops.city</code>.
     */
    public String getCity() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.shops.country_code</code>.
     */
    public void setCountryCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.shops.country_code</code>.
     */
    public String getCountryCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.shops.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.shops.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>public.shops.customer_email</code>.
     */
    public void setCustomerEmail(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.shops.customer_email</code>.
     */
    public String getCustomerEmail() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.shops.currency</code>.
     */
    public void setCurrency(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.shops.currency</code>.
     */
    public String getCurrency() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.shops.domain</code>.
     */
    public void setDomain(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.shops.domain</code>.
     */
    public String getDomain() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.shops.email</code>.
     */
    public void setEmail(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.shops.email</code>.
     */
    public String getEmail() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.shops.name</code>.
     */
    public void setName(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.shops.name</code>.
     */
    public String getName() {
        return (String) get(10);
    }

    /**
     * Setter for <code>public.shops.phone</code>.
     */
    public void setPhone(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.shops.phone</code>.
     */
    public String getPhone() {
        return (String) get(11);
    }

    /**
     * Setter for <code>public.shops.province</code>.
     */
    public void setProvince(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.shops.province</code>.
     */
    public String getProvince() {
        return (String) get(12);
    }

    /**
     * Setter for <code>public.shops.shop_owner</code>.
     */
    public void setShopOwner(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.shops.shop_owner</code>.
     */
    public String getShopOwner() {
        return (String) get(13);
    }

    /**
     * Setter for <code>public.shops.timezone</code>.
     */
    public void setTimezone(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.shops.timezone</code>.
     */
    public String getTimezone() {
        return (String) get(14);
    }

    /**
     * Setter for <code>public.shops.updated_at</code>.
     */
    public void setUpdatedAt(LocalDateTime value) {
        set(15, value);
    }

    /**
     * Getter for <code>public.shops.updated_at</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(15);
    }

    /**
     * Setter for <code>public.shops.zip</code>.
     */
    public void setZip(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>public.shops.zip</code>.
     */
    public String getZip() {
        return (String) get(16);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ShopsRecord
     */
    public ShopsRecord() {
        super(Shops.SHOPS);
    }

    /**
     * Create a detached, initialised ShopsRecord
     */
    public ShopsRecord(Long id, String address1, String address2, String city, String countryCode, LocalDateTime createdAt, String customerEmail, String currency, String domain, String email, String name, String phone, String province, String shopOwner, String timezone, LocalDateTime updatedAt, String zip) {
        super(Shops.SHOPS);

        setId(id);
        setAddress1(address1);
        setAddress2(address2);
        setCity(city);
        setCountryCode(countryCode);
        setCreatedAt(createdAt);
        setCustomerEmail(customerEmail);
        setCurrency(currency);
        setDomain(domain);
        setEmail(email);
        setName(name);
        setPhone(phone);
        setProvince(province);
        setShopOwner(shopOwner);
        setTimezone(timezone);
        setUpdatedAt(updatedAt);
        setZip(zip);
        resetChangedOnNotNull();
    }
}
