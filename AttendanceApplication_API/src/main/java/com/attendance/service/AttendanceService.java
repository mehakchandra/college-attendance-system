package com.attendance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.dto.AttendanceRequest;
import com.attendance.model.AttendanceRecord;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.repo.AttendanceRepository;
import com.attendance.repo.StudentRepository;
import com.attendance.repo.SubjectRepository;

@Service
public class AttendanceService {
	@Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    
    public String markAttendance(AttendanceRequest request) {
        Optional<Student> studentOpt = studentRepository.findById(request.getStudentId());
        if (studentOpt.isEmpty()) {
            return "Student with ID " + request.getStudentId() + " does not exist.";
        }

        Optional<Subject> subjectOpt = subjectRepository.findById(request.getSubjectId());
        if (subjectOpt.isEmpty()) {
            return "Subject with ID " + request.getSubjectId() + " does not exist.";
        }

        Student student = studentOpt.get();
        Subject subject = subjectOpt.get();

        if (!student.getDepartmentId().equals(subject.getDepartmentId()) ||
            !student.getSemester().equals(subject.getSemester())) {
            return "Student and Subject do not belong to the same department/semester.";
        }

        AttendanceRecord attendance = new AttendanceRecord();
        attendance.setStudentId(request.getStudentId());
        attendance.setSubjectId(request.getSubjectId());
        attendance.setDate(request.getDate());
        attendance.setPresent(request.getPresent());

        attendanceRepository.save(attendance);

        return "Attendance marked successfully.";
    }
    
    public List<AttendanceRecord> getAttendanceReport(String subject, int semester) {
        return attendanceRepository.findBySubjectAndSemester(subject, semester);
    }
}

