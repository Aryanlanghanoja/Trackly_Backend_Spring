package com.LMS.Trackly.repository;
import java.util.List;
import java.util.Optional;

import com.LMS.Trackly.model.Document;
import com.LMS.Trackly.model.Lead;
import com.LMS.Trackly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
//    List<User> findByLeads(Integer leads);
//    List<Lead> findByLeadId(Integer leadId);
//    List<Document> findByLeadIdAndDocNameContainingIgnoreCase(Long leadId, String fileName);

    Optional<User> findByEmail(String lowerCase);

    Optional<User> findByToken(String token);

    List<User> findByRole(String employee);

//    @Query("SELECT DISTINCT u FROM User u JOIN u.leads l WHERE l.leadId = :leadId")
//    List<User> findUsersByLeadId(@Param("leadId") Integer leadId);
//
//    @Query("SELECT DISTINCT u FROM User u JOIN u.leads l WHERE l.leadId = :leadId AND u.docName LIKE %:docName%")
//    List<User> findUsersByLeadIdAndDocNameContainingIgnoreCase(
//            @Param("leadId") Long leadId,
//            @Param("docName") String docName);
}
