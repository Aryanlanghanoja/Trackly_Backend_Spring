package com.LMS.Trackly.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "follow_up")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FollowUp {

    public FollowUp() {}

    public FollowUp(Lead lead, int noOfFollowup, String conclusion, LocalDate nextFollowupDate, User user) {
        this.lead = lead;
        this.noOfFollowup = noOfFollowup;
        this.conclusion = conclusion;
        this.nextFollowupDate = nextFollowupDate;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_up_id")
    private int followUpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @Column(name = "no_of_followup", nullable = false)
    private int noOfFollowup;

    @Column(name = "conclusion", nullable = false, columnDefinition = "TEXT")
    private String conclusion;

    @Column(name = "next_followup_date")
    private LocalDate nextFollowupDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // --- Getters and Setters ---

    public int getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(int followUpId) {
        this.followUpId = followUpId;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public int getNoOfFollowup() {
        return noOfFollowup;
    }

    public void setNoOfFollowup(int noOfFollowup) {
        this.noOfFollowup = noOfFollowup;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public LocalDate getNextFollowupDate() {
        return nextFollowupDate;
    }

    public void setNextFollowupDate(LocalDate nextFollowupDate) {
        this.nextFollowupDate = nextFollowupDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
