package bank.service;

import java.math.BigDecimal;

public interface BankService {
    Long createAccount();

    Long transfer(TransactionContext... transParamObject);

    Long reverse(Long groupId);

}
