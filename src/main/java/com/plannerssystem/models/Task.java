package com.plannerssystem.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Column(name = "startDate", nullable = true)
    private Time startDate;

    @Column(name = "endDate", nullable = true)
    private Time endDate;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    public Task() {

    }

    public Task(String name, String description, Time startDate, Time endDate) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static  String genId(){
        String id;
        Date cur = new Date();
        Random r = new Random(cur.getTime());
        id = String.valueOf((char)('a'+r.nextInt(26))) + String.valueOf(r.nextInt(999));
        // we would write a check and run algo again if dupe
        return id;
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
}
