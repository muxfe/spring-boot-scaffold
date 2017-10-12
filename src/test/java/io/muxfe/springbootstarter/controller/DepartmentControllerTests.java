package io.muxfe.springbootstarter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.muxfe.springbootstarter.entity.Department;
import io.muxfe.springbootstarter.repository.DepartmentRepository;
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
public class DepartmentControllerTests {

  @Autowired
  private MockMvc mockMvc;

  private final String apiUrl = "/api/departments";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private DepartmentRepository repository;

  private Department department;

  @Before
  public void setup() {
    department = repository.save(new Department(null, "test", "test"));
  }

  @Test
  public void findAllDepartmentsTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/").
        accept(MediaType.APPLICATION_JSON).
        param("page", "0").
        param("size", "10").
        param("sort", "deptno,desc")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.content").isArray()).
      andExpect(jsonPath("$.number").value(0)).
      andExpect(jsonPath("$.size").value(10)).
      andExpect(jsonPath("$.numberOfElements").isNumber());
  }

  @Test
  public void findDepartmentByIdTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/").
        accept(MediaType.APPLICATION_JSON).
        param("deptno", department.getDeptno().toString())).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.content").isArray()).
      andExpect(jsonPath("$.content[0].deptno").value(department.getDeptno())).
      andExpect(jsonPath("$.number").value(0)).
      andExpect(jsonPath("$.size").value(20)).
      andExpect(jsonPath("$.numberOfElements").value(1));
  }
  
  @Test 
  public void findDepartmentsByDnameWithContaining() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/").
        accept(MediaType.APPLICATION_JSON).
        param("dname", "e")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.content").isArray()).
      andExpect(jsonPath("$.content[0].dname").value(department.getDname())).
      andExpect(jsonPath("$.number").value(0)).
      andExpect(jsonPath("$.size").value(20)).
      andExpect(jsonPath("$.numberOfElements").isNumber());
  }

  @Test
  @WithMockUser
  public void createDepartmentTest() throws Exception {
    Department department = new Department(null, "test", "test");
    this.mockMvc.perform(
      post(apiUrl).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(department)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isCreated()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.dname").value("test")).
      andExpect(jsonPath("$.loc").value("test"));
  }

  @Test
  @WithMockUser
  public void updateDepartmentTest() throws Exception {
    Department body = new Department(department.getDeptno(), "changed", "changed");
    this.mockMvc.perform(
      put(apiUrl + "/{deptno}", department.getDeptno()).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(body)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.dname").value("changed")).
      andExpect(jsonPath("$.loc").value("changed"));
  }

  @Test
  @WithMockUser
  public void partialUpdateDepartmentTest() throws Exception {
    this.mockMvc.perform(
      patch(apiUrl + "/{deptno}", department.getDeptno()).
        contentType(MediaType.APPLICATION_JSON).
        content("{\"dname\":\"changed\"}").
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.dname").value("changed")).
      andExpect(jsonPath("$.loc").value("test"));
  }

  @Test
  @WithMockUser
  public void deleteDepartmentTest() throws Exception {
    this.mockMvc.perform(delete(apiUrl + "/{deptno}", department.getDeptno())).
      andDo(print()).
      andExpect(status().isNoContent());
  }

  @After
  public void clean() {
    repository.deleteAll();
  }
}
