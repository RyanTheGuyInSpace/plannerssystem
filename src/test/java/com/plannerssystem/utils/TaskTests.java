package com.plannerssystem.utils;

import com.plannerssystem.models.Task;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TaskTests {
    @Test
    public void testTaskComplete() {
        Task task = new Task();

        task.complete();

        assertThat(task.getDateCompleted() != null && task.isCompleted()).isTrue();
    }
}
