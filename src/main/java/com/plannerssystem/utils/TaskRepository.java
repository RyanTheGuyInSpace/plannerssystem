package com.plannerssystem.utils;

import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t WHERE user = ?1 AND isDeleted = 0 AND isCompleted = 0 AND routine_id IS NULL")
	public Set<Task> getTasksByUser(User user);

	@Query("SELECT t FROM Task t WHERE user = ?1 AND (LOWER(name) LIKE %?2% OR LOWER(description) LIKE %?2%) AND isDeleted = 0")
	public Set<Task> getTasksByKeyword(User user, String keyword);

	@Query("SELECT t FROM Task t WHERE id = ?1")
	public Task getTaskByID(long id);
}
