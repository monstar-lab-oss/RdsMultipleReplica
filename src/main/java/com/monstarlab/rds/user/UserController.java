package com.monstarlab.rds.user;

import com.monstarlab.rds.datasource.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class UserController {

  private final UserService service;

  @GetMapping("/user")
  Iterable<User> findAll() {
    return service.findAll();
  }

  @PostMapping("/user")
  User newUser(@RequestBody User newUser) {
    return service.createNew(newUser);
  }

  @GetMapping("/user/{uuid}")
  User findUser(@PathVariable UUID uuid) {
    return service.getUser(uuid);
  }

  @DeleteMapping("/user/{id}")
  void deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
  }
}
