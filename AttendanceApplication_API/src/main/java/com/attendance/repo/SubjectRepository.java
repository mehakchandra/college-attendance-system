package com.attendance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attendance.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
