package com.plannerssystem.utils;

import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t WHERE user = ?1 AND isDeleted = 0")
	public Set<Task> getTasksByUser(User user);
}
