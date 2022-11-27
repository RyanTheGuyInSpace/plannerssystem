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
import java.util.List;
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

        Set<Routine> subroutines = routineRepository.getUndeletedSubroutines(targetRoutine);

        model.addAttribute("routine", targetRoutine);
        model.addAttribute("routineTasks", targetRoutine.getTasks());
        model.addAttribute("subroutines", subroutines);

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

    @GetMapping("/addExistingTaskToRoutine")
    public ModelAndView addExistingTaskToRoutine(long routineID, long taskID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);
        Task taskToAddToRoutine = taskRepository.getTaskByID(taskID);

        // Validate that the Routine and Task exist and that they belong to this User
        if (targetRoutine == null || !targetRoutine.getUser().equals(user) || taskToAddToRoutine == null || !taskToAddToRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Task newTaskForRoutine = new Task();

        newTaskForRoutine.setName(taskToAddToRoutine.getName());
        newTaskForRoutine.setDescription(taskToAddToRoutine.getDescription());
        newTaskForRoutine.setUser(user);
        newTaskForRoutine.setRoutine(targetRoutine);
        newTaskForRoutine.setDateCreated(new Date());

        taskRepository.save(newTaskForRoutine);

        return new ModelAndView("redirect:/routines/edit?routineID=" + routineID);
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

    @GetMapping("/addSubroutineToRoutine")
    public String addSubroutineToRoutineModal(long routineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetRoutine = routineRepository.getRoutineByID(routineID);
        Set<Routine> eligibleRoutines = routineRepository.getRoutinesAsSubroutines(user, routineID);

        if (targetRoutine == null || !targetRoutine.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("parentRoutine", targetRoutine);
        model.addAttribute("routines", eligibleRoutines);

        return "routines/addSubroutineToRoutineModal";
    }

    @GetMapping("/addExistingSubroutineToRoutine")
    public ModelAndView addExistingSubroutineToRoutineModal(long routineID, long subroutineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetParentRoutine = routineRepository.getRoutineByID(routineID);
        Routine targetSubroutine = routineRepository.getRoutineByID(subroutineID);

        if (targetParentRoutine == null || !targetParentRoutine.getUser().equals(user)
                || targetSubroutine == null
                || !targetSubroutine.getUser().equals(user)
                || targetSubroutine.equals(targetParentRoutine)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // Clone the routine and make the clone's parent the target parent Routine
        Routine newSubroutine = new Routine();

        newSubroutine.setName(targetSubroutine.getName());
        newSubroutine.setDescription(targetSubroutine.getDescription());
        newSubroutine.setDateCreated(new Date());
        newSubroutine.setUser(user);
        newSubroutine.setParentRoutine(targetParentRoutine);

        Routine savedSubroutine = routineRepository.save(newSubroutine);

        List<Task> clonedTasks = targetSubroutine.cloneTasksToRoutine(savedSubroutine);

        for (Task task : clonedTasks) {
            taskRepository.save(task);
        }

        return new ModelAndView("redirect:/routines/edit?routineID=" + routineID);
    }

    @GetMapping("/removeSubroutineFromRoutine")
    public ModelAndView removeSubroutineFromRoutine(long routineID, long subroutineID, Model model) {
        // Finds the user calling the method
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Queries the user from the database
        User user = userRepository.findByUserName(currentPrincipalName);

        Routine targetParentRoutine = routineRepository.getRoutineByID(routineID);
        Routine targetSubroutine = routineRepository.getRoutineByID(subroutineID);

        if (targetParentRoutine == null || !targetParentRoutine.getUser().equals(user)
                || targetSubroutine == null
                || !targetSubroutine.getUser().equals(user)
                || !targetSubroutine.getParentRoutine().equals(targetParentRoutine)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        targetSubroutine.setDeleted(true);

        routineRepository.save(targetSubroutine);

        return new ModelAndView("redirect:/routines/edit?routineID=" + routineID);
    }
}
