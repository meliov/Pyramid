package bank.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
//@Table(indexes = {@Index(columnList = "reversed_id"), @Index(columnList = "reversal_id")})
@NamedQueries(value = {@NamedQuery(name = Transaction.QUERY_FIND_TRANSACTIONS_FOR_REVERSAL, query = "select t from Transaction t where t.groupTransactionId = :groupTransactionId and t.operationType = :operationType")})
public class TransactionReversal extends _BaseEntity {

    @Column(unique = true, nullable = false)
    private Long reversedId;

    @Column(unique = true, nullable = false)
    private Long reversalId;

    public void setReversedId(Long reversedId) {
        this.reversedId = reversedId;
    }

    public Long getReversalId() {
        return reversalId;
    }

    public void setReversalId(Long reversalId) {
        this.reversalId = reversalId;
    }
}
