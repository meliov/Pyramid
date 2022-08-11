package com.example.demo.bank.account;

import com.example.demo.bank.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Account extends BaseEntity {
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void incBalance(BigDecimal amount){
        balance =  balance.add(amount);
    }
    public void decBalance(BigDecimal amount){
       balance =  balance.subtract(amount);
    }
}
