package com.LMS.Trackly.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.LMS.Trackly.model.Lead;
import com.LMS.Trackly.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    // Create lead
    public Lead createLead(Lead lead) {
        return leadRepository.save(lead);
    }

    // Get all leads
    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    // Get lead by ID
    public Optional<Lead> getLeadById(Integer id) {
        return leadRepository.findById(id);
    }

    // Update lead
    public Optional<Lead> updateLead(Integer id, Lead updateData) {
        System.out.println(updateData);
        return leadRepository.findById(id).map(lead -> {
            lead.setEmp_name(updateData.getEmp_name());
            lead.setSource(updateData.getSource());
            lead.setDate(updateData.getDate());
            lead.setClient(updateData.getClient());
            lead.setDistrict(updateData.getDistrict());
            lead.setContact_number(updateData.getContact_number());
            lead.setEmail(updateData.getEmail());
            lead.setStatus(updateData.getStatus());
            lead.setEmpID(updateData.getEmpID());
            updateData.setAssignedTo(lead.getAssignedTo());
            return leadRepository.save(lead);
        });
    }

    // Delete lead
    public boolean deleteLead(Integer id) {
        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get unassigned leads
    public List<Lead> getUnassignedLeads() {
        // Fetch leads where assignedTo is null or empty string
        return leadRepository.findByAssignedToIsNullOrAssignedTo("");
    }

    // Get leads by employee (assignedTo)
    public List<Lead> getLeadsByEmpId(Integer empId) {
        return leadRepository.findByEmpID(empId);
    }

    // Get leads by source
    public List<Lead> getLeadsBySource(String source) {
        return leadRepository.findBySource(source);
    }

    // Get leads by client
    public List<Lead> getLeadsByClient(String client) {
        return leadRepository.findByClient(client);
    }

    // Get leads by status
    public List<Lead> getLeadsByStatus(String status) {
        return leadRepository.findByStatus(status);
    }

    // Get leads by email
    public List<Lead> getLeadsByEmail(String email) {
        return leadRepository.findByEmail(email);
    }

    // Get leads by contact number
    public List<Lead> getLeadsByContact(String contactNumber) {
        return leadRepository.findBycontactNumber(contactNumber);
    }

    // Get leads in a date range
    public List<Lead> getLeadsByDateRange(Date start, Date end) {
        return leadRepository.findByDateBetween(start, end);
    }

    public List<Lead> createLeadsFromExcel(List<Lead> leads) {
        return leadRepository.saveAll(leads);
    }
}
