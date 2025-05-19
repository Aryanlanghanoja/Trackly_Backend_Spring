package com.LMS.Trackly.service;

import com.LMS.Trackly.model.Document;
import com.LMS.Trackly.model.Task;
import com.LMS.Trackly.repository.DocumentRepository;
import com.LMS.Trackly.repository.TaskRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository docsRepository;

    @Autowired
    private TaskRepository taskRepository;

    private final String uploadDir = "public/documents/";

    public List<Document> getAllDocuments() {
        return docsRepository.findAll();
    }

    public Optional<Document> getDocumentById(Integer id) {
        return docsRepository.findById(id);
    }

    public Resource downloadDocument(Integer id) throws MalformedURLException {
        Document document = docsRepository.findById(id).orElse(null);
        if (document == null) return null;

        Path filePath = Paths.get("public/documents").resolve(document.getDocPath()).normalize();
        UrlResource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            return (Resource) resource;
        } else {
            return null;
        }
    }

    public Document createDocument(Document doc, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        Files.createDirectories(Paths.get(uploadDir));
        file.transferTo(new File(filePath));

        doc.setDocPath("documents/" + fileName);
        return docsRepository.save(doc);
    }

    public Document updateDocument(Integer id, Document updatedDoc, MultipartFile file) throws IOException {
        return docsRepository.findById(id).map(doc -> {
            try {
                if (file != null) {
                    File oldFile = new File("public/" + doc.getDocPath());
                    if (oldFile.exists()) oldFile.delete();

                    String newFileName = file.getOriginalFilename();
                    String newPath = uploadDir + newFileName;
                    Files.createDirectories(Paths.get(uploadDir));
                    file.transferTo(new File(newPath));

                    doc.setDocPath("documents/" + newFileName);
                }

                doc.setDocName(updatedDoc.getDocName());
                doc.setDocDesc(updatedDoc.getDocDesc());
                doc.setLeadId(updatedDoc.getLeadId());

                return docsRepository.save(doc);
            } catch (IOException e) {
                throw new RuntimeException("Error updating document", e);
            }
        }).orElse(null);
    }

    public String deleteDocument(Integer id) {
        Optional<Document> optionalDoc = docsRepository.findById(id);
        if (optionalDoc.isEmpty()) return "Document not found";

        Document doc = optionalDoc.get();
        File file = new File("public/" + doc.getDocPath());
        if (file.exists()) file.delete();

        docsRepository.deleteById(id);
        return "Deleted successfully";
    }

    public List<Document> getByLeadId(Integer leadId) {
        return docsRepository.findByLeadId(leadId);
    }

    public List<Document> getByFollowupId(Integer followupId) {
        List<Task> tasks = taskRepository.findByFollowUpFollowUpId(followupId);
        List<Document> allDocs = new ArrayList<>();
        for (Task task : tasks) {
            allDocs.addAll(task.getDocuments()); // assumes a OneToMany or ManyToMany mapping exists
        }
        return allDocs;
    }

    public List<Document> searchByFileNameInLead(Integer leadId, String fileName) {
        List<Document> results = docsRepository.findByLeadIdAndDocNameContainingIgnoreCase(leadId, fileName);
        return results.isEmpty() ? new ArrayList<>() : results;
    }

}
