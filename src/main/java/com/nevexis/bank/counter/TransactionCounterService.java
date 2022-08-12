package com.nevexis.bank.counter;


import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Service
public class TransactionCounterService {

    @PersistenceContext
    private EntityManager em;

    public Long nextVal() {
       return em.find(TransactionCounter.class, TransactionCounter.DEFAULT_ID, LockModeType.PESSIMISTIC_WRITE).nextVal();
    }
}
