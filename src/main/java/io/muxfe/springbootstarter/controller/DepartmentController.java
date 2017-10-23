package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.Department;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/api/departments")
public class DepartmentController extends FuzzySearchController<Department> {

}
