package com.nevexis.bank.reversal;

import com.nevexis.bank.base._BaseEntity;

import javax.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "reversed_id"), @Index(columnList = "reversal_id")})
@NamedQueries(value = {
        @NamedQuery(name =TransactionReversal.QUERY_FIND_TRANSACTIONS_REVERSED_ID, query = "select tr from TransactionReversal tr where tr.reversedId = :reversedId"),
        @NamedQuery(name = TransactionReversal.QUERY_FIND_TRANSACTIONS_REVERSAL_ID, query = "select tr from TransactionReversal tr where tr.reversalId = :reversalId")
})

public class TransactionReversal extends _BaseEntity {
    public static final String QUERY_FIND_TRANSACTIONS_REVERSAL_ID = "QUERY_FIND_TRANSACTIONS_REVERSAL_ID";
    public static final String QUERY_FIND_TRANSACTIONS_REVERSED_ID = "QUERY_FIND_TRANSACTIONS_REVERSED_ID";
    @Column(unique = true, nullable = false, name = "reversed_id")
    private Long reversedId = 0l;

    @Column(unique = true, nullable = false, name = "reversal_id")
    private Long reversalId = 0l;

    public void setReversedId(Long reversedId) {
        this.reversedId = reversedId;
    }

    public Long getReversalId() {
        return reversalId;
    }

    public void setReversalId(Long reversalId) {
        this.reversalId = reversalId;
    }
}
