package com.nevexis.bank.base;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TransactionContext {
    private final BigDecimal amount;
    private final Long srcAccountId;
    private final Long dstAccountId;
    private Map<String, String > props = new HashMap<>();

    public static TransactionContext of(Long src, Long dst, BigDecimal amount, Map<String, String> props) {
        return new TransactionContext(src, dst, amount, props);
    }
    public static TransactionContext of(Long src, Long dst, BigDecimal amount) {
        return new TransactionContext(src, dst, amount);
    }
    public static TransactionContext of(Transaction e) {
        return TransactionContext.of(e.getSrcAccount().getId(), e.getDstAccount().getId(), e.getAmount());
    }

    private TransactionContext(Long src, Long dst, BigDecimal amount) {
        this.amount = amount;
        this.srcAccountId = src;
        this.dstAccountId = dst;
    }

    public TransactionContext( Long srcAccountId, Long dstAccountId,BigDecimal amount, Map<String, String> props) {
        this.amount = amount;
        this.srcAccountId = srcAccountId;
        this.dstAccountId = dstAccountId;
        this.props = props;
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




    public String get(Object key) {
        return props.get(key);
    }

    public String put(String key, String value) {
        return props.put(key, value);
    }

    public Map<String, String> getProps() {
        return props;
    }
}

