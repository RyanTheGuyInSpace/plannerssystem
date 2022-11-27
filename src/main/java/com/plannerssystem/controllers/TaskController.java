package com.plannerssystem.controllers;

import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import com.plannerssystem.utils.TaskRepository;
import com.plannerssystem.utils.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Access;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("")
    public String tasksHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUserName(currentPrincipalName);

        Set<Task> userTasks = taskRepository.getTasksByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("tasks", userTasks);
        model.addAttribute("numTasks", userTasks.size());

        return "tasks/home";
    }

    @GetMapping("/create")
    public String createTaskModal(Model model) {
        model.addAttribute("task", new Task());

        return "tasks/createTaskModal";
    }

    @PostMapping("/create")
    public ModelAndView createTask(@ModelAttribute("task") Task task) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        task.setDateCreated(new Date());
        task.setUser(user);

        taskRepository.save(task);

        return new ModelAndView("redirect:/tasks");
    }

    @GetMapping("/complete")
    public ModelAndView completeTask(long taskID) throws ResponseStatusException {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Task taskToComplete = taskRepository.getTaskByID(taskID);

        // Make sure the user is completing one of their own Tasks
        if (!taskToComplete.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        taskToComplete.complete();

        taskRepository.save(taskToComplete);

        return new ModelAndView("redirect:/tasks");
    }
}
