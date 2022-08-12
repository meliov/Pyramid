package com.nevexis.bank.service;

import com.nevexis.bank.base.*;
import com.nevexis.bank.counter.TransactionCounterService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Component("bankService")
class BankServiceImpl implements BankService {

    @Value("${transactions.for.reversal.limit}")
    private int transactionsforReversalLimit = 100;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionCounterService transactionCounterService;


    @Override
    @Transactional
    public Long createAccount() {
        Account account = new Account();
        em.persist(account);
        return account.getId();
    }

    private void createTransaction(Account srcAccount, Account dstAccount, BigDecimal amount, TransactionType type, Long groupId) {
        Transaction transaction = new Transaction();
        transaction.setSrcAccount(srcAccount);
        transaction.setDstAccount(dstAccount);
        transaction.setAmount(amount);
        transaction.setOperationType(type);
        transaction.setGroupTransactionId(groupId);
        transaction.setDateOperation(LocalDateTime.now());
        em.persist(transaction);
    }

    @Override
    @Transactional
    public Long transfer(TransactionContext... transactions) {
        final Long transactionGroupId = transactionCounterService.nextVal();
        Stream.of(transactions).forEach(
                t -> {
                    BigDecimal transactionAmount = t.getAmount();
                    Account srcAccount = em.find(Account.class, t.getSrcAccountId());
                    Account dstAccount = em.find(Account.class, t.getDstAccountId());
                    createTransaction(srcAccount, dstAccount, transactionAmount, TransactionType.DEBIT, transactionGroupId);
                    createTransaction(dstAccount, srcAccount, transactionAmount, TransactionType.CREDIT, transactionGroupId);
                    srcAccount.decBalance(transactionAmount);
                    dstAccount.incBalance(transactionAmount);
                });
        return transactionGroupId;
    }

    @Override
    @Transactional
    public Long reverse(Long groupId) {
        List<Transaction> transactions =
                em.createNamedQuery(Transaction.QUERY_FIND_TRANSACTIONS_FOR_REVERSAL, Transaction.class)
                        .setParameter("groupTransactionId", groupId)
                        .setParameter("operationType", TransactionType.CREDIT)
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .setMaxResults(transactionsforReversalLimit)
                        .getResultList();

        return transfer(transactions.stream()
                .map(TransactionContext::of)
                .toArray(TransactionContext[]::new));
    }
}