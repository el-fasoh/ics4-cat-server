package com.strathmore.ics3.cat.domain;

import com.fasterxml.jackson.databind.node.DoubleNode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_operations")
public class StudentOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private Operation operation;

    @ElementCollection
    @CollectionTable(name = "tracks")
    private List<Double> operands = new ArrayList<>();

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    public RequestStatus requestStatus;

    @Transient
    private Double result;

    public StudentOperation(Student student, Operation operation, List<Double> operands,RequestStatus requestStatus) {
        this.student = student;
        this.operation = operation;
        this.operands = operands;
        this.requestStatus = requestStatus;
    }

    public StudentOperation() {
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getResult() {
        if (operation == Operation.Add)
            result = Calculator.add(operands);
        else
            result = Calculator.multiply(operands);
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<Double> getOperands() {
        return operands;
    }

    public void setOperands(List<Double> operands) {
        this.operands = operands;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
