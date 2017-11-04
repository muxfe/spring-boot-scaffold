package io.muxfe.springbootscaffold.controller;

import io.muxfe.springbootscaffold.entity.User;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/api/users")
public class UserController extends FuzzySearchController<User> {
  
}
