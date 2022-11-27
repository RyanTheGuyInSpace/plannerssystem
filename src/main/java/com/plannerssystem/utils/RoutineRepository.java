package com.plannerssystem.utils;

import com.plannerssystem.models.Event;
import com.plannerssystem.models.Routine;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Query("SELECT r FROM Routine r WHERE user = ?1 AND isDeleted = 0")
    public Set<Routine> getRoutinesByUser(User user);
}
