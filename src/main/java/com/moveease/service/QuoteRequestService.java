package com.moveease.service;

import com.moveease.model.QuoteRequest;
import com.moveease.model.QuoteRequest.QuoteStatus;
import com.moveease.repository.QuoteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteRequestService {

    private final QuoteRequestRepository quoteRequestRepository;

    @Autowired
    public QuoteRequestService(QuoteRequestRepository quoteRequestRepository) {
        this.quoteRequestRepository = quoteRequestRepository;
    }

    public QuoteRequest submitQuote(QuoteRequest quote) {
        quote.setStatus(QuoteStatus.NEW);
        return quoteRequestRepository.save(quote);
    }

    public List<QuoteRequest> getAllQuotes() {
        return quoteRequestRepository.findAll();
    }

    public QuoteRequest getQuoteById(Long id) {
        return quoteRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quote not found with ID: " + id));
    }

    public List<QuoteRequest> getNewQuotes() {
        return quoteRequestRepository.findByStatus(QuoteStatus.NEW);
    }

    public QuoteRequest updateQuoteStatus(Long id, QuoteStatus status) {
        QuoteRequest quote = getQuoteById(id);
        quote.setStatus(status);
        return quoteRequestRepository.save(quote);
    }

    public void deleteQuote(Long id) {
        quoteRequestRepository.deleteById(id);
    }
}
