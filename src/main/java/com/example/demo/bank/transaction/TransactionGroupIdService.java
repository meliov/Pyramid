package com.example.demo.bank.transaction;


import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;

@Service
public class TransactionGroupIdService {

    @PersistenceContext
    private EntityManager em;

    public Long getNextVal() {
        Long x;
        try {
            x = em.find(TransactionGroupId.class, TransactionGroupId.DEFAULT_ID, LockModeType.PESSIMISTIC_WRITE).incAndGet();
        }catch (Exception e){
            em.persist(new TransactionGroupId());
            x = em.find(TransactionGroupId.class, TransactionGroupId.DEFAULT_ID, LockModeType.PESSIMISTIC_WRITE).incAndGet();
        }
         return x;
    }
}
