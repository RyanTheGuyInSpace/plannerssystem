package com.plannerssystem.controllers;

import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import com.plannerssystem.utils.TaskRepository;
import com.plannerssystem.utils.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

        model.addAttribute("user", user);

        return "tasks/home";
    }

    @GetMapping("/create")
    public String createTaskModal(Model model) {
        model.addAttribute("task", new Task());

        return "tasks/createTaskModal";
    }

    @PostMapping("/create")
    public ModelAndView createTask(@ModelAttribute Task task) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        user.addTask(task);

        userRepository.save(user);

        return new ModelAndView("redirect:/tasks");
    }
}
