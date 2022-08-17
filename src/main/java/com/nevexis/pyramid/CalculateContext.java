package com.nevexis.pyramid;

public class CalculateContext {
    private Long accountId;
    private Integer percentage;

    private CalculateContext(Long accountId, Integer percentage) {
        this.accountId = accountId;
        this.percentage = percentage;
    }
    public static CalculateContext of(Long accountId, Integer percentage){
        return new CalculateContext(accountId,percentage);
    }

    public Long getAccountId() {
        return accountId;
    }

    public Integer getPercentage() {
        return percentage;
    }
}
