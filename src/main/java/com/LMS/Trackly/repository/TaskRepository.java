package com.LMS.Trackly.repository;

import com.LMS.Trackly.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.followUp.lead.assignedTo = :username")
    List<Task> findTasksByLeadAssignedTo(@Param("username") String username);

    List<Task> findByFollowUpFollowUpId(Integer followUpId);

    List<Task> findByDeadlineBetween(Date startDate, Date endDate);

}
