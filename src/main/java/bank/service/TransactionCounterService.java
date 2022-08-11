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
        Long x;
        try {
            x = em.find(TransactionCount.class, TransactionCount.DEFAULT_ID, LockModeType.PESSIMISTIC_WRITE).incAndGet();
        }catch (Exception e){
            em.persist(new TransactionCount());
            x = 1L;
        }
        // izvinqvai bai ivan, ne mojahme da go opravim s sql file
        return x;
    }
}
