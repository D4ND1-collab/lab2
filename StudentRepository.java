package com.lab2.students.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lab2.students.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
