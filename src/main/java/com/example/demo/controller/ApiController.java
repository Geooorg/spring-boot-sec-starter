package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello from public endpoint!";
    }

    @GetMapping("/guest/hello")
    @PreAuthorize("hasAnyRole('GUEST', 'USER', 'ADMIN')")
    public String guestHello() {
        return "Hello Guest! This is a protected endpoint for guests and above.";
    }

    @GetMapping("/user/hello")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String userHello() {
        return "Hello User! This is a protected endpoint for users and admins.";
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHello() {
        return "Hello Admin! This is a protected endpoint for admins only.";
    }
}
