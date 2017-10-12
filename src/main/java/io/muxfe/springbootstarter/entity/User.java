package io.muxfe.springbootstarter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "sys_user")
public class User {

  private String username;
  @JsonIgnore
  private String password;
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  
  public User() {
  }
}
