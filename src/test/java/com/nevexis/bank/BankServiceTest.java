package com.nevexis.bank;



import com.nevexis.bank.base.BankService;
import com.nevexis.pyramid.Tax;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration
public class BankServiceTest {
    @Autowired
    @Qualifier("propertyBankService")
    private BankService bankService;
    @PersistenceContext
    private EntityManager entityManager;
    @Test(expected = RuntimeException.class)
    public void createAccountTest(){
        System.out.println();
        bankService.createAccount();
        bankService.createAccount();
        bankService.createAccount();
    }

    @Test
    @Transactional
    public void multiTransferTest(){
        //bankService.transfer(TransactionContext.of( 1L, 2L, BigDecimal.valueOf(50)), TransactionContext.of(1L, 3L, BigDecimal.valueOf(50) ));
//        var x = TransactionContext.of(1L, 2L, BigDecimal.valueOf(50), new HashMap<>());
//        var y = TransactionContext.of(1L, 2L, BigDecimal.valueOf(50), new HashMap<>());
//        x.put("Name", "Value");
//        y.put("Name", "Value");
//       bankService.transfer(x, y);
        Tax tax = new Tax();
        entityManager.persist(tax);
    }
    @Test
    public void reverseTest(){
        bankService.reverse(7L);
    }


}
