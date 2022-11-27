package com.plannerssystem.utils;

import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TaskRepositoryTests {

	@Autowired
	private TestEntityManager entityManagerFactory;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TaskRepository taskRepo;

	@Test
	public void testAddTaskToUser() {
		User user = userRepo.findById(1);

		Task task = new Task();

		task.setUser(user);
		task.setName("Test Task");
		task.setDescription("Test Task Description");
		task.setDateCreated(new Date());

		Task savedTask = taskRepo.save(task);

		assertThat(savedTask.getUser().getId().equals(user.getId()));
	}

	@Test
	public void testGetUserTasks() {
		User user = userRepo.findById(2);

		Set<Task> userTasks = taskRepo.getTasksByUser(user);

		assertThat(user.getTasks().size() == userTasks.size());
	}
}
