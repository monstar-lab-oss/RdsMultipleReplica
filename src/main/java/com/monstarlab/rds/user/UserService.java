package com.monstarlab.rds.user;

import com.monstarlab.rds.datasource.repository.user.UserRepository;
import com.monstarlab.rds.datasource.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/** Provide user related functionality. */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  List<User> findAll() {
    return userRepository.findAll();
  }

  User createNew(User user) {
    return userRepository.save(user);
  }

  User getUser(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
  }

  User getUser(UUID uuid) {
    return userRepository.findByUuid(uuid);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
