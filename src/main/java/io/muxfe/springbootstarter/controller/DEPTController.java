package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.DEPT;
import io.muxfe.springbootstarter.repository.DEPTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/depts")
public class DEPTController {
  
  @Autowired
  DEPTRepository repository;
  
  @GetMapping("")
  public Page<DEPT> queryAllDeptByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "10") Integer size) {
    return repository.findAll(new PageRequest(page, size, Sort.Direction.ASC, "deptno"));
  }
  
  @PutMapping("")
  public DEPT save(DEPT department) {
    return repository.save(department);
  }
  
  @PostMapping("")
  public DEPT edit(DEPT department) {
    return repository.save(department);
  }
  
  @DeleteMapping("/{deptno}")
  public void delete(@PathVariable Integer deptno) {
    repository.delete(deptno);
  }
  
}
