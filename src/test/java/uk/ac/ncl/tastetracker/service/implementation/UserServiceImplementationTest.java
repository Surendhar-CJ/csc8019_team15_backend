package uk.ac.ncl.tastetracker.service.implementation;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;

import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplementationTest {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @Test
    public void testUserRegisterInvalidEmail() {
        User user = new User();
        user.setUsername("Jack");
        user.setEmail("Jack520163");
        user.setPassword("");
        assertThrows(InvalidInputException.class, () -> {
            userServiceImplementation.userRegister(user);
        });
    }

    @Test
    public void testUserRegisterInvalidPassword() {
        User user = new User();
        user.setUsername("Jack");
        user.setEmail("Jack520@163");
        user.setPassword("123");
        assertThrows(InvalidInputException.class, () -> {
            userServiceImplementation.userRegister(user);
        });
    }

    @Test
    public void testUserRegister() {
        User user = new User();
        user.setUsername("Jack");
        user.setEmail("Jack520@163");
        user.setPassword("@123456789");
        userServiceImplementation.userRegister(user);
    }


    @Test
    public void testUserLoginInvalidUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImplementation.userLogin("Jack1","@12345678");
        });

    }

    @Test
    public void testUserLoginInvalidPassward() {
        assertThrows(InvalidCredentialsException.class, () -> {
            userServiceImplementation.userLogin("Jack","@12345678");
        });
    }

    @Test
    public void testUserLogin() {
         userServiceImplementation.userLogin("Jack","@123456789");
    }
}
