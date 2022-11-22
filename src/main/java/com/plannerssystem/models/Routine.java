package com.plannerssystem.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Entity
@Table(name = "Routines")
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String description;
    private Time start;
    private Time end;
    private Date date;
    private HashMap<String, Routine> subroutines = new HashMap<>();
    private HashMap<String, Task> tasks = new HashMap<>();
    private boolean isDeleted;

    public Routine() {

    }

    public Routine(String name, String description, Time start, Time end, Date date) {
        setName(name);
        setDescription(description);
        setStart(start);
        setEnd(end);
        setDate(date);
        setId(genId());
    }

    public Routine(String name, String description) {
        setName(name);
        setDescription(description);
        // check if id already exists, if so run genId() as long as needed
        setId(genId());
    }

    public Routine(String name, String description, Time start, Time end, Date date, String id,
                   HashMap<String, Routine> subroutines, HashMap<String, Task> tasks) {
        setName(name);
        setDescription(description);
        setStart(start);
        setEnd(end);
        setDate(date);
        setId(id);
        /*
        How we do we load these?
        this.subroutines = subroutines;
        this.tasks = tasks;
        */
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        System.out.println("Routine{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", date=" + date +
                ", id='" + id + '\'' +
                ", subroutines=");
                this.printSubroutines();

                return "";
    }

    public void printSubroutines(){
        for (String key:this.subroutines.keySet()){
            System.out.println(this.subroutines.get(key));
        }
    }


    public static String genId(){
        String id;
        Date cur = new Date();
        Random r = new Random(cur.getTime());
        id = String.valueOf((char)('a'+r.nextInt(26))) + String.valueOf(r.nextInt(999));
        // we would write a check and run algo again if dupe
        return id;
    }

    public void addSubroutine(Routine sub){
        if (sub.getId() == this.getId()){
            System.out.println("CANNOT ADD A ROUTINE TO ITSELF");
            return;
        }

        // check for if routine is already a subroutine of this routine, maybe ask user if they want to continue

        this.subroutines.put(sub.id, sub);
    }

    public void addTask(Task t){
        // check if task already is in this routine, display message if so
        if (this.tasks.containsKey(t.getId())){
            System.out.println("TASK ALREADY EXISTS IN " + this.getName());
            return;
        }

        this.tasks.put(t.getId(), t);
    }
}
