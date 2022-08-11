package com.example.demo.bank;

import com.example.demo.bank.account.Account;

import com.example.demo.bank.transaction.TransactionGroupIdService;
import com.example.demo.bank.transaction.TransactionContext;
import com.example.demo.bank.transaction.Transaction;
import com.example.demo.bank.transaction.TransactionRepository;
import com.example.demo.bank.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BankServiceImpl implements BankService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ReentrantLock reentrantLock;
    @Autowired
    private TransactionRepository transactionRepository;



    @Override
    @Transactional
    public Long createAccount() {
        Account account = new Account();
        em.persist(account);
        return account.getId();
    }

    private void createTransaction(Long srcAccountId, Long dstAccountId, BigDecimal amount, TransactionType type, Long groupId) {

        Transaction transaction = new Transaction();

        Account srcAccount = em.find(Account.class, srcAccountId);
        transaction.setSrcAccount(srcAccount);
        transaction.setDstAccount(em.find(Account.class, dstAccountId));
        transaction.setAmount(amount);
        transaction.setOperationType(type);
        transaction.setGroupTransactionId(groupId);
        transaction.setDateOperation(LocalDateTime.now());
        em.persist(transaction);

        if (type == TransactionType.DEBIT) {
            srcAccount.decBalance(amount);
        }
        else if (type == TransactionType.CREDIT) {
            srcAccount.incBalance(amount);
        }


    }

    @Autowired
    TransactionGroupIdService transactionGroupIdService;

    @Override
    @Transactional
    public  Long transfer(TransactionContext... transParamObject) {
        reentrantLock.lock();

        try {
            Long transactionId = transactionGroupIdService.getNextVal();
            Stream.of(transParamObject).forEach(
                    t -> {
                        BigDecimal transactionAmount = t.getAmount();
                        createTransaction(t.getSrcAccountId(), t.getDstAccountId(), transactionAmount, TransactionType.DEBIT, transactionId);
                        createTransaction(t.getDstAccountId(), t.getSrcAccountId(), transactionAmount, TransactionType.CREDIT, transactionId);
                    }
            );
            return transactionId;
        }finally {
            reentrantLock.unlock();
        }


    }

    @Override
    @Transactional
    public void payTax(Long acctId, BigDecimal sum) {
        reentrantLock.lock();
        try {
            transfer(TransactionContext.of(getBankClearingAccountId(), acctId, sum));
        }finally {
            reentrantLock.unlock();
        }
    }

    @Override
    @Transactional
    public  void reverse(Long groupId) {
        reentrantLock.lock();
        try {
            List<TransactionContext> list = transactionRepository.findAll().stream()
                    .filter(t -> t.getGroupTransactionId().equals(groupId) && t.getOperationType().equals(TransactionType.CREDIT))
                    .map(e -> TransactionContext.of(e.getSrcAccount().getId(), e.getDstAccount().getId(), e.getAmount()))
                    .collect(Collectors.toList());
            transfer(list.toArray(TransactionContext[]::new));
        }finally {
            reentrantLock.unlock();
        }
    }


    private Long getBankClearingAccountId() {
        return 1L;
    }


}
