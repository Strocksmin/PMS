package com.example.pms.controller;

import com.example.pms.DTO.RoleData;
import com.example.pms.model.*;
import com.example.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Controller
public class AdminPanelController {
    @Autowired
    ProposalService proposalService;
    @Autowired
    ClientService clientService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;

    @GetMapping("/adminpanel")
    public String adminpanel(Model model) {
        model.addAttribute("proposals", proposalService.getProposals());
        return "adminpanel";
    }

    @GetMapping("/adminpanel/edit/{id}")
    public String editProposal(Model model, @PathVariable int id) {
        model.addAttribute("proposal", proposalService.getProposal(id));
        model.addAttribute("doctors", doctorService.getDoctors());
        return "editproposal";
    }

    @PostMapping("/adminpanel/postedit/{id}")
    public String postEditProposal(Model model, @PathVariable int id, @ModelAttribute("proposal") Proposal proposal) {
        Proposal newProposal = proposalService.getProposal(id);
        newProposal.setId(id);
        newProposal.setDatetime(proposal.getDatetime());
        newProposal.setName(proposal.getName());
        newProposal.setLastname(proposal.getLastname());
        newProposal.setSpecialization(proposal.getSpecialization());
        newProposal.setEmail(proposal.getEmail());
        newProposal.setNumber(proposal.getNumber());
        proposalService.addProposal(newProposal);
        return "redirect:/adminpanel";
    }

    @GetMapping("/adminpanel/delete/{id}")
    public String deleteProposal(Model model, @PathVariable int id) {
        proposalService.deleteProposal(id);
        return "redirect:/adminpanel";
    }

    @GetMapping("/adminpanel/accept/{id}")
    public String acceptProposal(Model model, @PathVariable int id) {
        Proposal proposal = proposalService.getProposal(id);
        clientService.addClient(new Client(proposal.getName(), proposal.getLastname(),
                proposal.getEmail(), proposal.getNumber(),
                doctorService.getDoctorForProposal(proposal.getSpecialization()).getId(),
                proposal.getDatetime()));
        proposalService.deleteProposal(id);
        return "redirect:/adminpanel";
    }

    /*@GetMapping("/adminpanel/clients")
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    } */

    @GetMapping("/adminpanel/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/adminpanel/edituser/{id}")
    public String getEditUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("rol", new RoleData());
        return "editUser";
    }

    @PostMapping("/adminpanel/postedituser/{id}")
    public String postEditUser(Model model, @PathVariable int id,  @ModelAttribute("user") User userdata, @ModelAttribute("rol") RoleData rol) {
        User newUser = userService.getUserById(id);
        Collection<Role> roles = newUser.getRoles();
        if (!roles.contains(userService.getRole(rol.getRoleN())))
            roles.add(userService.getRole(rol.getRoleN()));
        newUser.setId(id);
        newUser.setName(userdata.getName());
        newUser.setName(userdata.getLastname());
        newUser.setName(userdata.getName());
        newUser.setRoles(roles);
        userService.upgradeUser(newUser);
        return "redirect:/adminpanel";
    }

    @PostMapping("/adminpanel/taskCreation")
    public String createTask(Model model, @ModelAttribute("task") Task task) {
        User fUser = userService.getUserByEmail(task.getCreation_date());
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setCreation_date(now.format(formatter));
        newTask.setFinish_date(task.getFinish_date());
        newTask.setUserID(fUser.getId());
        newTask.setUser(fUser);
        Collection<Task> tasks = fUser.getTasks();
        tasks.add(newTask);
        fUser.setTasks(tasks);
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        User adminUser = userService.getUserByEmail(principal.getName());
        List<Task> aTasks = adminUser.getAssignedTasks();
        aTasks.add(newTask);
        adminUser.setAssignedTasks(aTasks);
        //userService.upgradeUser(adminUser);
        userService.addUser(fUser);
        //taskService.addTask(newTask);
        return "redirect:/adminpanel";
    }

    @GetMapping("/adminpanel/createTask")
    public String getCreateTask(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userService.getUsers());
        return "createTask";
    }

    @GetMapping("/adminpanel/acceptTask/{id}")
    public String acceptTask(Model model, @PathVariable int id) {
        Task task = taskService.getTask(id);
        task.setFinished(true);
        taskService.addTask(task);
        return "redirect:/clientprofile";
    }

    @GetMapping("/adminpanel/clients")
    public String getWorkerks(Model model) {
        model.addAttribute("users", userService.getUsers());
        //model.addAttribute("path", "http://localhost:8081/download/" + String.valueOf(userService.getUserByEmail(principal.getName()).getId()) + ".jpeg");
        return "clients";
    }
}
