package com.strathmore.ics3.cat.controller;

import com.strathmore.ics3.cat.domain.*;
import com.strathmore.ics3.cat.exceptions.BadRequestException;
import com.strathmore.ics3.cat.exceptions.ResourceNotFoundException;
import com.strathmore.ics3.cat.repository.StudentOperationRepository;
import com.strathmore.ics3.cat.repository.StudentRepository;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "students", produces = {MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE})
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentOperationRepository studentOperationRepository;


    public StudentController(StudentRepository studentRepository, StudentOperationRepository studentOperationRepository) {
        this.studentRepository = studentRepository;
        this.studentOperationRepository = studentOperationRepository;
    }

    @PostMapping
    public Student createStudent(@RequestBody @Validated(Student.Create.class) Student student) {
        return studentRepository.save(student);
    }

    @GetMapping(value = "{id}")
    public Student findStudentById(@PathVariable Long id) {
        return this.validate(id);
    }

    @PostMapping(value = "{studentId}/calculate")
    public Double calculate(@RequestBody List<Double> operands, @RequestParam Operation operation, @PathVariable Long studentId) {
        Student student= this.validate(studentId);
        if (operands.size()<2)
            throw new BadRequestException("Minimum operands is 2");
        if(studentOperationRepository.findByStudentId(studentId).isPresent())
            throw new BadRequestException("You already have an existing operation");
        if (operation == Operation.Multiply) {
            studentOperationRepository.save(new StudentOperation(student,operation,operands, RequestStatus.Received));
            return Calculator.multiply(operands);
        }
        else {
            studentOperationRepository.save(new StudentOperation(student,operation,operands,RequestStatus.Received));
            return Calculator.add(operands);
        }
    }

    @PostMapping(value = "{studentId}/validate")
    public void validateResult(@RequestBody List<Double> operands, @RequestParam Double result, @PathVariable Long studentId){
        this.validate(studentId);
        if (operands.size()<2)
            throw new BadRequestException("Minimum operands is 2");
        if(!studentOperationRepository.findByStudentId(studentId).isPresent())
            throw new BadRequestException("You do not have an existing operation");
        StudentOperation studentOperation=studentOperationRepository.findByStudentId(studentId).orElseThrow(()-> new ResourceNotFoundException("You do not have any operations created"));
        if(Objects.equals(result, studentOperation.getResult())){
            studentOperation.setRequestStatus(RequestStatus.Validated);
            studentOperationRepository.save(studentOperation);
        }else{
            studentOperation.setRequestStatus(RequestStatus.Missmatch);
            studentOperationRepository.save(studentOperation);
            throw new BadRequestException("You seem to be cooking stuff try validate again. The same inputs you started with.");
        }

    }

    @GetMapping(value = "{studentId}/operations")
    public StudentOperation findByStudentId(@PathVariable Long studentId){
        return studentOperationRepository.findByStudentId(studentId).orElseThrow(()->new ResourceNotFoundException("No operations associated with this id."));
    }

    @GetMapping(value = "search")
    public Student findByAdmissionNumber(@RequestParam Long admissionNumber){
        return studentRepository.findByAdmissionNumber(admissionNumber).orElseThrow(()->new  ResourceNotFoundException("Not found"));
    }


    Student validate(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

}
