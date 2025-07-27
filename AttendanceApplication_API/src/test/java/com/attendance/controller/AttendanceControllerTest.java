package com.attendance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.attendance.dto.AttendanceRequest;
import com.attendance.model.AttendanceRecord;
import com.attendance.service.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.media.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService service;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetReport() throws Exception {
        AttendanceRecord record = new AttendanceRecord();
        record.setStudentId(101);
        record.setSubjectId(1);
        record.setPresent(true);

        Mockito.when(service.getAttendanceReport("Maths", 4))
                .thenReturn(Collections.singletonList(record));

        mockMvc.perform(get("/attendance/report")
                        .param("subject", "Maths")
                        .param("semester", "4"))
                .andExpect(status().isOk());
       
    }
        @Test
        public void testMarkAttendance_Success() throws Exception {
            AttendanceRequest request = new AttendanceRequest();
            request.setStudentId(1);
            request.setSubjectId(101);
            request.setDate(new Date());
            request.setPresent(true);

            // Mock service response
            Mockito.when(service.markAttendance(Mockito.any()))
                    .thenReturn("Attendance marked successfully.");

            mockMvc.perform(post("/attendance/mark")
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Attendance marked successfully."));
        }
    
}
