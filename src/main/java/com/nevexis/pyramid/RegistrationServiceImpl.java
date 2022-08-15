package com.nevexis.pyramid;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public Person register(Long id) {
//        em.find()
        return null;
    }

    @Override
    public void payTax(Long personId, BigDecimal sum) {

    }
    //calculate method
}
