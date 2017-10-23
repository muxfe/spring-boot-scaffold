package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.User;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/api/users")
public class UserController extends FuzzySearchController<User> {
  
}
