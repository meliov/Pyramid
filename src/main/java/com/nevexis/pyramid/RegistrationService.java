package com.nevexis.pyramid;

public interface RegistrationService {
    Person register(Long id);
    void payTax(Long personId, Long taxId);
}
