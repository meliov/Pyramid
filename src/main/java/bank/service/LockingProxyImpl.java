package bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Component("lockedBankService")
public class LockingProxyImpl implements BankService {

    @Autowired
    @Qualifier("bankService")
    private BankService bankService;

    private ReentrantLock transactionLock = new ReentrantLock();

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
        return doLock(() -> {
            return bankService.transfer(transParamObject);
        });
    }

    @Override
    public Long reverse(Long groupId) {
        return bankService.reverse(groupId);
    }
}
