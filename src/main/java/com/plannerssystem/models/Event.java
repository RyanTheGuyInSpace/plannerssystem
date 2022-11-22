package com.plannerssystem.models;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Column(name = "startDate", nullable = true)
    private Time startDate;

    @Column(name = "endDate", nullable = true)
    private Time endDate;

    @Column(name = "isDelete", nullable = false)
    private boolean isDeleted;

    public Event() {

    }

    public Event(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public Event(String name, String description, Time startDate, Time endDate) {
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
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

    public Time getStartDate() {
        return startDate;
    }

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    public Time getEndDate() {
        return endDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
