package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.Employee;
import io.muxfe.springbootstarter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeRepository repository;

  @Autowired
  public EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/")
  public Page<Employee> findAll(Pageable pageable, Employee employee) {
    return repository.findAll(
      Example.of(employee,
        ExampleMatcher.matching()
          .withIgnoreCase()
          .withIgnoreNullValues()
          .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable);
  }
}
