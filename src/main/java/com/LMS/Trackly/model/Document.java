package com.LMS.Trackly.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "document")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Document {

    public Document() {}

    public Document(Lead lead, String docPath, String docName, String docDesc) {
        this.lead = lead;
        this.docPath = docPath;
        this.docName = docName;
        this.docDesc = docDesc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")  // typo preserved for matching with DB column
    private int documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @Column(name = "doc_path", nullable = false)
    private String docPath;

    @Column(name = "doc_name", nullable = false)
    private String docName;

    @Column(name = "doc_desc", columnDefinition = "TEXT")
    private String docDesc;

    // --- Getters and Setters ---

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }
}