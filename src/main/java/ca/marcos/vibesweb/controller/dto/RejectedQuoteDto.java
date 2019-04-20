package ca.marcos.vibesweb.controller.dto;

import javax.validation.constraints.NotEmpty;

public class RejectedQuoteDto {

    @NotEmpty
    private String id;
    @NotEmpty
    private String quote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
