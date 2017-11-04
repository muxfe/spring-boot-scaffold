package io.muxfe.springbootscaffold.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.muxfe.springbootscaffold.entity.Department;
import io.muxfe.springbootscaffold.repository.DepartmentRepository;
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
  public void findAllDepartmentsByPaginationTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl).
        accept(MediaType.APPLICATION_JSON).
        param("page", "0").
        param("size", "10").
        param("sort", "id,desc")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$._embedded.departments").isArray()).
      andExpect(jsonPath("$.page.number").value(0)).
      andExpect(jsonPath("$.page.size").value(10)).
      andExpect(jsonPath("$.page.totalElements").isNumber()).
      andExpect(jsonPath("$.page.totalPages").value(1));
  }

  @Test
  public void findDepartmentByIdTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/{id}", department.getId()).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.id").value(department.getId())).
      andExpect(jsonPath("$.dname").value(department.getDname())).
      andExpect(jsonPath("$.loc").value(department.getLoc()));
  }

  @Test
  public void findDepartmentsByFuzzySearch() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/fuzzy").
        accept(MediaType.APPLICATION_JSON).
        param("dname", "1")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$._embedded.departments").isArray()).
      andExpect(jsonPath("$._embedded.departments[0].dname").value("test-1")).
      andExpect(jsonPath("$.page.number").value(0)).
      andExpect(jsonPath("$.page.size").value(20)).
      andExpect(jsonPath("$.page.totalElements").value(1)).
      andExpect(jsonPath("$.page.totalPages").value(1));
  }

  @Test
  @WithMockUser
  public void createDepartmentTest() throws Exception {
    Department department = new Department(null, "test-create", "test-create");
    this.mockMvc.perform(
      post(apiUrl).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(department)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isCreated()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.dname").value("test-create")).
      andExpect(jsonPath("$.loc").value("test-create"));
  }

  @Test
  @WithMockUser
  public void updateDepartmentTest() throws Exception {
    Department body = new Department(department.getId(), "changed", "changed");
    this.mockMvc.perform(
      put(apiUrl + "/{id}", department.getId()).
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
      patch(apiUrl + "/{id}", department.getId()).
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
    this.mockMvc.perform(delete(apiUrl + "/{id}", department.getId())).
      andDo(print()).
      andExpect(status().isNoContent());
  }

  @After
  public void clean() {
    repository.deleteAll();
  }
}
