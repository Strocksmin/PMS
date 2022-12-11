package com.example.pms.controller;

import com.example.pms.DTO.FileResponse;
import com.example.pms.model.Task;
import com.example.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {
    @Autowired
    ClientService clientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    StorageService storageService;

    @GetMapping("/profile")
    private String getProfile() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority grantedAuthority: principal.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                return "redirect:/adminpanel";
            }
        }
        for (GrantedAuthority grantedAuthority: principal.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("DOCTOR")) {
                return "redirect:/doctorprofile";
            }
        }
        return "redirect:/clientprofile";
    }

    @GetMapping("/doctorprofile")
    public String getDoctorProfile(Model model) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("clients", doctorService.getDoctorByDoctorUser(principal.getName()).getClients());
        model.addAttribute("doctor", doctorService.getDoctorByDoctorUser(principal.getName()));
        return "doctorprofile";
    }

    @GetMapping("/clientprofile")
    public String getUserProfile(Model model) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("clients", clientService.getClientsByEmail(principal.getName()));
        model.addAttribute("tasks",
                taskService.getNotFinishedTasksForUser(userService.getUserByEmail(principal.getName()).getId()));
        model.addAttribute("path", "http://localhost:8081/download/" + String.valueOf(userService.getUserByEmail(principal.getName()).getId()) + ".jpeg");
        return "clientprofile";
    }

    @GetMapping("/clientprofileUploadImage")
    public String UploadUserProfilePhoto(Model model) throws IOException {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        model.addAttribute("clients", clientService.getClientsByEmail(principal.getName()));
        model.addAttribute("tasks",
                taskService.getNotFinishedTasksForUser(userService.getUserByEmail(principal.getName()).getId()));
        model.addAttribute("path", "http://localhost:8081/download/" + String.valueOf(userService.getUserByEmail(principal.getName()).getId()) + ".jpeg");
        return "clientprofileUploadImage";
    }
}
