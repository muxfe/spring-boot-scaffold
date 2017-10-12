package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.Department;
import io.muxfe.springbootstarter.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
  
  private final DepartmentRepository repository;

  @Autowired
  public DepartmentController(DepartmentRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/")
  public Page<Department> findAll(Pageable pageable, Department department) {
    return repository.findAll(
      Example.of(department, 
        ExampleMatcher.matching()
          .withIgnoreCase()
          .withIgnoreNullValues()
          .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable);
  }
}
