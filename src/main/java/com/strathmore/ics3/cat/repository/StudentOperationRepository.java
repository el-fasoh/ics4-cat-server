package com.strathmore.ics3.cat.repository;

import com.strathmore.ics3.cat.domain.Operation;
import com.strathmore.ics3.cat.domain.StudentOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentOperationRepository extends JpaRepository<StudentOperation,Long>{
    Optional<StudentRepository> findByStudentIdAndOperation(Long studentId, Operation operation);

    Optional<StudentOperation>findByStudentId(Long studentId);
}
