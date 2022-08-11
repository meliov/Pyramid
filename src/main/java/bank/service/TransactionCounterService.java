package bank.service;


import bank.model.TransactionCount;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Service
class TransactionCounterService {

    @PersistenceContext
    private EntityManager em;

    public Long nextVal() {
        return em.find(TransactionCount.class, TransactionCount.DEFAULT_ID, LockModeType.PESSIMISTIC_WRITE).incAndGet();
    }
}
