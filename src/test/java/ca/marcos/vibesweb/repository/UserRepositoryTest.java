package ca.marcos.vibesweb.repository;

import ca.marcos.vibesweb.repository.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailFound(){

        User user = new User("Marcos", "Ribeiro", "marcos@gmail.com", "1234567890");
        entityManager.persistAndFlush(user);

        User found = userRepository.findByEmail(user.getEmail());

        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    public void findByEmailNotFound(){

        User user = new User("Marcos", "Ribeiro", "marcos@gmail.com", "1234567890");
        entityManager.persistAndFlush(user);

        User found = userRepository.findByEmail("error@gmail.com");

        assertNull(found);
    }
}
