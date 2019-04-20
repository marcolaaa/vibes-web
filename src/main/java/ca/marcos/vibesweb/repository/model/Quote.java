package ca.marcos.vibesweb.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements Serializable {

    @JsonProperty("_id")
    private String id;
    private String quote;
    private String author;
    private int length;
    private boolean isExplicit;

    public Quote(String id, String quote, String author, int length, boolean isExplicit) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.length = length;
        this.isExplicit = isExplicit;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isExplicit() {
        return isExplicit;
    }

    public void setExplicit(boolean explicit) {
        isExplicit = explicit;
    }
}
