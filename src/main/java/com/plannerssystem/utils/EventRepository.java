package com.plannerssystem.utils;

import com.plannerssystem.models.Event;
import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE user = ?1 AND isDeleted = 0")
    public Set<Event> getEventsByUser(User user);

    @Query("SELECT e FROM Event e WHERE user = ?1 AND (LOWER(name) LIKE '%?2%' OR LOWER(description) LIKE '%?2%') AND isDeleted = 0")
    public Set<Event> getEventsByKeywords(User user, String keywords);

    @Query("SELECT e FROM Event e WHERE id = ?1")
    public Event getEventByID(long id);
}
