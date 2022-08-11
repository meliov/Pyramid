package bank.model;

import javax.persistence.Entity;

@Entity
public class TransactionCount extends _BaseEntity {

    public final static Long DEFAULT_ID = 1L;

    private Long nextVal;

    public Long incAndGet(){
        return ++nextVal;
    }

}
