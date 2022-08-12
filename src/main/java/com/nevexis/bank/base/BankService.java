package com.nevexis.bank.base;

public interface BankService {
    Long createAccount();

    Long transfer(TransactionContext... transParamObject);

    Long reverse(Long groupId);

}
