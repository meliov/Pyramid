package com.nevexis.pyramid;

import java.math.BigDecimal;

public interface RegistrationService {
    Person register(Long id);
    void payTax(Long personId, BigDecimal sum);
}
