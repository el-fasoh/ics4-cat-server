package com.strathmore.ics3.cat.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    @NotNull(groups = Create.class)
    private String fullName;

    @Column(name = "admission_number",unique = true)
    @NotNull(groups = Create.class)
    private Long admissionNumber;

    public Student( String fullName, Long admissionNumber) {
        this.fullName = fullName;
        this.admissionNumber = admissionNumber;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(Long admissionNumber) {
        this.admissionNumber = admissionNumber;
    }

    public interface Create {
    }
}
