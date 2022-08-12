package com.nevexis.bank.lock;

import com.nevexis.bank.base.BankService;
import com.nevexis.bank.base.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Component("lockedBankService")
/**
 * delegates to reversalBankService which delegates to bankService, chaining 3 of them
 */
class LockingProxyImpl implements BankService {

    @Autowired
    @Qualifier("reversalBankService")
    private BankService bankService;

    private final ReentrantLock transactionLock = new ReentrantLock();

    private <T> T doLock(Supplier<T> s) {
        try {
            transactionLock.lock();
            return s.get();
        } finally {
            transactionLock.unlock();
        }
    }


    @Override
    public Long createAccount() {
        return bankService.createAccount();
    }

    @Override
    public Long transfer(TransactionContext... transParamObject) {
        return doLock(() -> bankService.transfer(transParamObject));
    }

    @Override
    public Long reverse(Long groupId) {
        return doLock(() -> bankService.reverse(groupId));
    }
}
