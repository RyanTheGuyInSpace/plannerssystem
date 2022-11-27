package com.plannerssystem.utils;

import com.plannerssystem.models.Reminder;
import com.plannerssystem.models.Routine;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query("SELECT r FROM Reminder r WHERE id = ?1")
    public Reminder getReminderByID(long id);

    @Query("SELECT r FROM Reminder r WHERE user = ?1 AND isDeleted = 0")
    public Set<Reminder> getRemindersByUser(User user);

    @Query("SELECT t FROM Reminder t WHERE user = ?1 AND (LOWER(name) LIKE %?2% OR LOWER(description) LIKE %?2%) AND isDeleted = 0")
    public Set<Reminder> getRemindersByKeyword(User user, String keyword);
}
