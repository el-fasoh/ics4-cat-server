package com.strathmore.ics3.cat.repository;

import com.strathmore.ics3.cat.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findById(Long id);

    Optional<Student> findByAdmissionNumber(Long admissionNumber);
}
