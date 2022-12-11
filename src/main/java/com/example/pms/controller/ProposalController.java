package com.example.pms.controller;

import com.example.pms.model.Doctor;
import com.example.pms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class ProposalController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("doctors")
    public @ResponseBody List<Doctor> getdoctors(Model model) {
        return doctorService.getDoctors();
    }
}
