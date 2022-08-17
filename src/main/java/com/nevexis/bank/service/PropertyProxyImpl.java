package com.nevexis.bank.service;

import com.nevexis.bank.base.BankService;
import com.nevexis.bank.base.TransactionContext;
import com.nevexis.bank.property.TransactionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

/**
 *
 */
@Component("propertyBankService")
class PropertyProxyImpl extends BankServiceImpl {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    @Qualifier("lockedBankService")
    private BankService bankService;

    @Override
    public Long createAccount() {
        return bankService.createAccount();
    }

    @Override
    @Transactional
    public Long transfer(TransactionContext... transParamObject) {//6
        var grId = bankService.transfer(transParamObject);
        int x = 0;
        for (TransactionContext transactionContext : transParamObject) {
            for (int j = x; j < transactionIdsForProperties.size(); j++, x++) {
                int finalJ = j;
                transactionContext.getProps().forEach((k, v) -> {
                    TransactionProperty transactionProperty = new TransactionProperty();
                    transactionProperty.setTransactionId(transactionIdsForProperties.get(finalJ));
                    transactionProperty.setPropertyName(k);
                    transactionProperty.setPropertyValue(v);
                    em.persist(transactionProperty);
                });
                if (x % 2 != 0) {
                    x++;
                    break;
                }
            }
        }
        return grId;
    }

    @Override
    public Long reverse(Long groupId) {
        return bankService.reverse(groupId);
    }
}
