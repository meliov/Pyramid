package bank.service;

import bank.model.TransactionReversal;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Component("reversalBankService")
public class ReversalProxyImpl implements BankService {

   @PersistenceContext
   EntityManager em;

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
                .setParameter("reversedId", groupId).getResultList().isEmpty()) {
            throw new IllegalArgumentException("transaction already reversed!");
        }

        if (!em.createNamedQuery(TransactionReversal.QUERY_FIND_TRANSACTIONS_REVERSAL_ID, TransactionReversal.class)
                .setParameter("reversalId", groupId).getResultList().isEmpty()) {
            throw new IllegalArgumentException("transaction is a reversal of other transaction");
        }

        TransactionReversal tr = new TransactionReversal();
        tr.setReversedId(groupId);
        tr.setReversalId(bankService.reverse(groupId));
        em.persist(tr);

        return tr.getReversalId();

    }

}
