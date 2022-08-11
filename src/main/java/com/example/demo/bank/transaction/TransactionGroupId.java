package com.example.demo.bank.transaction;

import com.example.demo.bank.BaseEntity;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TransactionGroupId extends BaseEntity {

    final static Long DEFAULT_ID = 1L;
    private Long nextVal = 0L;

    public Long incAndGet(){
        return ++nextVal;
    }

}
