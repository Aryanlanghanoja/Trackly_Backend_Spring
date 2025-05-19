package com.LMS.Trackly.service;

import com.LMS.Trackly.model.FollowUp;
import com.LMS.Trackly.repository.FollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FollowUpService {

    @Autowired
    private FollowUpRepository followUpRepository;

    // Create FollowUp
    public FollowUp createFollowUp(FollowUp followUp) {
        return followUpRepository.save(followUp);
    }

    // Get All FollowUps
    public List<FollowUp> getAllFollowUps() {
        return followUpRepository.findAll();
    }

    // Get FollowUp by ID
    public Optional<FollowUp> getFollowUpById(Integer id) {
        return followUpRepository.findById(id);
    }

    // Update FollowUp
    public FollowUp updateFollowUp(Integer id, FollowUp updatedFollowUp) {
        return followUpRepository.findById(id).map(existing -> {
            existing.setLead(updatedFollowUp.getLead());
            existing.setFollowupCount(updatedFollowUp.getFollowupCount());
            existing.setConclusion(updatedFollowUp.getConclusion());
            existing.setNextFollowupDate(updatedFollowUp.getNextFollowupDate());
            existing.setUsername(updatedFollowUp.getUsername());
            return followUpRepository.save(existing);
        }).orElse(null);
    }

    // Delete FollowUp
    public boolean deleteFollowUp(Integer id) {
        return followUpRepository.findById(id).map(f -> {
            followUpRepository.delete(f);
            return true;
        }).orElse(false);
    }

    // Get FollowUps by Lead ID
    public List<FollowUp> getFollowUpsByLeadId(Integer leadId) {
        return followUpRepository.findByLead_LeadId(leadId);
    }

    // Get FollowUps by Next FollowUp Date
    public List<FollowUp> getFollowUpsByNextFollowupDate(Date date) {
        return followUpRepository.findByNextFollowupDate((java.sql.Date) date);
    }

    // Get FollowUps by Username
    public List<FollowUp> getFollowUpsByUsername(String username) {
        return followUpRepository.findByUsername(username);
    }

    public FollowUp addFollowUp(FollowUp followUp) {
        return followUpRepository.save(followUp);
    }
}

