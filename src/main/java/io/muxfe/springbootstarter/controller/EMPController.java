package io.muxfe.springbootstarter.controller;

import io.muxfe.springbootstarter.entity.EMP;
import io.muxfe.springbootstarter.repository.EMPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/emps")
public class EMPController {
  
  @Autowired
  EMPRepository repository;
  
  @GetMapping("")
  public Page<EMP> queryAllEMPByPage(
    @RequestParam(value = "page", defaultValue = "0") Integer page,
    @RequestParam(value = "size", defaultValue = "10") Integer size) {
    return repository.findAll(new PageRequest(page, size, Sort.Direction.ASC, "empno"));
  }
  
  @PutMapping("")
  public EMP save(EMP employee) {
    employee.setHiredate(new Timestamp(System.currentTimeMillis()));
    return repository.save(employee);
  }
  
  @PostMapping("")
  public EMP edit(EMP employee) {
    return repository.save(employee);
  }
  
  @DeleteMapping("/{empno}")
  public void delete(@PathVariable Integer empno) {
    repository.delete(empno);
  }
  
}
