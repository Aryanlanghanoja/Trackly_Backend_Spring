package com.LMS.Trackly.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Task {

    public Task() {}

    public Task(FollowUp followUp, LocalDate deadline, String description) {
        this.followUp = followUp;
        this.deadline = deadline;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_up_id", nullable = false)
    private FollowUp followUp;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    // --- Getters and Setters ---

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public FollowUp getFollowUp() {
        return followUp;
    }

    public void setFollowUp(FollowUp followUp) {
        this.followUp = followUp;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
