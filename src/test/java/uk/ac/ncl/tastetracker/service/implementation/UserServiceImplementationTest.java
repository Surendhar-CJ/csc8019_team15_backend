package uk.ac.ncl.tastetracker.service.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ncl.tastetracker.dto.dtomapper.UserDTOMapper;
import uk.ac.ncl.tastetracker.entity.User;
import uk.ac.ncl.tastetracker.exception.custom.InvalidCredentialsException;
import uk.ac.ncl.tastetracker.exception.custom.InvalidInputException;
import uk.ac.ncl.tastetracker.repository.UserRepository;
import static org.junit.Assert.assertThrows;


/**
 * UserServiceImplementationTest is used to test RestaurantServiceImplementation class.
 *
 * @author Sandy Zhang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplementationTest {

    /**
     * UserServiceImplementation dependency to test the user service implementation class
     */
    @Autowired
    private UserServiceImplementation userServiceImplementation;

    /**
     * UserDTOMapper that maps user to user dto
     */
    @Autowired
    private UserDTOMapper userDTOMapper;

    /**
     * UserRepository that performs database related operations
     */
    @MockBean
    private UserRepository userRepository;




    /**
     * Tests user registration by invalid email
     */
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



    /**
     * Tests user registration by invalid password
     */
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



    /**
     * Tests user register
     */
    @Test
    public void testUserRegister() {
        User user = new User();
        user.setUsername("Jack");
        user.setEmail("Jack520@163");
        user.setPassword("@123456789");
        userServiceImplementation.userRegister(user);
    }



    /**
     * Tests user login by invalid username
     */
    @Test
    public void testUserLoginInvalidUsername() {
        assertThrows(InvalidCredentialsException.class, () -> {
            userServiceImplementation.userLogin("Jack1","@12345678");
        });

    }



    /**
     * Tests user login by invalid password
     */
    @Test
    public void testUserLoginInvalidPassword() {
        assertThrows(InvalidCredentialsException.class, () -> {
            userServiceImplementation.userLogin("Jack","@12345678");
        });
    }


}
