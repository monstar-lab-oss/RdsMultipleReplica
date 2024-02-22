package com.monstarlab.rds.user;

import com.monstarlab.rds.datasource.repository.user.UserRepository;
import com.monstarlab.rds.datasource.schema.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void findAll() {
        //GIVEN
        User user = new User();
        user.setFirstname("TestName");
        user.setLastname("TestLastname");
        doReturn(new ArrayList<>(){{
            add(user);
        }}).when(userRepository).findAll();

        //WHEN
        List<User> users = userService.findAll();

        //THEN
        assertThat(users.get(0).getFirstname()).isEqualTo("TestName");
        verify(userRepository).findAll();
    }

    @Test
    void createNew() {
        //GIVEN
        User user = new User();
        user.setFirstname("TestName");
        user.setLastname("TestLastname");

        //WHEN
        userService.createNew(user);

        //THEN
        verify(userRepository).save(user);
    }

    @Test
    void getUser() {
        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setFirstname("TestName");
        user.setLastname("TestLastname");
        doReturn(Optional.of(user)).when(userRepository).findById(1L);

        //WHEN
        User u = userService.getUser(1L);

        //THEN
        assertThat(u).isEqualTo(user);
        verify(userRepository).findById(eq(1L));
    }

    @Test
    void deleteUser() {
        //GIVEN

        //WHEN
        userService.deleteUser(1L);

        //THEN
        verify(userRepository).deleteById(1L);
    }
}