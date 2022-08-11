package com.example.demo.bank.transaction;

import com.example.demo.bank.account.Account;

import java.math.BigDecimal;

public class TransactionContext {
    private final BigDecimal amount;
    private final Long srcAccountId;
    private final Long dstAccountId;

    public static TransactionContext of(Long src, Long dst, BigDecimal amount) {
        return new TransactionContext(src, dst, amount);
    }

    private TransactionContext(Long src, Long dst, BigDecimal amount) {
        this.amount = amount;
        this.srcAccountId = src;
        this.dstAccountId = dst;
    }

    public BigDecimal getAmount() {
        return amount;
    }



    public Long getSrcAccountId (){
        return srcAccountId;
    }
    public Long getDstAccountId (){
        return dstAccountId;
    }
}

