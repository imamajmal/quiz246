package com.example.Quiz_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParticipantPageController {

    @GetMapping("/participant/dashboard")
    public String participantDashboard() {
        return "participant-dashboard";
    }
}

