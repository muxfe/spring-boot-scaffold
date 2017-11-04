package io.muxfe.springbootscaffold.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.muxfe.springbootscaffold.entity.Department;
import io.muxfe.springbootscaffold.entity.Employee;
import io.muxfe.springbootscaffold.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTests {

  @Autowired
  private MockMvc mockMvc;

  private final String apiUrl = "/api/employees";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmployeeRepository repository;

  private Employee employee;

  @Before
  public void setup() {
    Department department = new Department(null, "test", "test");
    Employee manager = new Employee(null, "manager", "manager", null,
      System.currentTimeMillis(),
      0.0, 0.0, department);
    employee = repository.save(new Employee(
      null, "test", "test", manager,
      System.currentTimeMillis(),
      0.0, 0.0, department));
  }

  @Test
  public void findAllEmployeesByPaginationTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl).
        accept(MediaType.APPLICATION_JSON).
        param("page", "0").
        param("size", "10").
        param("sort", "id,desc")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType("application/json;charset=UTF-8")).
      andExpect(jsonPath("$._embedded.employees").isArray()).
      andExpect(jsonPath("$.page.number").value(0)).
      andExpect(jsonPath("$.page.size").value(10)).
      andExpect(jsonPath("$.page.totalElements").isNumber()).
      andExpect(jsonPath("$.page.totalPages").value(1));
  }

  @Test
  public void findEmployeeByIdTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/{id}", employee.getId()).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType("application/json;charset=UTF-8")).
      andExpect(jsonPath("$.id").value(employee.getId())).
      andExpect(jsonPath("$.ename").value(employee.getEname())).
      andExpect(jsonPath("$.job").value(employee.getJob())).
      andExpect(jsonPath("$.hiredate").value(employee.getHiredate())).
      andExpect(jsonPath("$.sal").value(employee.getSal())).
      andExpect(jsonPath("$.comm").value(employee.getComm()));
  }

  @Test
  @WithMockUser
  public void createEmployeeTest() throws Exception {
    Employee newEmployee = new Employee();
    newEmployee.setEname("test");
    this.mockMvc.perform(
      post(apiUrl).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(newEmployee)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isCreated()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.ename").value("test"));
  }

  @Test
  @WithMockUser
  public void updateEmployeeTest() throws Exception {
    Employee body = new Employee(
      employee.getId(), "changed", "changed", employee.getMgr(),
      System.currentTimeMillis(),
      0.0, 0.0, employee.getDepartment());
    this.mockMvc.perform(
      put(apiUrl + "/{empno}", employee.getId()).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(body)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.ename").value("changed")).
      andExpect(jsonPath("$.job").value("changed"));
  }

  @Test
  @WithMockUser
  public void partialUpdateEmployeeTest() throws Exception {
    this.mockMvc.perform(
      patch(apiUrl + "/{empno}", employee.getId()).
        contentType(MediaType.APPLICATION_JSON).
        content("{\"ename\":\"changed\"}").
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.ename").value("changed")).
      andExpect(jsonPath("$.job").value("test"));
  }

  @Test
  @WithMockUser
  public void deleteEmployeeTest() throws Exception {
    this.mockMvc.perform(delete(apiUrl + "/{empno}", employee.getId())).
      andDo(print()).
      andExpect(status().isNoContent());
  }

  @After
  public void clean() {
    repository.deleteAll();
  }
}
