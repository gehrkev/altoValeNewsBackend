package com.ajmv.altoValeNewsBackend.controller;

import com.ajmv.altoValeNewsBackend.model.Administrador;
import com.ajmv.altoValeNewsBackend.repository.AdministradorRepository;
import com.ajmv.altoValeNewsBackend.model.Editor;
import com.ajmv.altoValeNewsBackend.model.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario/admin") // localhost:8080/usuario/editor
public class AdministradorController {
    @Autowired
    private AdministradorRepository repository;

    @GetMapping
    // mandar um GET para esse controller cai aqui - Isso substitui aquela query enviada em formato de string 'select * from administrador" etc
    public List<Administrador> getAll() {
        List<Administrador> administradorList = repository.findAll();
        return administradorList;
    }

    @GetMapping("/{id}") // endpoint para obter um editor pelo ID
    public ResponseEntity<Editor> getById(@PathVariable Integer id){
        Optional<Administrador> administradorOptional = repository.findById(id);
        if (administradorOptional.isPresent()) {
            return ResponseEntity.ok(administradorOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping // POST - criar um novo usuário
    public ResponseEntity<Editor> createAdministrador(@RequestBody Administrador novoAdministrador) {
        try {
            novoAdministrador.setTipo(TipoUsuario.ADMINISTRADOR); // Definir o tipo como tipo 3 - ADMINISTRADOR
            Editor administradorCriado = repository.save(novoAdministrador);
            return ResponseEntity.status(HttpStatus.CREATED).body(administradorCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
