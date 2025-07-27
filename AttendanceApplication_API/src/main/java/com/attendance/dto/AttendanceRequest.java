package com.attendance.dto;

import java.util.Date;

public class AttendanceRequest {
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
	public Boolean getPresent() {
		return present;
	}
	public void setPresent(Boolean present) {
		this.present = present;
	}
	public AttendanceRequest(Integer studentId, Integer subjectId, Date date, Boolean present) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.date = date;
		this.present = present;
	}
	public AttendanceRequest() {
		super();
	}
}
