package bank.service;

import bank.model.Account;
import bank.model.Transaction;
import bank.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static final long BANK_CLEARING_ACCOUNT_ID = 1L;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionCounterService transactionGroupIdService;


    @Override
    @Transactional
    public Long createAccount() {
        Account account = new Account();
        em.persist(account);
        return account.getId();
    }

    //! no lock (locked outside)
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
        } else {
            srcAccount.incBalance(amount);
        }
    }

    @Override
    @Transactional
    public Long transfer(TransactionContext... transactions) {
        final Long transactionGroupId = transactionGroupIdService.nextVal();

        Stream.of(transactions).forEach(
                t -> {
                    BigDecimal transactionAmount = t.getAmount();
                    createTransaction(t.getSrcAccountId(), t.getDstAccountId(), transactionAmount, TransactionType.DEBIT, transactionGroupId);
                    createTransaction(t.getDstAccountId(), t.getSrcAccountId(), transactionAmount, TransactionType.CREDIT, transactionGroupId);
                }
        );
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
                        .getResultList();

        return transfer(transactions.stream()
                .map(TransactionContext::of)
                .toArray(TransactionContext[]::new));
    }
}
