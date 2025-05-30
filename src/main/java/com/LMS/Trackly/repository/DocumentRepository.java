package com.LMS.Trackly.repository;

import com.LMS.Trackly.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByLeadId(Integer leadId);

    List<Document> findByLeadIdAndDocNameContainingIgnoreCase(Integer leadId, String fileName);
}
