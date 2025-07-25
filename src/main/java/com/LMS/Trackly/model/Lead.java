package com.LMS.Trackly.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "lead")
@JsonIgnoreProperties({
        "hibernateLazyInitializer", "handler"
})
public class Lead {
    public Lead() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lead_id")
    private Integer leadId;

    @Column(name = "emp_name", length = 100)
    private String emp_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // Foreign key in the Lead table
    private User user;


    @Column(name = "source", length = 100)
    private String source;

    @Column(name = "date")
    private Date date;

    @Column(name = "client", length = 100)
    private String client;

    @Column(name = "district", length = 100)
    private String district;

    @Column(name = "contact_number", length = 15)
    private String contactNumber;

    @Column(name = "email",  length = 150)
    private String email;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "assignedTo", length = 50)
    private String assignedTo;

    @Column(name = "emp_id")
    private Integer empID;

    public Lead(Integer leadId, String emp_name, String source, Date date, String client, String district, String contact_number, String email , int  empID) {
        this.leadId = leadId;
        this.emp_name = emp_name;
        this.source = source;
        this.date = date;
        this.client = client;
        this.district = district;
        this.contactNumber = contact_number;
        this.email = email;
        this.status = "Open";
        this.empID = empID;
    }

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContact_number() {
        return contactNumber;
    }

    public void setContact_number(String contact_number) {
        this.contactNumber = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }
}
