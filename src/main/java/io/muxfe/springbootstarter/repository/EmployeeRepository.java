package io.muxfe.springbootstarter.repository;

import io.muxfe.springbootstarter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  
}
