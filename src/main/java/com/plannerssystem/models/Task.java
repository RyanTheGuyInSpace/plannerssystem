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
    private String id;
    private String name;
    private String description;
    private Time start;
    private Time end;
    private boolean isDeleted;

    public Task() {

    }

    public Task(String name, String description, Time start, Time end) {
        setName(name);
        setDescription(description);
        setStart(start);
        setEnd(end);
        setId(genId());
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

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
                ", start=" + start +
                ", end=" + end +
                ", id=" + id +
                '}';
    }
}
