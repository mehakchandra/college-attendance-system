package com.attendance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attendance.dto.AttendanceRequest;
import com.attendance.model.AttendanceRecord;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.repo.AttendanceRepository;
import com.attendance.repo.StudentRepository;
import com.attendance.repo.SubjectRepository;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {

    @Mock
    private AttendanceRepository repository;

    @InjectMocks
    private AttendanceService service;
    
    
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMarkAttendance_Success() {
        // Arrange
        AttendanceRequest request = new AttendanceRequest();
        request.setStudentId(1);
        request.setSubjectId(101);
        request.setDate(new Date());
        request.setPresent(true);

        Student student = new Student();
        student.setStudentId(1);
        student.setDepartmentId(10);
        student.setSemester(3);

        Subject subject = new Subject();
        subject.setSubjectId(101);
        subject.setDepartmentId(10);
        subject.setSemester(3);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(101)).thenReturn(Optional.of(subject));

        // Act
        String result = service.markAttendance(request);

        // Assert
        assertEquals("Attendance marked successfully.", result);
        verify(attendanceRepository, times(1)).save(any(AttendanceRecord.class));
    }

    @Test
    public void testMarkAttendance_StudentNotFound() {
        AttendanceRequest request = new AttendanceRequest();
        request.setStudentId(1);
        request.setSubjectId(101);

        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        String result = service.markAttendance(request);
        assertEquals("Student with ID 1 does not exist.", result);
    }

    @Test
    public void testMarkAttendance_SubjectNotFound() {
        AttendanceRequest request = new AttendanceRequest();
        request.setStudentId(1);
        request.setSubjectId(101);

        Student student = new Student();
        student.setStudentId(1);
        student.setDepartmentId(10);
        student.setSemester(3);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(101)).thenReturn(Optional.empty());

        String result = service.markAttendance(request);
        assertEquals("Subject with ID 101 does not exist.", result);
    }

    @Test
    public void testMarkAttendance_MismatchedDepartmentOrSemester() {
        AttendanceRequest request = new AttendanceRequest();
        request.setStudentId(1);
        request.setSubjectId(101);

        Student student = new Student();
        student.setStudentId(1);
        student.setDepartmentId(10);
        student.setSemester(3);

        Subject subject = new Subject();
        subject.setSubjectId(101);
        subject.setDepartmentId(20); // different department
        subject.setSemester(3);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(101)).thenReturn(Optional.of(subject));

        String result = service.markAttendance(request);
        assertEquals("Student and Subject do not belong to the same department/semester.", result);
    }


    @Test
    public void testGetAttendanceReport() {
        AttendanceRecord record = new AttendanceRecord();
        record.setStudentId(101);
        record.setSubjectId(1);
        record.setPresent(true);

        when(repository.findBySubjectAndSemester("Maths", 4))
                .thenReturn(Arrays.asList(record));

        List<AttendanceRecord> result = service.getAttendanceReport("Maths", 4);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getStudentId());
    }
}
