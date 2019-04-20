package ca.marcos.vibesweb.controller;

import ca.marcos.vibesweb.controller.dto.RejectedQuoteDto;
import ca.marcos.vibesweb.repository.model.Quote;
import ca.marcos.vibesweb.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quotes")
public class QuotesController {

    @Autowired
    private QuotesService quotesService;

    @GetMapping("/getQuotes")
    public List<Quote> getQuotes() {
        return quotesService.getQuotesList();
    }

    @PostMapping("/saveRejectedQuote")
    public void saveRejectedQuote(@RequestBody RejectedQuoteDto rejectedQuote) {
        quotesService.saveRejectedQuote(rejectedQuote);
    }

    @DeleteMapping("/deleteAllRejectedQuote")
    public void deleteAllRejectedQuote() {
        quotesService.deleteAllRejectedQuote();
    }

}