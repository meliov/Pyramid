package com.nevexis.pyramid;

import com.nevexis.base.BaseEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Person extends BaseEntity {
    private Long parentId;
    private Long accountId;
    private Long taxId;
    private LocalDateTime taxExpirationDate;

    public Person(Long parentId, Long accountId) {
        this.parentId = parentId;
        this.accountId = accountId;
    }

    public Person() {

    }

    public static Person of(Long parentId, Long accountId) {
        return new Person(parentId, accountId);
    }

    public Long getParentId() {
        return parentId;
    }

    public Person setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Person setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getTaxId() {
        return taxId;
    }

    public Person setTaxId(Long taxId) {
        this.taxId = taxId;
        return this;
    }

    public LocalDateTime getTaxExpirationDate() {
        return taxExpirationDate;
    }

    public Person setTaxExpirationDate(LocalDateTime taxExpirationDate) {
        this.taxExpirationDate = taxExpirationDate;
        return this;
    }
}
