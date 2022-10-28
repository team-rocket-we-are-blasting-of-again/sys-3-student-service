package com.teamrocket.sys3studentservice.repository;

import com.teamrocket.sys3studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {



}
