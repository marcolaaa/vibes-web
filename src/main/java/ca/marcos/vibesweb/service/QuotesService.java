package ca.marcos.vibesweb.service;

import ca.marcos.vibesweb.controller.dto.RejectedQuoteDto;
import ca.marcos.vibesweb.repository.model.Quote;

import java.util.List;

public interface QuotesService {

    List<Quote> getQuotesList();

    void saveRejectedQuote(RejectedQuoteDto rQuote);

    void deleteAllRejectedQuote();
}
