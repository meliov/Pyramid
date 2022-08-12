package com.nevexis.bank.base;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(columnList = "group_transaction_id, operation_type")})
@NamedQuery(name = Transaction.QUERY_FIND_TRANSACTIONS_FOR_REVERSAL, query = "select t from Transaction t where t.groupTransactionId = :groupTransactionId and t.operationType = :operationType")
public class Transaction extends _BaseEntity {

    public static final String QUERY_FIND_TRANSACTIONS_FOR_REVERSAL = "QUERY_FIND_TRANSACTIONS_FOR_REVERSAL";

    @OneToOne
    private Account srcAccount;

    @OneToOne
    private Account dstAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime dateOperation;

    @Column(nullable = false, name = "operation_type")
    @Enumerated(EnumType.STRING)
    private TransactionType operationType;

    @Column(nullable = false, name = "group_transaction_id")
    private Long groupTransactionId;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setSrcAccount(Account srcAccount) {
        this.srcAccount = srcAccount;
    }

    public void setDstAccount(Account dstAccount) {
        this.dstAccount = dstAccount;
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }

    public void setOperationType(TransactionType operationType) {
        this.operationType = operationType;
    }

    public void setGroupTransactionId(Long groupTransactionId) {
        this.groupTransactionId = groupTransactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getSrcAccount() {
        return srcAccount;
    }

    public Account getDstAccount() {
        return dstAccount;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    public TransactionType getOperationType() {
        return operationType;
    }

    public Long getGroupTransactionId() {
        return groupTransactionId;
    }
}
