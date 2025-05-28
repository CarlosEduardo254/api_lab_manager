package com.example.demo.controllers;

import com.example.demo.usuario.DTOLogin;
import com.example.demo.usuario.Usuario;
import com.example.demo.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DTOLogin dtoLogin) {
        try {
            Usuario usuario = usuarioService.login(dtoLogin.nome(), dtoLogin.email());
            return ResponseEntity.ok("Login bem-sucedido: " + usuario.getNomeUsuario()); // Retorna sucesso
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Retorna 401 Unauthorized em caso de erro
        }
    }
}
