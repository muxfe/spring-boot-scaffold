package io.muxfe.springbootscaffold.repository;

import io.muxfe.springbootscaffold.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  
}
