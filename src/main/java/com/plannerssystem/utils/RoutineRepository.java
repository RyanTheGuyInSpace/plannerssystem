package com.plannerssystem.utils;

import com.plannerssystem.models.Event;
import com.plannerssystem.models.Routine;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    @Query("SELECT r FROM Routine r WHERE user = ?1 AND isDeleted = 0 AND parent_id IS NULL")
    public Set<Routine> getRoutinesByUser(User user);

    @Query("SELECT t FROM Routine t WHERE user = ?1 AND (LOWER(name) LIKE %?2% OR LOWER(description) LIKE %?2%) AND isDeleted = 0")
    public Set<Routine> getRoutinesByKeyword(User user, String keyword);

    @Query("SELECT r FROM Routine r WHERE id = ?1")
    public Routine getRoutineByID(long routineID);

    @Query("SELECT r FROM Routine r WHERE user = ?1 AND isDeleted = 0 AND id != ?2 AND parent_id IS NULL")
    public Set<Routine> getRoutinesAsSubroutines(User user, long routineToExcludeID);

    @Query("SELECT r FROM Routine r WHERE parentRoutine = ?1 AND isDeleted = 0")
    public Set<Routine> getUndeletedSubroutines(Routine routine);
}
