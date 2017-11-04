package io.muxfe.springbootscaffold.controller;

import io.muxfe.springbootscaffold.entity.Department;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

@RepositoryRestController
@RequestMapping("/departments")
public class DepartmentController extends FuzzySearchController<Department> {

}
