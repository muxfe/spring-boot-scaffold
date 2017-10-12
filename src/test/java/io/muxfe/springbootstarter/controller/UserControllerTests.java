package io.muxfe.springbootstarter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.muxfe.springbootstarter.entity.User;
import io.muxfe.springbootstarter.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  private final String apiUrl = "/api/users";

  @Autowired
  private ObjectMapper objectMapper;
  
  @Autowired
  private UserRepository repository;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  private User user;
  
  private List<String> roles;

  @Before
  public void setup() {
    roles = new ArrayList<>();
    roles.add("USER");
    user = repository.save(new User("testuser", bCryptPasswordEncoder.encode("pass"), roles, null));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void findAllUsersTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/").
        accept(MediaType.APPLICATION_JSON).
        param("page", "0").
        param("size", "10").
        param("sort", "id,desc")).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.content").isArray()).
      andExpect(jsonPath("$.number").value(0)).
      andExpect(jsonPath("$.size").value(10)).
      andExpect(jsonPath("$.numberOfElements").isNumber());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void findUserByIdTest() throws Exception {
    this.mockMvc.perform(
      get(apiUrl + "/").
        accept(MediaType.APPLICATION_JSON).
        param("id", user.getId().toString())).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.content").isArray()).
      andExpect(jsonPath("$.content[0].id").value(user.getId())).
      andExpect(jsonPath("$.number").value(0)).
      andExpect(jsonPath("$.size").value(20)).
      andExpect(jsonPath("$.numberOfElements").value(1));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void createUserTest() throws Exception {
    User user = new User("testCreateUser", "pass", roles, null);
    this.mockMvc.perform(
      post(apiUrl).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(user)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isCreated()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.username").value("testCreateUser")).
      andExpect(jsonPath("$.roles[0]").value("USER"));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void updateUserTest() throws Exception {
    User body = new User("changedName", "pass", Arrays.asList("ADMIN"), user.getId());
    this.mockMvc.perform(
      put(apiUrl + "/{id}", user.getId()).
        contentType(MediaType.APPLICATION_JSON).
        content(objectMapper.writeValueAsString(body)).
        accept(MediaType.APPLICATION_JSON)).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$._links.self.href").value("http://localhost/api/users/" + user.getId().toString())).
      andExpect(jsonPath("$.username").value("changedName")).
      andExpect(jsonPath("$.roles[0]").value("ADMIN"));
  }
  
  @Test
  @WithMockUser(roles = "ADMIN")
  public void partialUpdateUserTest() throws Exception {
    RequestBuilder requestBuilder = patch(apiUrl + "/{id}", user.getId()).
      contentType(MediaType.APPLICATION_JSON).
      content("{\"username\":\"changed\"}").
      accept(MediaType.APPLICATION_JSON);
    this.mockMvc.perform(requestBuilder).
      andDo(print()).
      andExpect(status().isOk()).
      andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
      andExpect(jsonPath("$.username").value("changed"));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteUserTest() throws Exception {
    this.mockMvc.perform(delete(apiUrl + "/{id}", user.getId())).
      andDo(print()). 
      andExpect(status().isNoContent());
  }
  
  @After
  public void clean() {
    repository.deleteAll();
  }
}
