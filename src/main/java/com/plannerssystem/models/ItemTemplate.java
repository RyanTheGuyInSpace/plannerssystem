package com.plannerssystem.models;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Table(name = "ItemTemplates")
public class ItemTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    @Column(name = "dateCreated", nullable = false, length = 100)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "itemTemplate", fetch = FetchType.EAGER)
    private Set<ItemTemplateItem> templateItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<ItemTemplateItem> getTemplateItems() {
        return templateItems;
    }

    public int getNumUndeletedTemplateItems() {
        Set<ItemTemplateItem> allTemplateItems = this.templateItems;

        int count = 0;

        for (ItemTemplateItem item : allTemplateItems) {
            if (!item.isDeleted()) {
                count++;
            }
        }

        return count;
    }

    public void setTemplateItems(Set<ItemTemplateItem> templateItems) {
        this.templateItems = templateItems;
    }

    public LinkedList<ItemTemplateItem> getTaskTemplateItems() {
        LinkedList<ItemTemplateItem> taskTemplateItems = new LinkedList<>();

        for (ItemTemplateItem templateItem : this.templateItems) {
            if (!templateItem.isDeleted() && templateItem.getTask() != null) {
                taskTemplateItems.add(templateItem);
            }
        }

        return taskTemplateItems;
    }

    public LinkedList<ItemTemplateItem> getEventTemplateItems() {
        LinkedList<ItemTemplateItem> eventTemplateItems = new LinkedList<>();

        for (ItemTemplateItem templateItem : this.templateItems) {
            if (!templateItem.isDeleted() && templateItem.getEvent() != null) {
                eventTemplateItems.add(templateItem);
            }
        }

        return eventTemplateItems;
    }

    public LinkedList<ItemTemplateItem> getRoutineTemplateItems() {
        LinkedList<ItemTemplateItem> routineTemplateItems = new LinkedList<>();

        for (ItemTemplateItem templateItem : this.templateItems) {
            if (!templateItem.isDeleted() && templateItem.getRoutine() != null) {
                routineTemplateItems.add(templateItem);
            }
        }

        return routineTemplateItems;
    }

    public LinkedList<ItemTemplateItem> getReminderTemplateItems() {
        LinkedList<ItemTemplateItem> reminderTemplateItems = new LinkedList<>();

        for (ItemTemplateItem templateItem : this.templateItems) {
            if (!templateItem.isDeleted() && templateItem.getReminder() != null) {
                reminderTemplateItems.add(templateItem);
            }
        }

        return reminderTemplateItems;
    }

    public LinkedList<Task> getActivatableTasks(User user) {
        Set<ItemTemplateItem> itemTemplateItems = this.getTemplateItems();
        LinkedList<Task> tasksToActivate = new LinkedList<>();

        for (ItemTemplateItem item : itemTemplateItems) {
            if (item.getTask() != null && !item.isDeleted()) {
                Task task = new Task();

                task.setName(item.getTask().getName());
                task.setDescription(item.getTask().getDescription());
                task.setDateCreated(new Date());
                task.setStartDate(item.getTask().getStartDate());
                task.setEndDate(item.getTask().getEndDate());
                task.setUser(user);

                tasksToActivate.add(task);
            }
        }

        return tasksToActivate;
    }

    public LinkedList<Event> getActivatableEvents() {
        Set<ItemTemplateItem> itemTemplateItems = this.getTemplateItems();
        LinkedList<Event> eventsToActivate = new LinkedList<>();

        for (ItemTemplateItem item : itemTemplateItems) {
            if (item.getEvent() != null && !item.isDeleted()) {
                Event event = new Event();

                event.setName(item.getEvent().getName());
                event.setDescription(item.getEvent().getDescription());
                event.setDateCreated(new Date());
                event.setStartDate(item.getEvent().getStartDate());
                event.setEndDate(item.getEvent().getEndDate());
                event.setUser(user);

                eventsToActivate.add(event);
            }
        }

        return eventsToActivate;
    }

    public LinkedList<Routine> getActivatableRoutines() {
        Set<ItemTemplateItem> itemTemplateItems = this.getTemplateItems();
        LinkedList<Routine> routinesToActivate = new LinkedList<>();

        for (ItemTemplateItem item : itemTemplateItems) {
            if (item.getRoutine() != null) {
                Routine routine = new Routine();

                routine.setName(item.getRoutine().getName());
                routine.setDescription(item.getRoutine().getDescription());
                routine.setDateCreated(new Date());
                routine.setStartDate(item.getRoutine().getStartDate());
                routine.setEndDate(item.getRoutine().getEndDate());
                routine.setUser(user);

                routinesToActivate.add(routine);
            }
        }

        return routinesToActivate;
    }

    public LinkedList<Reminder> getActivatableReminders() {
        Set<ItemTemplateItem> itemTemplateItems = this.getTemplateItems();
        LinkedList<Reminder> remindersToActivate = new LinkedList<>();

        for (ItemTemplateItem item : itemTemplateItems) {
            if (item.getReminder() != null) {
                Reminder reminder = new Reminder();

                reminder.setName(item.getReminder().getName());
                reminder.setDescription(item.getReminder().getDescription());
                reminder.setDateCreated(new Date());
                reminder.setStartDate(item.getReminder().getStartDate());
                reminder.setEndDate(item.getReminder().getEndDate());
                reminder.setUser(user);

                remindersToActivate.add(reminder);
            }
        }

        return remindersToActivate;
    }

}
