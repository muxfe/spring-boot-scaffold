package io.muxfe.springbootscaffold.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
public class Department {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String dname;
  
  private String loc;
  
  public Department() { // for jpa
  }
}
