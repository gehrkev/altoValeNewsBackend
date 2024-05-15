package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Usuario;
import com.ajmv.altoValeNewsBackend.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin
    @PostMapping("/usuario/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuarioLogin) {
        // Verificar se o email existe no banco de dados
        Usuario usuario = repository.findByEmail(usuarioLogin.getEmail());

        if (usuario != null) {
            // Verificar se a senha está correta
            if (passwordEncoder.matches(usuarioLogin.getSenha(), usuario.getSenhahash())) {
                // Aqui você pode gerar um token de autenticação e retorná-lo
                String token = "TOKEN_GERADO_AQUI";
                return ResponseEntity.ok(token);
            } else {
                // Senha incorreta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
        } else {
            // Email não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
        }
    }
}