package io.muxfe.springbootstarter.repository;

import io.muxfe.springbootstarter.entity.EMP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EMPRepository extends JpaRepository<EMP, Integer> {
  
}
