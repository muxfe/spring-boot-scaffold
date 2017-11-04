package io.muxfe.springbootscaffold.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
public class Employee {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String ename;
  
  private String job;
  
  @ManyToOne(cascade = CascadeType.ALL)
  private Employee mgr;
  
  private Long hiredate;
  
  private Double sal;
  
  private Double comm;
  
  @ManyToOne(cascade = CascadeType.ALL)
  private Department department;
  
  public Employee() { // for jpa
  }
}
