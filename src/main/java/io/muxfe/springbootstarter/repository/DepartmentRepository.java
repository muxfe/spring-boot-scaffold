package io.muxfe.springbootstarter.repository;

import io.muxfe.springbootstarter.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
  
}
