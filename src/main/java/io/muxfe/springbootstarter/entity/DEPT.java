package io.muxfe.springbootstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
public class DEPT {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer deptno;
  
  private String dname;
  
  private String loc;
  
  DEPT() { // for jpa
  }
  
}
