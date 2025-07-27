package com.attendance.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attendance")
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer studentId;
    private Integer subjectId;
    private Date date;
    private Boolean present;
    
    public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getpresent() {
		return present;
	}
	public void setPresent(Boolean present) {
		this.present = present;
	}
	public AttendanceRecord(Long id, Integer studentId, Integer subjectId, Date date, Boolean present) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.date = date;
		this.present = present;
	}
	public AttendanceRecord() {
		super();
	}
	
}
