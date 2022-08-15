package com.nevexis.pyramid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration
public class RegistrationServiceTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TaxService taxService;

    @Test

    public void taxTest(){
        int x = 10;
        for (int i = 0; i < 10; i++) {
            Tax tax = new Tax();
            List<Integer> list = new ArrayList<>();
            int m = x;
            for (int j = 0;j < 10; j++) {
                list.add(m);
                m--;
            }
            x++;
            tax.setCourses(list);
            taxService.save(tax);
        }

        //entityManager.persist(tax);

    }
}
