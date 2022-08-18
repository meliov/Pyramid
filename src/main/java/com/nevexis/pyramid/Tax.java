package com.nevexis.pyramid;

import com.nevexis.base.BaseEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(indexes = {@Index(columnList = "required_amount")})
@NamedQuery(name = Tax.QUERY_FIND_TAX_BY_ID, query = "select t from Tax t where t.id = :id")

public class Tax extends BaseEntity {
    public static final String QUERY_FIND_TAX_BY_ID = "QUERY_FIND_TAX_BY_ID";

    private Integer tax;
    @Column(name = "required_amount")
    private BigDecimal requiredAmount;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> levels;

    public Integer getTax() {
        return tax;
    }

    public Tax setTax(Integer tax) {
        this.tax = tax;
        return this;
    }

    public BigDecimal getRequiredAmount() {
        return requiredAmount;
    }

    public Tax setRequiredAmount(BigDecimal requiredAmount) {
        this.requiredAmount = requiredAmount;
        return this;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public Tax setLevels(List<Integer> levels) {
        this.levels = levels;
        return this;
    }

}
