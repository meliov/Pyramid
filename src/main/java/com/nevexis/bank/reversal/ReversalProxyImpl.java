package com.nevexis.bank.reversal;

import com.nevexis.bank.base.BankService;
import com.nevexis.bank.base.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Component("reversalBankService")
class ReversalProxyImpl implements BankService {

   @PersistenceContext
   private EntityManager em;

    @Autowired
    @Qualifier("bankService")
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
    @Transactional
    public Long reverse(Long groupId) {

        if (!em.createNamedQuery(TransactionReversal.QUERY_FIND_TRANSACTIONS_REVERSED_ID, TransactionReversal.class)
                .setParameter("reversedId", groupId).setMaxResults(1).getResultList().isEmpty()) {
            throw new IllegalArgumentException("transaction already reversed!");
        }

        if (!em.createNamedQuery(TransactionReversal.QUERY_FIND_TRANSACTIONS_REVERSAL_ID, TransactionReversal.class)
                .setParameter("reversalId", groupId).setMaxResults(1).getResultList().isEmpty()) {
            throw new IllegalArgumentException("transaction is a reversal of other transaction");
        }

        TransactionReversal tr = new TransactionReversal();
        tr.setReversedId(groupId);
        tr.setReversalId(bankService.reverse(groupId));
        em.persist(tr);

        return tr.getReversalId();

    }

}
