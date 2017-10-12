package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.User;
import io.muxfe.springbootstarter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserRepository repository;

  @Autowired
  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/")
  @RolesAllowed("ADMIN")
  public Page<User> findAll(Pageable pageable, User user) {
    return repository.findAll(Example.of(user, 
      ExampleMatcher.matching()
        .withIgnoreCase()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), pageable);
  }
}
