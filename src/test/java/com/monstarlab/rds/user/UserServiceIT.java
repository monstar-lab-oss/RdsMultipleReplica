package com.monstarlab.rds.user;

import com.monstarlab.rds.RdsMultipleReplicaApplication;
import com.monstarlab.rds.datasource.repository.user.UserRepository;
import com.monstarlab.rds.datasource.schema.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = RdsMultipleReplicaApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class UserServiceIT {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MockMvc mockMvc;

  @Test
  void saveAndRetrieveUserWithDefault() throws Exception {
    //GIVEN
    var user = new User();
    user.setUuid(UUID.fromString("ba61da8c-938a-48a6-8eb6-55aa08cd1b08"));
    user.setFirstname("Name");
    user.setLastname("Lastname");
    userRepository.save(user);

    //WHEN
    mockMvc.perform(MockMvcRequestBuilders.get("/user/{uuid}", "ba61da8c-938a-48a6-8eb6-55aa08cd1b08"))
    //THEN
            .andExpect((MockMvcResultMatchers.status().isOk()))
            .andExpect(result -> {
              var content = result.getResponse().getContentAsString();
              Assertions.assertThat(content).contains("Lastname");
            });

    assertTrue(userRepository.existsByUuid(UUID.fromString("ba61da8c-938a-48a6-8eb6-55aa08cd1b08")));
  }

  @Test
  void deleteWithPrimary() throws Exception {
    //GIVEN
    var user = new User();
    user.setUuid(UUID.fromString("ca61da8c-938a-48a6-8eb6-55aa08cd1b08"));
    user.setFirstname("Name");
    user.setLastname("Lastname");
    userRepository.save(user);

    //WHEN
    mockMvc.perform(MockMvcRequestBuilders.get("/user/{uuid}", "ca61da8c-938a-48a6-8eb6-55aa08cd1b08"))
            //THEN
            .andExpect((MockMvcResultMatchers.status().isOk()))
            .andExpect(result -> {
              var content = result.getResponse().getContentAsString();
              Assertions.assertThat(content).contains("Lastname");
            });

    //WHEN
    userRepository.delete(user);

    //THEN
    assertFalse(userRepository.existsByUuid(UUID.fromString("ca61da8c-938a-48a6-8eb6-55aa08cd1b08")));
  }

  @Test
  void deleteWithReadOnly() {
    //GIVEN
    var user = new User();
    user.setUuid(UUID.fromString("da61da8c-938a-48a6-8eb6-55aa08cd1b08"));
    user.setFirstname("Name");
    user.setLastname("Lastname");
    userRepository.save(user);

    //WHEN
    userRepository.deleteAll();

    //THEN
    assertTrue(userRepository.existsByUuid(UUID.fromString("da61da8c-938a-48a6-8eb6-55aa08cd1b08")));
  }
}
