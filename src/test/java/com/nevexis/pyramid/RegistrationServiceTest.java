package com.nevexis.pyramid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration
public class RegistrationServiceTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TaxService taxService;

    @Autowired
    private RegistrationServiceImpl registrationService;
    @Test

    public void populateTaxesTest(){
        int x = 10;
        for (int i = 1, j = 10; i < 11; i++, j--) {
            Tax tax = new Tax();
            List<Integer> list= List.of(10,9,8,7,6,5,4,3,2,1);
           tax.setTax(i);
           tax.setRequiredAmount(BigDecimal.valueOf(j* 10L));
           tax.setLevels(list);
            taxService.save(tax);
            System.out.println(tax);
        }

        //entityManager.persist(tax);

    }
    @Test
    public void payTaxTest(){
        registrationService.payTax(6L,1L);
    }
}
