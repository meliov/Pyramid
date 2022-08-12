package com.nevexis.bank.counter;

import com.nevexis.bank.base._BaseEntity;

import javax.persistence.Entity;

@Entity
public class TransactionCounter extends _BaseEntity {

    public final static Long DEFAULT_ID = 1L;

    private Long nextVal = 1L;

    public Long nextVal(){
        return ++nextVal;
    }

}
