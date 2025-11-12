package com.uoc.alquilatusvehiculos.controller;

import com.uoc.alquilatusvehiculos.model.User;
import com.uoc.alquilatusvehiculos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Muestra formulario de login
    @GetMapping("/login")
    public String login() {
        return "login";  // carga login.html
    }

    // Muestra formulario de registro
    @GetMapping("/registro")
    public String registroForm(User user) {
        return "registro"; // carga registro.html
    }

    // Procesa el POST del formulario de registro
    @PostMapping("/registro")
    public String registrar(User user) {
        userService.saveUser(user); // registra usuario y cifra contraseña
        return "redirect:/login";  // redirige al login después de registrarse
    }
}
