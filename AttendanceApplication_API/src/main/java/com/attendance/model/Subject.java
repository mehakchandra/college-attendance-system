package com.attendance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    private Integer subjectId;
	private String name;
    private Integer semester;
    private Integer departmentId;
    
    public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Subject(Integer subjectId, String name, Integer semester, Integer departmentId) {
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.semester = semester;
		this.departmentId = departmentId;
	}
	public Subject() {
		super();
	}
	
}
