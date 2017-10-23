package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.Employee;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/api/employees")
public class EmployeeController extends FuzzySearchController<Employee> {

}
