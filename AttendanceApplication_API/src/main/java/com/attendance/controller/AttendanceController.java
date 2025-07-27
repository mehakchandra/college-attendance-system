package com.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.dto.AttendanceRequest;
import com.attendance.model.AttendanceRecord;
import com.attendance.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request) {
        String result = attendanceService.markAttendance(request);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/report")
    public List<AttendanceRecord> getReport(@RequestParam String subject,
                                            @RequestParam int semester) {
        return attendanceService.getAttendanceReport(subject, semester);
    }
    
  
}
