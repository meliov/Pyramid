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
import java.util.LinkedList;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
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
                .getSingleResult();
        if (tax == null) {
            throw new RuntimeException("Tax does not exist..");
        }
        Person person = em.find(Person.class, personId);
        person.setTaxId(taxId);
        person.setTaxExpirationDate(LocalDateTime.now().plusYears(1));
        List<TransactionContext> transfers =  calculate(personId,person.getParentId(), tax.getRequiredAmount(), tax.getRequiredAmount(),0, new LinkedList<>());
        bankService.transfer(transfers.toArray(TransactionContext[]::new));
    }

    public List<TransactionContext> calculate(Long srcAccount, Long dstAccount, BigDecimal sum, BigDecimal firmSum, Integer level, List<TransactionContext> transfers) {
        if (dstAccount == 1L) {
            transfers.add(TransactionContext.of(srcAccount, dstAccount, firmSum));
            return transfers;
        }
        Person parent = em.find(Person.class, dstAccount);
        Tax tax = em.find(Tax.class, parent.getTaxId());
        var bonus = calculateCurrentBonus(sum, level, tax);
        firmSum = firmSum.subtract(bonus);
        transfers.add(TransactionContext.of(srcAccount, parent.getId(), bonus));

        return calculate(srcAccount, parent.getParentId(), sum, firmSum, ++level, transfers);


    }

    private BigDecimal calculateCurrentBonus(BigDecimal sum, Integer level, Tax tax) {
        return sum.multiply(BigDecimal.valueOf(tax.getLevels().get(level) * 0.01));
    }

    //calculate method
}
