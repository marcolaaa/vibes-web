package ca.marcos.vibesweb.repository.model;

import javax.persistence.*;

@Entity
public class RejectedQuote {

    @Id
    private String quoteId;

    private String userEmail;

    @Column(length=1024)
    private String quote;

    public RejectedQuote() {}

    public RejectedQuote(String quoteId, String userEmail, String quote) {
        this.quoteId = quoteId;
        this.userEmail = userEmail;
        this.quote = quote;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
