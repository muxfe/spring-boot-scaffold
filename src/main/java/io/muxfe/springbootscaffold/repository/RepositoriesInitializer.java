package io.muxfe.springbootscaffold.repository;

import io.muxfe.springbootscaffold.entity.Department;
import io.muxfe.springbootscaffold.entity.Employee;
import io.muxfe.springbootscaffold.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoriesInitializer {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DepartmentRepository departmentRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private final Object lock = new Object();

  private boolean initialized = false;

  public void initialize() {
    if (!initialized) {
      synchronized (lock) {
        if (!initialized) {
          initializeAll();
          initialized = true;
        }
      }
    }
  }

  private void initializeAll() {
    initializeUsers();
    initializeDepartments();
    initializeEmployees();
  }

  private void initializeUsers() {
    User user;
    List<String> roles = new ArrayList<>();
    roles.add("USER");
    user = new User(
      "user",
      bCryptPasswordEncoder.encode("pass"),
      roles,
      null);
    userRepository.save(user);
    roles.clear();
    roles.add("ADMIN");
    user = new User(
      "admin",
      bCryptPasswordEncoder.encode("pass"),
      roles,
      null);
    userRepository.save(user);
    roles.clear();
    roles.add("ACTUATOR");
    user = new User(
      "actuator",
      bCryptPasswordEncoder.encode("pass"),
      roles,
      null);
    userRepository.save(user);
  }

  private void initializeDepartments() {
    departmentRepository.save(new Department(null, "test-1", "test-1"));
    departmentRepository.save(new Department(null, "test-2", "test-2"));
    departmentRepository.save(new Department(null, "test-3", "test-3"));
  }

  private void initializeEmployees() {
    Department department = new Department(null, "test-4", "test-4");
    Employee manager = new Employee(null, "manager-1", "manager-1", null,
      System.currentTimeMillis(),
      0.0, 0.0, department);
    employeeRepository.save(new Employee(
      null, "employee-1", "employee-1", manager,
      System.currentTimeMillis(),
      0.0, 0.0, department));
  }

}
