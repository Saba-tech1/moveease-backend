package com.moveease.repository;

import com.moveease.model.QuoteRequest;
import com.moveease.model.QuoteRequest.QuoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {
    List<QuoteRequest> findByEmail(String email);
    List<QuoteRequest> findByStatus(QuoteStatus status);
    List<QuoteRequest> findByServiceType(String serviceType);
}