package com.LMS.Trackly.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

import com.LMS.Trackly.model.Document;
import com.LMS.Trackly.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<?> createDocument(@RequestParam("document") MultipartFile file,
                                            @RequestParam Map<String, String> body) {
        try {
            Document document = documentService.createDocument((Document) body, file);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Document Created Successfully", "data", document));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllDocuments() {
        try {
            List<Document> documents = documentService.getAllDocuments();
            return ResponseEntity.ok(Map.of("message", "Documents fetched successfully", "data", documents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Integer id) {
        try {
            Optional<Document> document = documentService.getDocumentById(id);
            if (document == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Document not found"));
            }
            return ResponseEntity.ok(Map.of("message", "Document fetched successfully", "data", document));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<?> getDocumentsByLeadId(@PathVariable Integer leadId) {
        try {
            List<Document> docs = documentService.getByLeadId(leadId);
            return ResponseEntity.ok(Map.of("message", "Documents by lead ID fetched successfully", "data", docs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/followup/{followupId}")
    public ResponseEntity<?> getDocumentsByFollowupId(@PathVariable Integer followupId) {
        try {
            List<Document> docs = documentService.getByFollowupId(followupId);
            return ResponseEntity.ok(Map.of("message", "Documents by follow-up ID fetched successfully", "data", docs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/lead/{leadId}/search/{fileName}")
    public ResponseEntity<?> searchByFileNameInLead(@PathVariable Integer leadId,
                                                    @PathVariable String fileName) {
        try {
            List<Document> results = documentService.searchByFileNameInLead(leadId, fileName);
            return ResponseEntity.ok(Map.of("message", "Search by file name successful", "data", results));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Integer id,
                                            @RequestParam Map<String, String> body,
                                            @RequestParam(value = "document", required = false) MultipartFile file) {
        try {
            Document updated = documentService.updateDocument(id, (Document) body, file);
            return ResponseEntity.ok(Map.of("message", "Document updated successfully", "data", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok(Map.of("message", "Document deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadDocument(@PathVariable Integer id) {
        try {
            Resource file = (Resource) documentService.downloadDocument(id);
            if (file == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Document not found"));
            }

            String fileName = file.getFilename();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }
}
