package ca.marcos.vibesweb.repository;

import ca.marcos.vibesweb.repository.model.RejectedQuote;
import ca.marcos.vibesweb.repository.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RejectedQuotesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RejectedQuotesRepository rejectedQuotesRepository;

    @Test
    public void findAllByUserEmail(){
        fillDatabase();

        List<RejectedQuote> quotes = rejectedQuotesRepository.findAllByUserEmail("marcos@gmail.com");
        assertTrue(quotes.size() == 3);

    }


    @Test
    public void deleteAllByUserEmail(){
        fillDatabase();

        rejectedQuotesRepository.deleteAllByUserEmail("marcos@gmail.com");

        List<RejectedQuote> quotesAfterDelete = rejectedQuotesRepository.findAllByUserEmail("marcos@gmail.com");
        assertTrue(quotesAfterDelete.isEmpty());
    }

    @Test
    public void findAllByUserEmailAnotherEmail(){
        fillDatabase();

        rejectedQuotesRepository.deleteAllByUserEmail("marcos@gmail.com");

        List<RejectedQuote> quotes = rejectedQuotesRepository.findAllByUserEmail("anotheremail@gmail.com");
        assertTrue(quotes.size() == 1);

    }

    private void fillDatabase() {
        RejectedQuote quote1 = new RejectedQuote("123", "marcos@gmail.com", "Test rejected quote 1.");
        entityManager.persist(quote1);
        RejectedQuote quote2 = new RejectedQuote("321", "marcos@gmail.com", "Test rejected quote 2.");
        entityManager.persist(quote2);
        RejectedQuote quote3 = new RejectedQuote("000", "marcos@gmail.com", "Test rejected quote 3.");
        entityManager.persist(quote3);
        RejectedQuote anotherEmail = new RejectedQuote("111", "anotheremail@gmail.com", "Another email test");
        entityManager.persist(anotherEmail);
        entityManager.flush();
    }

}
