package ca.marcos.vibesweb.service;

import ca.marcos.vibesweb.controller.dto.UserRegistrationDto;
import ca.marcos.vibesweb.repository.model.Role;
import ca.marcos.vibesweb.repository.model.User;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        Mockito.when(passwordEncoder.encode("123456"))
                .thenReturn("ENCODE");
    }

    @Test
    public void saveUserTest(){
        User user = userService.save(createUser());
        assertNotNull(user);
        assertEquals("Marcos", user.getFirstName());
        assertEquals("Ribeiro", user.getLastName());
        assertEquals("marcos@gmail.com", user.getEmail());
        assertEquals("ENCODE", user.getPassword());
        assertEquals("ROLE_USER", user.getRoles().iterator().next().getName());
    }

    @Test
    public void findByEmailTest(){
        userService.save(createUser());
        User user = userService.findByEmail("marcos@gmail.com");
        assertNotNull(user);
        assertEquals("Marcos", user.getFirstName());
        assertEquals("Ribeiro", user.getLastName());
        assertEquals("marcos@gmail.com", user.getEmail());
        assertEquals("ENCODE", user.getPassword());
        assertEquals("ROLE_USER", user.getRoles().iterator().next().getName());
    }

    @Test
    public void loadUserByUsernameFoundTest(){
        userService.save(createUser());
        UserDetails userDetails = userService.loadUserByUsername("marcos@gmail.com");
        assertNotNull(userDetails);
        assertEquals("marcos@gmail.com", userDetails.getUsername());
        assertEquals("ENCODE", userDetails.getPassword());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    private UserRegistrationDto createUser() {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setFirstName("Marcos");
        user.setLastName("Ribeiro");
        user.setEmail("marcos@gmail.com");
        user.setConfirmEmail("marcos@gmail.com");
        user.setPassword("123456");
        user.setConfirmPassword("123456");
        return user;
    }
}
