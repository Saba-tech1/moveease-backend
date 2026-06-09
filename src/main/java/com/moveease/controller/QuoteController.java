package com.moveease.controller;

import com.moveease.model.QuoteRequest;
import com.moveease.model.QuoteRequest.QuoteStatus;
import com.moveease.service.QuoteRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteRequestService quoteRequestService;

    // POST /api/quotes  ── Submit a quote request
    @PostMapping
    public ResponseEntity<QuoteRequest> submitQuote(@Valid @RequestBody QuoteRequest quote) {
        QuoteRequest saved = quoteRequestService.submitQuote(quote);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/quotes  ── Get all quote requests
    @GetMapping
    public ResponseEntity<List<QuoteRequest>> getAllQuotes() {
        return ResponseEntity.ok(quoteRequestService.getAllQuotes());
    }

    // GET /api/quotes/new  ── Get only new/unread quotes
    @GetMapping("/new")
    public ResponseEntity<List<QuoteRequest>> getNewQuotes() {
        return ResponseEntity.ok(quoteRequestService.getNewQuotes());
    }

    // GET /api/quotes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<QuoteRequest> getById(@PathVariable Long id) {
        return ResponseEntity.ok(quoteRequestService.getQuoteById(id));
    }

    // PATCH /api/quotes/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<QuoteRequest> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        QuoteStatus status = QuoteStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(quoteRequestService.updateQuoteStatus(id, status));
    }

    // DELETE /api/quotes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteQuote(@PathVariable Long id) {
        quoteRequestService.deleteQuote(id);
        return ResponseEntity.ok(Map.of("message", "Quote deleted successfully"));
    }
}