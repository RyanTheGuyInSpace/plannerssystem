package com.plannerssystem.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "Routines")
public class Routine {

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

    @Column(name = "date", nullable = false, length = 100)
    private Date date;

    //private List<Routine> subroutines;

    //private List<Task> tasks;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    public Routine() {

    }

    public Routine(String name, String description, Time startDate, Time endDate, Date date) {
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setDate(date);
    }

    public Routine(String name, String description) {
        setName(name);
        setDescription(description);
        // check if id already exists, if so run genId() as long as needed
    }

    public Routine(String name, String description, Time startDate, Time endDate, Date date, String id,
                   HashMap<String, Routine> subroutines, HashMap<String, Task> tasks) {
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setDate(date);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public HashMap<String, Routine> getSubroutines() {
        return subroutines;
    }
    public void setSubroutines(HashMap<String, Routine> subroutines) {
        this.subroutines = subroutines;
    }
    public HashMap<String, Task> getTasks() {
        return tasks;
    }
    public void setTasks(HashMap<String, Task> tasks) {
        this.tasks = tasks;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        System.out.println("Routine{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + startDate +
                ", end=" + endDate +
                ", date=" + date +
                ", id='" + id + '\'' +
                ", subroutines=");
                this.printSubroutines();

                return "";
    }

    public void printSubroutines(){
//        for (String key:this.subroutines.keySet()){
//            System.out.println(this.subroutines.get(key));
//        }
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

        //this.subroutines.add(sub);
    }

    public void addTask(Task t){
        // check if task already is in this routine, display message if so
//        if (this.tasks.contains(t.getId())){
//            System.out.println("TASK ALREADY EXISTS IN " + this.getName());
//            return;
//        }

        //this.tasks.add(t);
    }
}
