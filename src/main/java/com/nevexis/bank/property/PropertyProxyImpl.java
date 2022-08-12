package com.nevexis.bank.property;

import com.nevexis.bank.base.BankService;
import com.nevexis.bank.base.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 *
 */
@Component("propertyBankService")
class PropertyProxyImpl implements BankService {

    @Autowired
    @Qualifier("lockedBankService")
    private BankService bankService;

    @Override
    public Long createAccount() {
        return bankService.createAccount();
    }

    @Override
    public Long transfer(TransactionContext... transParamObject) {
        return bankService.transfer(transParamObject);
    }

    @Override
    public Long reverse(Long groupId) {
        return bankService.reverse(groupId);
    }
}
