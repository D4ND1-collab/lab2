package com.lab2.students.controller;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;

import com.lab2.students.exception.ResourceNotFoundException;
import com.lab2.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lab2.students.model.Student;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final String ID_PLACEHOLDER = "/{id}";
    private static final String ID = "id";

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping()
    public List<Student> getFilteredStudents(@RequestParam(name = "classes", required = false) List<Integer> classes
            , @RequestParam(name = "active", required = false) Boolean isActive
            , @RequestParam(name = "admissionYearAfter", required = false) Integer admissionYearAfter
            , @RequestParam(name = "admissionYearBefore", required = false) Integer admissionYearBefore
            , @RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber
            , @RequestParam(name = "pageSize", defaultValue = "20", required = false) Integer pageSize) {

        Page page = studentRepository.findAll(
                (Specification<Student>) (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (classes != null) {
                        predicates.add(root.get("_class").in(classes));
                    }
                    if (isActive != null) {
                        predicates.add(criteriaBuilder.equal(root.get("active"), isActive));
                    }
                    if (admissionYearAfter != null) {
                        predicates.add(criteriaBuilder.ge(root.get("admissionYear"), admissionYearAfter));
                    }
                    if (admissionYearBefore != null) {
                        predicates.add(criteriaBuilder.le(root.get("admissionYear"), admissionYearBefore));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }, new PageRequest(pageNumber, pageSize)
        );

        return page.getContent();
    }

    @GetMapping(ID_PLACEHOLDER)
    public ResponseEntity<Student> getStudentById(
            @PathVariable(value = ID)
                    Long studentId) throws ResourceNotFoundException {

        Student student = findStudentById(studentId);

        return ResponseEntity.ok().body(student);

    }

    @PostMapping()
    public ResponseEntity createStudent(@Valid
                                        @RequestBody
                                                Student student) {
        student.getId();
        student.setActive(true);
        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PatchMapping(ID_PLACEHOLDER)
    public ResponseEntity<Student> updateStudent(
            @PathVariable(value = ID)
                    Long studentId, @Valid
            @RequestBody
                    Student studentDetails) throws ResourceNotFoundException {

        Student student = findStudentById(studentId);

        student.set_class(studentDetails.get_class());
        return ResponseEntity.ok(studentRepository.save(student));

    }

    @DeleteMapping(ID_PLACEHOLDER)
    public Student deleteStudent(
            @PathVariable(value = ID)
                    Long studentId) throws ResourceNotFoundException {

        Student student = findStudentById(studentId);

        student.setActive(false);
        return studentRepository.save(student);
    }

    private Student findStudentById(Long studentId) throws ResourceNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
    }

}
