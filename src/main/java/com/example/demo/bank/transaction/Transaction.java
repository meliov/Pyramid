package com.example.demo.bank.transaction;

import com.example.demo.bank.account.Account;
import com.example.demo.bank.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(columnList = "src_account_id, dst_account_id"), @Index( columnList = "group_transaction_id")})

public class Transaction extends BaseEntity {
    @OneToOne
    private Account srcAccount;

    @OneToOne
    private Account dstAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime dateOperation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType operationType;

    @Column(nullable = false, name = "group_transaction_id" )
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
