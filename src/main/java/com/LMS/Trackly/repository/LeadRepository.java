package com.LMS.Trackly.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.LMS.Trackly.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Integer> {

    // Finds all Leads for a User
    List<Lead> findByUserId(Integer userId);

    // Find leads where assignedTo is null or empty (unassigned)
    List<Lead> findByAssignedToIsNullOrAssignedTo(String assignedTo);

    // Find by assignedTo (employee)
    List<Lead> findByAssignedTo(String assignedTo);

    // Find by source
    List<Lead> findBySource(String source);

    // Find by client
    List<Lead> findByClient(String client);

    // Find by status
    List<Lead> findByStatus(String status);

    // Find by email
    List<Lead> findByEmail(String email);

    // Find by contact number
    List<Lead> findBycontactNumber(String contact_number);

    // Find leads between two dates
    List<Lead> findByDateBetween(Date start, Date end);
}

