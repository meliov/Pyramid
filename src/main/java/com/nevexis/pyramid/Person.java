package com.nevexis.pyramid;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Person extends BaseEntity {
    private Long parentId;
    private Long accountId;
    private Long taxId;
    private LocalDateTime taxExpirationDate;


}
