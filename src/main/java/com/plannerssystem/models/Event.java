package com.plannerssystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "Events")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Column(name = "startDate", nullable = true)
    private Date startDate;

    @Column(name = "endDate", nullable = true)
    private Date endDate;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Event() {

    }

    public Event(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Event(String name, String description, Time start, Time end) {
        setName(name);
        setDescription(description);
        setStart(start);
        setEnd(end);
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
        return this.startDate;
    }

    public void setStart(Time start) {
        this.startDate = start;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEnd(Time end) {
        this.endDate = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void delete() {
        this.setDeleted(true);
    }
}
