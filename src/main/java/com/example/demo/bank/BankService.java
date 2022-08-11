package com.example.demo.bank;

import com.example.demo.bank.account.Account;
import com.example.demo.bank.transaction.TransactionContext;

import java.math.BigDecimal;

public interface BankService {
    Long createAccount();

    Long transfer(TransactionContext... transParamObject);

    void reverse(Long groupId);

    void payTax(Long acctId, BigDecimal sum);

}
