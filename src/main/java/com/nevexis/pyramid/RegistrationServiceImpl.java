package com.nevexis.pyramid;

import com.nevexis.bank.base.BankService;
import com.nevexis.bank.base.TransactionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    public static final long COMPANY_ACCOUNT_ID = 2L;
    public static final long BANK_ACCOUNT_ID = 1L;
    public static final BigDecimal PERCENT_CONSTANT = new BigDecimal("0.01");
    public static final int DEFAULT_REGISTRATION_LEVEL = 0;
    public static final int DEFAULT_FIRM_PERCENT = 100;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    @Qualifier("lockedBankService")
    private BankService bankService;

    @Override
    @Transactional
    public Person register(Long parentId) {
        Person person = Person.of(1L, 1L);
        em.persist(person);
        return person;
    }

    @Override
    @Transactional
    public void payTax(Long personId, Long taxId) {
        Tax tax = em.createNamedQuery(Tax.QUERY_FIND_TAX_BY_ID, Tax.class)
                .setParameter("id", taxId)
                        .getResultStream()
                                .findFirst().orElseThrow(() -> {
                    throw new RuntimeException("Tax does not exist..");
                });


        Person person = em.find(Person.class, personId);
        person.setTaxId(taxId);
        person.setTaxExpirationDate(LocalDateTime.now().plusYears(1));

        var tr = calculate(person, tax.getRequiredAmount());
        tr.add(TransactionContext.of(BANK_ACCOUNT_ID,person.getAccountId(),tax.getRequiredAmount()));
        System.out.println();
        bankService.transfer(tr.toArray(TransactionContext[]::new));

    }

    private List<TransactionContext> calculate(Person person, BigDecimal sum) {
        return getCalculateContextsRecursively(findPersonParent(person), new LinkedList<>(), DEFAULT_FIRM_PERCENT, DEFAULT_REGISTRATION_LEVEL)
                .parallelStream()
                .map(t -> TransactionContext.of(person.getAccountId(), t.getAccountId(), calculateCurrentSum(sum, t).setScale(2)))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateCurrentSum(BigDecimal sum, CalculateContext t) {
        return sum.multiply(BigDecimal.valueOf(t.getPercentage()).multiply(PERCENT_CONSTANT));
    }

    private List<CalculateContext> getCalculateContextsRecursively(Person parent, List<CalculateContext> calculateContexts, Integer firmPercent, Integer level) {
        if (parent.getId() == COMPANY_ACCOUNT_ID) {
            calculateContexts.add(CalculateContext.of(parent.getAccountId(), firmPercent));
            return calculateContexts;
        }
        calculateContexts.add(CalculateContext.of(parent.getAccountId(), getCurrentPercent(parent, level)));
        return getCalculateContextsRecursively(findPersonParent(parent), calculateContexts, firmPercent - getCurrentPercent(parent, level), ++level);
    }

    private Integer getCurrentPercent(Person parent, Integer level) {
        return em.find(Tax.class, parent.getTaxId()).getLevels().get(level);
    }

    private Person findPersonParent(Person person) {
        return em.find(Person.class, person.getParentId());
    }


    //calculate method
}
