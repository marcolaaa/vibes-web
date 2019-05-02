package ca.marcos.vibesweb.service;

import ca.marcos.vibesweb.repository.RejectedQuotesRepository;
import ca.marcos.vibesweb.repository.model.Quote;
import ca.marcos.vibesweb.repository.model.RejectedQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(QuotesService.class)
public class QuoteServiceTest {

    @Autowired
    private QuotesService service;

    @Autowired
    private MockRestServiceServer rest;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RejectedQuotesRepository rejectedQuotesRepository;

    @Before
    public void setUp() throws Exception {
        String response =
                objectMapper.writeValueAsString(crateQuotesList());

        rest.expect(requestTo("https://vibes.dgrzyb.me/api/v1/quotes?client_id=bf002af5-978e-4d48-8ef6-34b4e305fb6f"))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        List<RejectedQuote> rejectedQuoteList = new ArrayList<>();
        rejectedQuoteList.add(new RejectedQuote("1", "marcos@gmail.com","Test quote 1."));

        Mockito.when(rejectedQuotesRepository.findAllByUserEmail("marcos@gmail.com"))
                .thenReturn(rejectedQuoteList);
    }

    private List<Quote> crateQuotesList() {
        List<Quote> response = new ArrayList<>();
        response.add(new Quote("1", "Test quote 1.", null, 150, false));
        response.add(new Quote("2", "Test quote 2.", null, 150, false));
        response.add(new Quote("3", "Test quote 3.", null, 150, false));

        return response;
    }

    @Test
    @WithMockUser("ribeiro@gmail.com") //this user does not have rejected quotes.
    public void getQuotesListWithNoRejectedQuotes(){
        List<Quote> response = service.getQuotesList();
        assertNotNull(response);
        assertTrue(response.size() == 3);
        assertEquals("1", response.get(0).getId());
        assertEquals("2", response.get(1).getId());
        assertEquals("3", response.get(2).getId());
    }

    @Test
    @WithMockUser("marcos@gmail.com")
    public void getQuotesListWithOneRejectedQuotes(){
        List<Quote> response = service.getQuotesList();
        assertNotNull(response);
        assertTrue(response.size() == 2);
        assertEquals("2", response.get(0).getId());
        assertEquals("3", response.get(1).getId());
    }
}
