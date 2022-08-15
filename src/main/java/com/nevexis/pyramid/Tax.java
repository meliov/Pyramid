package com.nevexis.pyramid;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tax extends BaseEntity {

    private LocalDateTime validDate = LocalDateTime.now().plusYears(1);

    @ElementCollection
    private List<Integer> courses = new ArrayList<>() ;


    public Tax() {
    }



    public LocalDateTime getValidDate() {
        return validDate;
    }

    public Tax setValidDate(LocalDateTime validDate) {
        this.validDate = validDate;
        return this;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public Tax setCourses(List<Integer> courses) {
        this.courses = courses;
        return this;
    }
}
