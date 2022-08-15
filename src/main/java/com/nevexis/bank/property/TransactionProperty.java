package com.nevexis.bank.property;

import com.nevexis.bank.base._BaseEntity;
import com.nevexis.bank.reversal.TransactionReversal;

import javax.persistence.*;

@Entity
public class TransactionProperty extends _BaseEntity {

    @Column(nullable = false)
    private Long transactionId = 0l;

    @Column(nullable = false)
    private String propertyName;

    @Column(nullable = false)
    private String propertyValue;

    public TransactionProperty setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionProperty setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public TransactionProperty setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }
}
