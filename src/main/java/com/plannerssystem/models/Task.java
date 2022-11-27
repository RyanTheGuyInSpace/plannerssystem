package com.plannerssystem.models;

import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "startDate", nullable = true)
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "endDate", nullable = true)
    private Date endDate;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "isCompleted", nullable = false)
    private boolean isCompleted;

    @Column(name = "dateCompleted")
    private Date dateCompleted;

    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "routine_id", referencedColumnName = "id", nullable = true)
    private Routine routine;

    public Task() {

    }

    public Task(String name, String description, Date startDate, Date endDate) {
        this.setName(name);
        this.setDescription(description);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + startDate +
                ", end=" + endDate +
                ", id=" + id +
                '}';
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void complete() {
        this.setCompleted(true);
        this.setDateCompleted(new Date());
    }

    public void delete() {
        this.setDeleted(true);
    }
}
