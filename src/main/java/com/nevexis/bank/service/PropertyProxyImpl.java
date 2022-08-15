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
import java.util.List;
import java.util.Map;

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
    public Long transfer(TransactionContext... transParamObject) {
        var grId =  bankService.transfer(transParamObject);
        // 1 -> 2

        for (Map.Entry<String,String> entry : transParamObject[0].getProps().entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        transactionIdsForProperties.forEach(
                t ->{
                    TransactionProperty transactionProperty = new TransactionProperty();
                    transactionProperty.setTransactionId(t);

                    transactionProperty.setPropertyName(transParamObject[0].getProps() + " " + t );
                    transactionProperty.setPropertyValue("Value" + " " + t );
                    em.persist(transactionProperty);
                    transactionProperty.setPropertyName("Name" + " " + t );

                }
        );
        return grId;

    }
    @Override
    public Long reverse(Long groupId) {
        return bankService.reverse(groupId);
    }
}
