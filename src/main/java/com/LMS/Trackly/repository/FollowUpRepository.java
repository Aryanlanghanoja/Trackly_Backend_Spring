package com.LMS.Trackly.repository;

import com.LMS.Trackly.model.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface FollowUpRepository extends JpaRepository<FollowUp,Integer> {
    List<FollowUp> findByLead_LeadId(Integer leadId);
    List<FollowUp> findByNextFollowupDate(Date date);
    List<FollowUp> findByUsername(String username);
}
