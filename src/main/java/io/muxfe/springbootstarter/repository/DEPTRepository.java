package io.muxfe.springbootstarter.repository;

import io.muxfe.springbootstarter.entity.DEPT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DEPTRepository extends JpaRepository<DEPT, Integer> {
  
}
