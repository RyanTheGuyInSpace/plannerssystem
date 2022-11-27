package com.plannerssystem.controllers;

import com.plannerssystem.models.Event;
import com.plannerssystem.models.Routine;
import com.plannerssystem.models.Task;
import com.plannerssystem.models.User;
import com.plannerssystem.utils.EventRepository;
import com.plannerssystem.utils.RoutineRepository;
import com.plannerssystem.utils.TaskRepository;
import com.plannerssystem.utils.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/routines")
public class RoutineController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @GetMapping("")
    public String routinesHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByUserName(currentPrincipalName);

        Set<Routine> userRoutines = routineRepository.getRoutinesByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("routines", userRoutines);
        model.addAttribute("numRoutines", userRoutines.size());

        return "routines/home";
    }

    @GetMapping("/create")
    public String createRoutineModal(Model model) {
        model.addAttribute("routine", new Routine());

        return "routines/createRoutineModal";
    }

    @PostMapping("/create")
    public ModelAndView createRoutine(@ModelAttribute("routine") Routine routine) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        routine.setDateCreated(new Date());
        routine.setUser(user);

        routineRepository.save(routine);

        return new ModelAndView("redirect:/routines");
    }

    @GetMapping("/edit")
    public String editRoutine(long routineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("routine", targetRoutine);
        model.addAttribute("routineTasks", targetRoutine.getTasks());

        return "routines/edit";
    }

    @PostMapping("/edit")
    public ModelAndView editRoutine(long routineID, Routine routine, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        targetRoutine.setName(routine.getName());
        targetRoutine.setDescription(routine.getDescription());

        routineRepository.save(targetRoutine);

        return new ModelAndView("redirect:/routines/edit?routineID=" + routineID);
    }

    @GetMapping("/addTaskToRoutine")
    public String addTaskToRoutineModal(long routineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("routine", targetRoutine);

        Set<Task> eligibleTasks = taskRepository.getTasksByUser(user);

        model.addAttribute("tasks", eligibleTasks);

        return "routines/addTaskToRoutineModal";
    }

    @GetMapping("/addNewTaskToRoutine")
    public String addNewTaskToRoutineModal(long routineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("task", new Task());
        model.addAttribute("routine", targetRoutine);

        return "routines/addNewTaskToRoutineModal";
    }

    @PostMapping("/addNewTaskToRoutine")
    public ModelAndView addNewTaskToRoutine(long routineID, Task task, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        task.setDateCreated(new Date());
        task.setUser(user);
        task.setRoutine(targetRoutine);

        taskRepository.save(task);

        return new ModelAndView("redirect:/routines/edit?routineID=" + routineID);
    }
}
