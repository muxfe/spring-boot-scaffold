package io.muxfe.springbootstarter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
public class EMP {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer empno;
  
  private String ename;
  
  private String job;
  
  private Integer mgr;
  
  private Timestamp hiredate;
  
  private Double sal;
  
  private Double comm;
  
  private Integer deptno;
  
  EMP() { // for jpa
  }
  
}
