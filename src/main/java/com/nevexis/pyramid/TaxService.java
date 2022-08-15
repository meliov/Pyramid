package com.nevexis.pyramid;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class TaxService {
@PersistenceContext
private EntityManager em;
@Transactional
    public void save(Tax tax){
        em.persist(tax);
    }
}
