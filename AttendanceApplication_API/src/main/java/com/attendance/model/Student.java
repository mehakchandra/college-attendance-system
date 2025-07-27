package com.attendance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    private Integer studentId;
	private String name;
    private Integer departmentId;
    private Integer semester;
    
    public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public Student(Integer studentId, String name, Integer departmentId, Integer semester) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.departmentId = departmentId;
		this.semester = semester;
	}
	public Student() {
		super();
	}
}
