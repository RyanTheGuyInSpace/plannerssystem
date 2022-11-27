package com.plannerssystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "Routines")
public class Routine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Column(name = "startDate", nullable = true)
    private Date startDate;

    @Column(name = "endDate", nullable = true)
    private Date endDate;

    @Column(name = "dateCreated", nullable = false, length = 100)
    private Date dateCreated;

    @OneToMany(mappedBy = "routine", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private Routine parentRoutine;

    @OneToMany(mappedBy = "parentRoutine", fetch = FetchType.EAGER)
    private Set<Routine> subroutines;

    public Routine() {

    }

    public Routine getParentRoutine() {
        return parentRoutine;
    }

    public void setParentRoutine(Routine parentRoutine) {
        this.parentRoutine = parentRoutine;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Task> getTasks() {
        return tasks;
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

    public void setStartDate(Time startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Time endDate) {
        this.endDate = endDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date date) {
        this.dateCreated = date;
    }

    public Long getId() {
        return this.id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Routine> getSubroutines() {
        return subroutines;
    }

    @Override
    public String toString() {
        System.out.println("Routine{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", start=" + startDate +
                ", end=" + endDate +
                ", dateCreated=" + dateCreated +
                ", id='" + id + '\'' +
                ", subroutines=");

                return "";
    }

    public void addSubroutine(Routine sub){
        if (sub.getId() == this.getId()){
            System.out.println("CANNOT ADD A ROUTINE TO ITSELF");
            return;
        }

        // check for if routine is already a subroutine of this routine, maybe ask user if they want to continue

        //this.subroutines.add(sub);
    }


    public List<Task> cloneTasksToRoutine(Routine targetRoutine) {
        LinkedList<Task> newTasks = new LinkedList<Task>();

        for (Task task : this.tasks) {
            Task clonedTask = new Task();

            clonedTask.setName(task.getName());
            clonedTask.setDescription(task.getDescription());
            clonedTask.setRoutine(targetRoutine);
            clonedTask.setUser(targetRoutine.getUser());
            clonedTask.setDateCreated(new Date());

            newTasks.add(clonedTask);
        }

        return newTasks;
    }
}
