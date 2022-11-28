package com.plannerssystem.models;

import javax.persistence.*;

@Entity
@Table(name = "ItemTemplateItems")
public class ItemTemplateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = true)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = true)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "routine_id", referencedColumnName = "id", nullable = true)
    private Routine routine;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "reminder_id", referencedColumnName = "id", nullable = true)
    private Reminder reminder;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "itemTemplate_id", referencedColumnName = "id", nullable = false)
    private ItemTemplate itemTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    public void setItemTemplate(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }
}
