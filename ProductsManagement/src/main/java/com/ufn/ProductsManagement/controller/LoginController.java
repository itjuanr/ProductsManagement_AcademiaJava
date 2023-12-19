package com.ufn.ProductsManagement.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @GetMapping
    public String loginSuccess(Authentication authentication) {
        return "Login bem-sucedido para: " + authentication.getName();
    }
}
