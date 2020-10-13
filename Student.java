package com.lab2.students.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "Name cannot be empty/null")
    private String name;
    @NotNull(message = "_class cannot be null")
    @Min(1)
    private Integer _class;
    private boolean active;
    @NotNull(message = "admissionYear cannot be null")
    @Min(0)
    private Integer admissionYear;

    public Student() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name",
            nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "_class",
            nullable = false)
    public int get_class() {
        return _class;
    }

    public void set_class(int _class) {
        this._class = _class;
    }

    @Column(name = "is_active",
            nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "admission_year",
            nullable = false)
    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }
}
