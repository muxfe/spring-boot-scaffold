package io.muxfe.springbootscaffold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.hateoas.PagedResources.wrap;

public abstract class FuzzySearchController<T> {

  @Autowired
  private JpaRepository<T, Long> repository;

  private final ExampleMatcher defaultExampleMatcher =
    ExampleMatcher.matching().
      withIgnoreCase().
      withIgnorePaths("id").
      withIgnoreNullValues().
      withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

  @GetMapping("/fuzzy")
  public ResponseEntity<PagedResources> fuzzySearch(T x, Pageable pageable) {
    Page<T> data = repository.findAll(Example.of(x, defaultExampleMatcher), pageable);
    PagedResources.PageMetadata pageMetadata =
      new PagedResources.PageMetadata(
        data.getSize(),
        data.getNumber(),
        data.getTotalElements(),
        data.getTotalPages());
    return ResponseEntity.ok(wrap(data, pageMetadata));
  }
}
