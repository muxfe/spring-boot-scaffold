package io.muxfe.springbootscaffold.controller;

import io.muxfe.springbootscaffold.entity.Employee;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/api/employees")
public class EmployeeController extends FuzzySearchController<Employee> {

}
