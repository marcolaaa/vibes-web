package ca.marcos.vibesweb.service;

import ca.marcos.vibesweb.controller.dto.RejectedQuoteDto;
import ca.marcos.vibesweb.repository.RejectedQuotesRepository;
import ca.marcos.vibesweb.repository.model.Quote;
import ca.marcos.vibesweb.repository.model.RejectedQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuotesServiceImpl implements QuotesService {

    @Autowired
    private RejectedQuotesRepository rejectedQuotesRepository;
    private final RestTemplate restTemplate;

    public QuotesServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Quote> getQuotesList() {
        ResponseEntity<List<Quote>> response = restTemplate.exchange(
                "https://vibes.dgrzyb.me/api/v1/quotes?client_id=bf002af5-978e-4d48-8ef6-34b4e305fb6f",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Quote>>(){});

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<RejectedQuote> rejectedQuotes = rejectedQuotesRepository.findAllByUserEmail(authentication.getName());
            //verify if there is rejected quotes.
            if (!rejectedQuotes.isEmpty()) {
                if(rejectedQuotes.size() <= 300) {
                    List<Quote> result = filterQuotes(response, rejectedQuotes);
                    //if the result after filter is empty, call the getQuoteList again.
                    if (result.isEmpty()) {
                        return getQuotesList();
                    } else {
                        return result;
                    }
                } else {
                    //if list of rejected quotes is higher than 300 the user should clear the rejected quotes.
                    return new ArrayList<>();
                }

            }
        }

        return response.getBody();
    }

    private List<Quote> filterQuotes(ResponseEntity<List<Quote>> response, List<RejectedQuote> rejectedQuotes) {
        Set<String> ids = rejectedQuotes.stream()
                .map(RejectedQuote::getQuoteId)
                .collect(Collectors.toSet());

        return response.getBody().stream()
                .filter(e -> !ids.contains(e.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveRejectedQuote(RejectedQuoteDto rQuote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            rejectedQuotesRepository.save(new RejectedQuote(rQuote.getId(), authentication.getName(), rQuote.getQuote()));
        }

    }

    @Override
    public void deleteAllRejectedQuote() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            rejectedQuotesRepository.deleteAllByUserEmail(authentication.getName());
        }
    }


}
